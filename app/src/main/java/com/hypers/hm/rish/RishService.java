package com.hypers.hm.rish;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.system.Os;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * RishService berjalan di sisi SERVER (proses Hypers/Shizuku).
 *
 * Ini adalah abstract class — kamu perlu meng-extend-nya dan
 * mengimplementasikan {@link #enforceCallingPermission(String)}.
 *
 * Cara kerja:
 * - Server men-delegate panggilan onTransact() ke RishService.onTransact().
 * - RishService menangani 3 transaksi:
 *   1. TRANSACTION_createHost   → fork proses shell baru (RishHost)
 *   2. TRANSACTION_setWindowSize → ubah ukuran window TTY
 *   3. TRANSACTION_getExitCode  → ambil exit code proses
 *
 * Setiap proses shell dilacak per PID pemanggil (Binder.getCallingPid()).
 *
 * Environment:
 * - Jika server berjalan sebagai ROOT: env klien dipertahankan secara default.
 *   Gunakan RISH_PRESERVE_ENV=0 untuk menonaktifkan.
 * - Jika server berjalan sebagai ADB: env klien TIDAK dipertahankan secara default.
 *   Gunakan RISH_PRESERVE_ENV=1 untuk mengaktifkan.
 * (Termux set PATH/LD_PRELOAD ke path internalnya, yang tidak bisa diakses ADB)
 */
public abstract class RishService {

    private static final String TAG = "RishService";

    /** Map dari PID klien ke RishHost yang sedang berjalan. */
    private static final Map<Integer, RishHost> HOSTS = new HashMap<>();

    /** Apakah server berjalan sebagai root (uid 0). */
    private static final boolean IS_ROOT = Os.getuid() == 0;

    // ------------------------------------------------------------------ //
    //  Internal handlers                                                   //
    // ------------------------------------------------------------------ //

    /**
     * Buat proses shell baru untuk PID klien yang memanggil.
     * Environment akan disaring sesuai kebijakan RISH_PRESERVE_ENV.
     */
    private void createHost(
            String[] args, String[] env, String dir,
            byte tty,
            ParcelFileDescriptor stdin,
            ParcelFileDescriptor stdout,
            ParcelFileDescriptor stderr) {

        int callingPid = Binder.getCallingPid();

        // Tentukan apakah env klien boleh diteruskan ke proses shell
        boolean allowEnv = IS_ROOT; // default: root=boleh, adb=tidak
        if (env != null) {
            for (String e : env) {
                if ("RISH_PRESERVE_ENV=1".equals(e)) {
                    allowEnv = true;
                    break;
                } else if ("RISH_PRESERVE_ENV=0".equals(e)) {
                    allowEnv = false;
                    break;
                }
            }
        }
        if (!allowEnv) {
            env = null; // null → shell akan menggunakan env default server
        }

        RishHost host = new RishHost(args, env, dir, tty, stdin, stdout, stderr);
        host.start();
        Log.d(TAG, "Forked pid=" + host.getPid() + " for callingPid=" + callingPid);

        HOSTS.put(callingPid, host);
    }

    /**
     * Teruskan perubahan ukuran window ke proses shell klien yang bersangkutan.
     */
    private void setWindowSize(long size) {
        int callingPid = Binder.getCallingPid();
        RishHost host = HOSTS.get(callingPid);
        if (host == null) {
            Log.w(TAG, "setWindowSize: tidak ada host untuk callingPid=" + callingPid);
            return;
        }
        host.setWindowSize(size);
    }

    /**
     * Ambil exit code proses shell yang di-fork untuk klien yang memanggil.
     * Mengembalikan {@link Integer#MAX_VALUE} jika proses masih berjalan,
     * atau -1 jika tidak ada host yang ditemukan.
     */
    private int getExitCode() {
        int callingPid = Binder.getCallingPid();
        RishHost host = HOSTS.get(callingPid);
        if (host == null) {
            Log.w(TAG, "getExitCode: tidak ada host untuk callingPid=" + callingPid);
            return -1;
        }
        return host.getExitCode();
    }

    // ------------------------------------------------------------------ //
    //  Abstract                                                            //
    // ------------------------------------------------------------------ //

    /**
     * Periksa apakah klien yang memanggil memiliki izin untuk operasi ini.
     * Lempar {@link SecurityException} jika tidak.
     *
     * @param func nama fungsi yang sedang dipanggil (untuk pesan error)
     */
    public abstract void enforceCallingPermission(String func);

    // ------------------------------------------------------------------ //
    //  Binder transaction handler                                          //
    // ------------------------------------------------------------------ //

    /**
     * Delegasikan dari {@code onTransact()} di Binder server kamu.
     *
     * Contoh penggunaan di server:
     * <pre>
     *   private final RishService rishService = new RishService() {
     *       {@literal @}Override
     *       public void enforceCallingPermission(String func) {
     *           // periksa izin klien di sini
     *       }
     *   };
     *
     *   {@literal @}Override
     *   public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
     *       if (rishService.onTransact(code, data, reply, flags)) return true;
     *       return super.onTransact(code, data, reply, flags);
     *   }
     * </pre>
     *
     * @return true jika transaksi ini ditangani oleh RishService, false jika bukan.
     */
    public boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) {

        // ---- TRANSACTION_createHost ----------------------------------------
        if (code == RishConfig.getTransactionCode(RishConfig.TRANSACTION_createHost)) {
            Log.d(TAG, "onTransact: TRANSACTION_createHost");

            enforceCallingPermission("createHost");

            // Transaksi oneway tidak mengirim reply
            if (reply == null || (flags & IBinder.FLAG_ONEWAY) != 0) {
                return true;
            }

            data.enforceInterface(RishConfig.getInterfaceToken());

            byte                tty    = data.readByte();
            ParcelFileDescriptor stdin  = data.readFileDescriptor();
            ParcelFileDescriptor stdout = data.readFileDescriptor();
            ParcelFileDescriptor stderr = null;

            // Jika stderr tidak di-redirect ke TTY, klien mengirim fd terpisah
            if ((tty & RishConstants.ATTY_ERR) == 0) {
                stderr = data.readFileDescriptor();
            }

            String[] args = data.createStringArray();
            String[] env  = data.createStringArray();
            String   dir  = data.readString();

            createHost(args, env, dir, tty, stdin, stdout, stderr);
            reply.writeNoException();
            return true;
        }

        // ---- TRANSACTION_setWindowSize -------------------------------------
        if (code == RishConfig.getTransactionCode(RishConfig.TRANSACTION_setWindowSize)) {
            Log.d(TAG, "onTransact: TRANSACTION_setWindowSize");

            enforceCallingPermission("setWindowSize");

            data.enforceInterface(RishConfig.getInterfaceToken());
            long size = data.readLong();
            setWindowSize(size);

            if (reply != null) {
                reply.writeNoException();
            }
            return true;
        }

        // ---- TRANSACTION_getExitCode ---------------------------------------
        if (code == RishConfig.getTransactionCode(RishConfig.TRANSACTION_getExitCode)) {
            Log.d(TAG, "onTransact: TRANSACTION_getExitCode");

            enforceCallingPermission("getExitCode");

            data.enforceInterface(RishConfig.getInterfaceToken());
            int exitCode = getExitCode();

            if (reply != null) {
                reply.writeNoException();
                reply.writeInt(exitCode);
            }
            return true;
        }

        // Bukan transaksi rish
        return false;
    }
}
