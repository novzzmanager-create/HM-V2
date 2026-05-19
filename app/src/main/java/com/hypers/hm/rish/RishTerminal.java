package com.hypers.hm.rish;

import android.os.Parcel;
import android.os.RemoteException;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * RishTerminal berjalan di sisi KLIEN (aplikasi yang menggunakan shell rish).
 *
 * Cara kerja:
 * 1. {@link #prepare()} (native) — deteksi apakah stdin/stdout/stderr klien
 *    terhubung ke TTY, dan kembalikan bitmask ATTY_*.
 * 2. Buat pipe (Os.pipe()) untuk stdin, stdout, dan opsional stderr.
 * 3. Kirim fd ke server via TRANSACTION_createHost.
 * 4. {@link #start()} — mulai transfer data antara terminal klien dan pipe.
 * 5. Jika mode TTY aktif, thread terpisah memantau perubahan ukuran window
 *    dan mengirimkannya ke server via TRANSACTION_setWindowSize.
 * 6. {@link #waitFor()} — blokir sampai server melaporkan proses selesai.
 *
 * Semua operasi I/O level rendah dilakukan di librish.so.
 */
public class RishTerminal {

    private static final String TAG = "RishTerminal";

    // ------------------------------------------------------------------ //
    //  Helper statis untuk akses array FileDescriptor dengan null-check   //
    // ------------------------------------------------------------------ //

    /** Ambil raw fd dari elemen ke-i array, atau -1 jika array null. */
    public static int getFd(FileDescriptor[] fileDescriptor, int i) {
        if (fileDescriptor == null) return -1;
        return FileDescriptors.getFd(fileDescriptor[i]);
    }

    /** Tutup elemen ke-i array FileDescriptor (silent). */
    public static void closeFd(FileDescriptor[] fileDescriptor, int i) {
        if (fileDescriptor == null) return;
        FileDescriptors.closeSilently(fileDescriptor[i]);
    }

    // ------------------------------------------------------------------ //
    //  Fields                                                              //
    // ------------------------------------------------------------------ //

    private final String[] argv;
    private final byte     tty;  // bitmask ATTY_* hasil prepare()

    /**
     * Pipe stdin: [0]=sisi baca (dikirim ke server), [1]=sisi tulis (dari klien)
     */
    private FileDescriptor[] stdin;

    /**
     * Pipe stdout: [0]=sisi baca (ke klien), [1]=sisi tulis (dari server)
     */
    private FileDescriptor[] stdout;

    /**
     * Pipe stderr: [0]=sisi baca (ke klien), [1]=sisi tulis (dari server).
     * Null jika stderr dialihkan ke TTY.
     */
    private FileDescriptor[] stderr;

    /**
     * FD master pty lokal (di sisi klien).
     * -1 jika tidak ada TTY.
     */
    private int ttyFd = -1;

    private int exitCode;

    // ------------------------------------------------------------------ //
    //  Constructor                                                         //
    // ------------------------------------------------------------------ //

    /**
     * Siapkan terminal dan kirim permintaan createHost ke server.
     *
     * @param argv argumen shell (mis. ["sh", "-c", "ls"])
     * @throws ErrnoException  jika Os.pipe() gagal
     * @throws RemoteException jika komunikasi binder gagal
     */
    public RishTerminal(String[] argv) throws ErrnoException, RemoteException {
        this.argv = argv;
        this.tty  = prepare(); // native: deteksi TTY, kembalikan bitmask ATTY_*
        createHost();
    }

    // ------------------------------------------------------------------ //
    //  Private: kirim createHost ke server                                //
    // ------------------------------------------------------------------ //

    private void createHost() throws ErrnoException, RemoteException {
        Log.d(TAG, "createHost");

        Parcel data  = Parcel.obtain();
        Parcel reply = Parcel.obtain();

        // Kumpulkan environment saat ini
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            list.add(entry.getKey() + "=" + entry.getValue());
        }
        String[] env = list.toArray(new String[0]);
        String   dir = new File("").getAbsolutePath();

        try {
            data.writeInterfaceToken(RishConfig.getInterfaceToken());
            data.writeByte(tty);

            // stdin pipe: sisi [0] dikirim ke server (server baca dari sini)
            stdin = Os.pipe();
            data.writeFileDescriptor(stdin[0]);

            // stdout pipe: sisi [1] dikirim ke server (server tulis ke sini)
            stdout = Os.pipe();
            data.writeFileDescriptor(stdout[1]);

            // stderr pipe hanya dikirim jika tidak menggunakan TTY untuk stderr
            if ((tty & RishConstants.ATTY_ERR) == 0) {
                stderr = Os.pipe();
                data.writeFileDescriptor(stderr[1]);
            }

            data.writeStringArray(argv);
            data.writeStringArray(env);
            data.writeString(dir);

            RishConfig.getBinder().transact(
                    RishConfig.getTransactionCode(RishConfig.TRANSACTION_createHost),
                    data, reply, 0);
            reply.readException();

        } finally {
            data.recycle();
            reply.recycle();

            // Tutup sisi fd yang sudah dikirim ke server — tidak perlu lagi di klien
            closeFd(stdin,  0); // server pegang sisi baca stdin
            closeFd(stdout, 1); // server pegang sisi tulis stdout
            closeFd(stderr, 1); // server pegang sisi tulis stderr
        }
    }

    // ------------------------------------------------------------------ //
    //  Public: mulai transfer I/O                                         //
    // ------------------------------------------------------------------ //

    /**
     * Mulai transfer data antara file descriptor lokal dan pipe ke server.
     * Jika mode TTY aktif, juga mulai thread pemantau ukuran window.
     *
     * Harus dipanggil setelah constructor berhasil.
     */
    public void start() {
        Log.d(TAG, "start");

        // native start() menyambungkan:
        // - stdin klien  → stdin[1] (sisi tulis pipe stdin)
        // - stdout[0]    → stdout klien (sisi baca pipe stdout)
        // - stderr[0]    → stderr klien (atau digabung ke stdout jika TTY)
        // Mengembalikan fd master pty lokal (-1 jika bukan TTY)
        ttyFd = start(tty,
                getFd(stdin,  1),  // klien tulis ke stdin proses
                getFd(stdout, 0),  // klien baca dari stdout proses
                getFd(stderr, 0)); // klien baca dari stderr proses (-1 jika TTY)

        // Jika ada TTY, pantau perubahan ukuran window TIOCGWINSZ secara native
        if (ttyFd != -1) {
            new Thread(() -> {
                while (true) {
                    Log.d(TAG, "waitForWindowSizeChange");
                    try {
                        long size = waitForWindowSizeChange(ttyFd);
                        setWindowSize(size);
                    } catch (Throwable e) {
                        Log.w(TAG, Log.getStackTraceString(e));
                    }
                }
            }, "rish-winsize").start();
        }
    }

    // ------------------------------------------------------------------ //
    //  Private: transaksi ke server                                       //
    // ------------------------------------------------------------------ //

    /** Kirim TRANSACTION_setWindowSize ke server. */
    private void setWindowSize(long size) throws RemoteException {
        Log.d(TAG, "setWindowSize");

        Parcel data  = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(RishConfig.getInterfaceToken());
            data.writeLong(size);
            // FLAG_ONEWAY → tidak perlu menunggu reply untuk operasi non-kritis ini
            RishConfig.getBinder().transact(
                    RishConfig.getTransactionCode(RishConfig.TRANSACTION_setWindowSize),
                    data, null, 0);
            // Tidak ada reply untuk setWindowSize
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    /** Kirim TRANSACTION_getExitCode ke server dan kembalikan hasilnya. */
    private int requestExitCode() throws RemoteException {
        Log.d(TAG, "requestExitCode");

        Parcel data  = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(RishConfig.getInterfaceToken());
            RishConfig.getBinder().transact(
                    RishConfig.getTransactionCode(RishConfig.TRANSACTION_getExitCode),
                    data, reply, 0);
            reply.readException();
            return reply.readInt();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    // ------------------------------------------------------------------ //
    //  Public: tunggu selesai                                             //
    // ------------------------------------------------------------------ //

    /**
     * Blokir sampai proses shell di server selesai, lalu kembalikan exit code.
     * Harus dipanggil setelah {@link #start()}.
     */
    public int waitFor() {
        Log.d(TAG, "waitFor");

        // native: tunggu sampai EOF di pipe I/O (proses shell sudah mati)
        waitForProcessExit();

        try {
            exitCode = requestExitCode();
        } catch (Throwable e) {
            Log.w(TAG, Log.getStackTraceString(e));
            exitCode = -1;
        }
        return exitCode;
    }

    /** Exit code terakhir yang diterima dari server. */
    public int getExitCode() {
        return exitCode;
    }

    // ------------------------------------------------------------------ //
    //  Native methods (diimplementasikan di librish.so)                   //
    // ------------------------------------------------------------------ //

    /**
     * Deteksi apakah stdin/stdout/stderr terminal saat ini terhubung ke TTY.
     * @return bitmask ATTY_IN | ATTY_OUT | ATTY_ERR
     */
    private static native byte prepare();

    /**
     * Mulai transfer data antar pipe.
     * @param tty    bitmask ATTY_*
     * @param stdin  sisi tulis pipe stdin (klien → server)
     * @param stdout sisi baca pipe stdout (server → klien)
     * @param stderr sisi baca pipe stderr (server → klien), -1 jika TTY
     * @return fd master pty lokal, atau -1 jika tidak ada TTY
     */
    private static native int start(byte tty, int stdin, int stdout, int stderr);

    /**
     * Blokir sampai ukuran window berubah (SIGWINCH), lalu kembalikan
     * ukuran baru yang di-encode: high 16-bit = rows, low 16-bit = cols.
     * @param fd fd terminal lokal
     */
    private static native long waitForWindowSizeChange(int fd);

    /**
     * Blokir sampai proses shell di sisi server selesai
     * (terdeteksi lewat EOF pada pipe I/O).
     */
    private static native void waitForProcessExit();
}
