package com.hypers.hm.rish;

import android.util.Log;

import java.util.Arrays;

/**
 * Entry point publik untuk menjalankan shell rish dari sisi klien.
 *
 * Alur penggunaan:
 * 1. Panggil {@link RishConfig#init(android.os.IBinder, String, int)} terlebih dahulu
 *    untuk menginisialisasi koneksi ke server Hypers/Shizuku.
 * 2. Buat instance {@link Rish}.
 * 3. Override {@link #requestPermission(Runnable)} untuk menangani
 *    permintaan izin jika diperlukan.
 * 4. Panggil {@link #start(String[])} dengan argumen shell yang diinginkan.
 *
 * Contoh sederhana:
 * <pre>
 *   // Setelah Hypers/Shizuku siap:
 *   RishConfig.init(binder, interfaceToken, transactionCodeStart);
 *
 *   Rish rish = new Rish() {
 *       {@literal @}Override
 *       public void requestPermission(Runnable onGranted) {
 *           // Tampilkan dialog, lalu panggil onGranted.run() jika diizinkan
 *           onGranted.run();
 *       }
 *   };
 *   rish.start(new String[]{"sh", "-c", "echo hello"});
 * </pre>
 */
public class Rish {

    private static final String TAG = "RISH";

    // ------------------------------------------------------------------ //
    //  Permission                                                          //
    // ------------------------------------------------------------------ //

    /**
     * Minta izin sebelum menjalankan shell.
     *
     * Override method ini untuk menampilkan dialog/UI izin.
     * Panggil {@code onGrantedRunnable.run()} ketika izin sudah diberikan.
     *
     * Implementasi default tidak melakukan apa-apa (otomatis dianggap tidak
     * memiliki izin dan tidak akan lanjut).
     *
     * @param onGrantedRunnable callback yang dipanggil jika izin diberikan
     */
    public void requestPermission(Runnable onGrantedRunnable) {
        // Default: tidak ada implementasi izin.
        // Override di subclass atau anonymous class.
    }

    // ------------------------------------------------------------------ //
    //  Internal                                                            //
    // ------------------------------------------------------------------ //

    /**
     * Cek izin — jika belum ada, minta dulu; jika sudah ada, langsung jalankan.
     */
    private void startShell(String[] args, boolean permissionGranted) {
        if (!permissionGranted) {
            // Minta izin, lalu jalankan ulang dengan permissionGranted=true
            requestPermission(() -> startShell(args, true));
            return;
        }
        startShell(args);
    }

    /**
     * Jalankan shell melalui RishTerminal.
     * Proses ini BLOCKING — memanggil {@link RishTerminal#waitFor()}.
     *
     * Di akhir, memanggil System.exit() dengan exit code yang diterima.
     * Jika terjadi error, System.exit(1).
     */
    private void startShell(String[] args) {
        try {
            RishTerminal terminal = new RishTerminal(args);
            terminal.start();
            int exitCode = terminal.waitFor();
            System.exit(exitCode);
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            System.err.flush();
            System.exit(1);
        }
    }

    // ------------------------------------------------------------------ //
    //  Public entry point                                                  //
    // ------------------------------------------------------------------ //

    /**
     * Mulai sesi shell rish.
     *
     * Pastikan {@link RishConfig#init(android.os.IBinder, String, int)} sudah
     * dipanggil sebelum memanggil method ini.
     *
     * Method ini akan:
     * 1. Meminta izin (via {@link #requestPermission}).
     * 2. Membuat RishTerminal dan menyambungkan I/O.
     * 3. Menunggu shell selesai.
     * 4. Memanggil System.exit() dengan exit code hasil.
     *
     * Karena memanggil System.exit(), sebaiknya jalankan di thread terpisah
     * agar tidak membunuh proses utama sebelum waktunya.
     *
     * @param args argumen shell, mis. {@code new String[]{"sh", "-c", "ls -la"}}
     */
    public void start(String[] args) {
        Log.d(TAG, "start: args=" + Arrays.toString(args));
        startShell(args, false);
    }
}
