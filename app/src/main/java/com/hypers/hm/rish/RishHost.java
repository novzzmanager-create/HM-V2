package com.hypers.hm.rish;

import android.os.ParcelFileDescriptor;
import android.util.Log;

/**
 * RishHost berjalan di sisi SERVER (proses Hypers/Shizuku).
 *
 * Tugasnya:
 * 1. Menerima argumen, environment, dan file descriptor dari klien.
 * 2. Fork proses shell baru via JNI (native start()).
 * 3. Meneruskan stdin/stdout/stderr antara klien dan proses shell yang di-fork.
 * 4. Menunggu exit code proses.
 * 5. Jika mode TTY aktif, menerima perubahan ukuran window (SIGWINCH).
 *
 * Semua operasi fork/exec/wait dilakukan di lapisan native (librish.so)
 * karena Android tidak mengekspos API fork langsung ke Java.
 */
public class RishHost {

    private static final String TAG = "RishHost";

    // ------------------------------------------------------------------ //
    //  Helper: konversi String[] → byte[] dengan null-terminator (C-style) //
    // ------------------------------------------------------------------ //

    /**
     * Buat blok byte C-style dari array String.
     * Format: [ s0\0 | s1\0 | ... | sN\0 ]
     * Digunakan untuk melewatkan argv/envp ke execve() di native.
     */
    private static byte[] createCBytesForStringArray(String[] array) {
        if (array == null) {
            return null;
        }
        byte[][] bytes = new byte[array.length][];
        int count = bytes.length; // untuk NUL byte tiap string
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = array[i].getBytes();
            count += bytes[i].length;
        }
        byte[] block = new byte[count];
        int i = 0;
        for (byte[] arg : bytes) {
            System.arraycopy(arg, 0, block, i, arg.length);
            i += arg.length + 1;
            // byte NUL sudah 0 secara default (Java zero-init)
        }
        return block;
    }

    /**
     * Buat byte[] null-terminated dari sebuah String tunggal.
     * Digunakan untuk melewatkan path direktori kerja ke chdir().
     */
    private static byte[] createCBytesForString(String s) {
        if (s == null) {
            return null;
        }
        byte[] bytes  = s.getBytes();
        byte[] result = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        result[result.length - 1] = (byte) 0;
        return result;
    }

    /**
     * Detach fd dari ParcelFileDescriptor sehingga GC tidak menutupnya.
     * Mengembalikan -1 jika pfd null.
     */
    private static int detachFd(ParcelFileDescriptor pfd) {
        if (pfd == null) {
            return -1;
        }
        return pfd.detachFd();
    }

    // ------------------------------------------------------------------ //
    //  Fields                                                              //
    // ------------------------------------------------------------------ //

    private final String[] args;
    private final String[] env;
    private final String   dir;
    private final byte     tty;

    // raw fd yang sudah di-detach dari ParcelFileDescriptor
    private final int stdin;
    private final int stdout;
    private final int stderr;

    private int pid;
    private int ptmx;                         // master pty fd (hanya valid saat TTY)
    private int exitCode = Integer.MAX_VALUE; // belum keluar

    // ------------------------------------------------------------------ //
    //  Constructor                                                         //
    // ------------------------------------------------------------------ //

    /**
     * @param args   argv proses shell (mis. ["sh", "-c", "ls"])
     * @param env    environment variables; null → tidak mengubah env
     * @param dir    direktori kerja; null → direktori saat ini
     * @param tty    bitmask ATTY_IN | ATTY_OUT | ATTY_ERR
     * @param stdin  ParcelFileDescriptor sisi baca stdin dari klien
     * @param stdout ParcelFileDescriptor sisi tulis stdout ke klien
     * @param stderr ParcelFileDescriptor sisi tulis stderr ke klien (null jika TTY)
     */
    public RishHost(
            String[] args, String[] env, String dir,
            byte tty,
            ParcelFileDescriptor stdin,
            ParcelFileDescriptor stdout,
            ParcelFileDescriptor stderr) {

        this.args   = args;
        this.env    = env;
        this.dir    = dir;
        this.tty    = tty;
        this.stdin  = detachFd(stdin);
        this.stdout = detachFd(stdout);
        this.stderr = detachFd(stderr);
    }

    // ------------------------------------------------------------------ //
    //  Public API                                                          //
    // ------------------------------------------------------------------ //

    /**
     * Fork dan eksekusi proses shell, lalu mulai thread transfer I/O.
     * Dipanggil oleh RishService setelah menerima TRANSACTION_createHost.
     */
    public void start() {
        Log.d(TAG, "start");

        byte[] argBlock = createCBytesForStringArray(args);
        byte[] envBlock = createCBytesForStringArray(env);
        byte[] dirBlock = createCBytesForString(dir);

        // native start() melakukan:
        // 1. fork()
        // 2. dup2(stdin/stdout/stderr)
        // 3. execve(args, env, dir)
        // Mengembalikan [pid, ptmxFd]
        int[] result = start(
                argBlock, args.length,
                envBlock, env != null ? env.length : -1,
                dirBlock,
                tty, stdin, stdout, stderr);

        pid  = result[0];
        ptmx = result[1];

        // Thread terpisah menunggu exit agar tidak memblokir binder thread
        new Thread(() -> exitCode = waitFor(pid)).start();
    }

    /** PID proses shell yang sudah di-fork. */
    public int getPid() {
        return pid;
    }

    /**
     * Exit code proses.
     * Mengembalikan {@link Integer#MAX_VALUE} selama proses masih berjalan.
     */
    public int getExitCode() {
        return exitCode;
    }

    /**
     * Set ukuran window terminal (diteruskan ke TIOCSWINSZ via ioctl).
     * Hanya relevan jika mode TTY aktif.
     *
     * @param size nilai yang di-encode: high 16-bit = rows, low 16-bit = cols
     */
    public void setWindowSize(long size) {
        Log.d(TAG, "setWindowSize");
        setWindowSize(ptmx, size);
    }

    // ------------------------------------------------------------------ //
    //  Native methods (diimplementasikan di librish.so)                   //
    // ------------------------------------------------------------------ //

    /**
     * Fork dan exec proses shell.
     *
     * @param argBlock  byte[] argv C-style
     * @param argc      jumlah argumen
     * @param envBlock  byte[] env C-style; null → tidak ubah env
     * @param envc      jumlah env vars; -1 → tidak ubah env
     * @param dirBlock  byte[] working directory C-style; null → direktori saat ini
     * @param tty       bitmask ATTY_*
     * @param stdin     raw fd stdin
     * @param stdout    raw fd stdout
     * @param stderr    raw fd stderr (-1 jika mode TTY)
     * @return          int[2] { pid, ptmxFd }
     */
    private static native int[] start(
            byte[] argBlock, int argc,
            byte[] envBlock, int envc,
            byte[] dirBlock,
            byte tty, int stdin, int stdout, int stderr);

    /**
     * Kirim TIOCSWINSZ ke master pty.
     *
     * @param ptmx raw fd master pty
     * @param size high 16-bit = rows, low 16-bit = cols
     */
    private static native void setWindowSize(int ptmx, long size);

    /**
     * Tunggu (blocking) sampai proses dengan PID tertentu selesai.
     * Mengembalikan exit code.
     */
    private static native int waitFor(int pid);
}
