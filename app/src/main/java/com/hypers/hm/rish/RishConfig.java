package com.hypers.hm.rish;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.util.Log;

/**
 * Menyimpan konfigurasi global untuk sistem rish:
 * - IBinder ke server rish (sisi klien)
 * - Interface token untuk Parcel
 * - Transaction code offset
 * - Path library native librish.so
 *
 * Harus di-init sebelum RishTerminal atau RishService digunakan.
 *
 * Sisi SERVER (berjalan di proses Hypers/Shizuku):
 *   RishConfig.init(interfaceToken, transactionCodeStart);
 *
 * Sisi KLIEN (aplikasi yang menggunakan rish):
 *   RishConfig.init(binder, interfaceToken, transactionCodeStart);
 */
public class RishConfig {

    private static final String TAG = "RISHConfig";

    // Kode transaksi relatif terhadap transactionCodeStart
    static final int TRANSACTION_createHost    = 0;
    static final int TRANSACTION_setWindowSize = 1;
    static final int TRANSACTION_getExitCode   = 2;

    private static IBinder binder;
    private static String  interfaceToken;
    private static int     transactionCodeStart;
    private static String  libraryPath;

    // ------------------------------------------------------------------ //
    //  Getter                                                              //
    // ------------------------------------------------------------------ //

    /** IBinder ke proses server rish (diisi sisi klien). */
    static IBinder getBinder() {
        return binder;
    }

    /** Interface token yang ditulis ke Parcel setiap transaksi. */
    static String getInterfaceToken() {
        return interfaceToken;
    }

    /**
     * Terjemahkan kode transaksi lokal (TRANSACTION_*) ke kode
     * sesungguhnya yang dimengerti server.
     */
    static int getTransactionCode(int code) {
        return transactionCodeStart + code;
    }

    // ------------------------------------------------------------------ //
    //  Setter                                                              //
    // ------------------------------------------------------------------ //

    /**
     * Atur path direktori yang berisi librish.so.
     * Jika null, System.loadLibrary("rish") akan digunakan.
     */
    public static void setLibraryPath(String path) {
        libraryPath = path;
    }

    // ------------------------------------------------------------------ //
    //  Load native library                                                 //
    // ------------------------------------------------------------------ //

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    private static void loadLibrary() {
        if (libraryPath == null) {
            System.loadLibrary("rish");
        } else {
            System.load(libraryPath + "/librish.so");
        }
    }

    // ------------------------------------------------------------------ //
    //  Init                                                                //
    // ------------------------------------------------------------------ //

    /**
     * Init sisi SERVER – dipanggil dari dalam proses daemon Hypers/Shizuku.
     * Tidak perlu binder karena server adalah proses itu sendiri.
     *
     * @param interfaceToken       token yang digunakan di writeInterfaceToken/enforceInterface
     * @param transactionCodeStart offset kode transaksi (biasanya dari IHypersService.DESCRIPTOR)
     */
    public static void init(String interfaceToken, int transactionCodeStart) {
        Log.d(TAG, "init (server) " + interfaceToken + " " + transactionCodeStart);
        RishConfig.interfaceToken      = interfaceToken;
        RishConfig.transactionCodeStart = transactionCodeStart;
        loadLibrary();
    }

    /**
     * Init sisi KLIEN – dipanggil dari aplikasi yang ingin menggunakan shell rish.
     *
     * @param binder               IBinder ke layanan server rish
     * @param interfaceToken       harus sama dengan yang di-init server
     * @param transactionCodeStart harus sama dengan yang di-init server
     */
    public static void init(IBinder binder, String interfaceToken, int transactionCodeStart) {
        Log.d(TAG, "init (client) " + binder + " " + interfaceToken + " " + transactionCodeStart);
        RishConfig.binder              = binder;
        RishConfig.interfaceToken      = interfaceToken;
        RishConfig.transactionCodeStart = transactionCodeStart;
        loadLibrary();
    }
}
