package com.hypers.hm.bridge;

// =====================================================================
//  HypersTcpManager.java — TCP Detection & Connection untuk Sketchware
//  Tambahkan file ini ke: src/com/hypers/hm/bridge/
//
//  Fitur:
//  ✓ Detect apakah TCP/ADB sudah aktif
//  ✓ Aktifkan TCP melalui shell (ADB atau root)
//  ✓ Hentikan TCP
//  ✓ Bisa running walau WiFi mati (mode USB)
//  ✓ Auto-reconnect via Binder (tidak butuh jaringan)
// =====================================================================

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Mengelola koneksi TCP untuk HypersManager.
 *
 * PENTING: ADB over TCP hanya dibutuhkan untuk MODE PAIRING AWAL.
 * Setelah Binder terhubung, Hypers berjalan via IPC (bukan TCP),
 * sehingga app tetap berfungsi walau WiFi mati.
 *
 * Cara pakai di MainActivity:
 * <pre>
 *   HypersTcpManager.checkTcpStatus(result -> {
 *       runOnUiThread(() -> tvStatus.setText(result.summary()));
 *   });
 * </pre>
 */
public class HypersTcpManager {

    private static final String TAG = "HypersTcpManager";

    /** Port default ADB over TCP */
    public static final int ADB_TCP_PORT = 5555;

    // ================================================================
    //  STATUS RESULT
    // ================================================================

    public static class TcpStatus {
        public final boolean tcpEnabled;      // adb tcpip aktif
        public final boolean wifiConnected;   // WiFi tersambung
        public final boolean usbConnected;    // USB debugging aktif
        public final boolean binderAlive;     // Hypers Binder hidup (tidak butuh TCP)
        public final int     tcpPort;         // Port TCP yang aktif (0 = tidak aktif)
        public final String  localIp;         // IP lokal (null jika tidak ada WiFi)

        TcpStatus(boolean tcp, boolean wifi, boolean usb, boolean binder,
                  int port, String ip) {
            this.tcpEnabled   = tcp;
            this.wifiConnected = wifi;
            this.usbConnected  = usb;
            this.binderAlive   = binder;
            this.tcpPort       = port;
            this.localIp       = ip;
        }

        /** Ringkasan status untuk ditampilkan ke UI */
        public String summary() {
            StringBuilder sb = new StringBuilder();

            if (binderAlive) {
                sb.append("✅ Hypers aktif (Binder hidup)\n");
                sb.append("   → Tidak perlu TCP/WiFi\n");
            } else {
                sb.append("⚠ Hypers belum terhubung\n");
            }

            sb.append("\n📡 TCP/ADB: ").append(tcpEnabled
                    ? "AKTIF (port " + tcpPort + ")"
                    : "TIDAK AKTIF");

            sb.append("\n📶 WiFi: ").append(wifiConnected
                    ? "Terhubung" + (localIp != null ? " (" + localIp + ")" : "")
                    : "Mati/Tidak tersambung");

            sb.append("\n🔌 USB: ").append(usbConnected ? "Terdeteksi" : "Tidak");

            if (!binderAlive) {
                sb.append("\n\n💡 Cara start tanpa WiFi:\n");
                sb.append("   • Sambungkan USB → tap \"Start via USB\"\n");
                sb.append("   • Atau gunakan Root jika sudah di-root");
            }

            return sb.toString();
        }
    }

    // ================================================================
    //  CALLBACK
    // ================================================================

    public interface TcpCallback {
        void onResult(TcpStatus status);
    }

    public interface ShellCallback {
        void onResult(boolean success, String message);
    }

    // ================================================================
    //  CEK STATUS TCP
    // ================================================================

    /**
     * Cek status TCP secara lengkap (async, jangan panggil di main thread).
     * Hasil dikirim via callback di background thread.
     * Gunakan runOnUiThread() untuk update UI.
     *
     * @param context  context app
     * @param callback hasil TcpStatus
     */
    public static void checkTcpStatus(Context context, TcpCallback callback) {
        new Thread(() -> {
            boolean tcpEnabled  = isTcpEnabled();
            boolean wifi        = isWifiConnected(context);
            boolean usb         = isUsbDebuggingActive();
            boolean binder      = com.hypers.hm.Hypers.pingBinder();
            int     port        = tcpEnabled ? getTcpPort() : 0;
            String  ip          = wifi ? getLocalIpAddress() : null;

            TcpStatus status = new TcpStatus(tcpEnabled, wifi, usb, binder, port, ip);
            Log.i(TAG, "TCP status: tcp=" + tcpEnabled + " wifi=" + wifi
                    + " usb=" + usb + " binder=" + binder);
            callback.onResult(status);
        }, "hypers-tcp-check").start();
    }

    // ================================================================
    //  AKTIFKAN TCP
    // ================================================================

    /**
     * Aktifkan ADB over TCP di port 5555.
     * Bisa dijalankan via ADB user (tidak perlu root).
     *
     * CATATAN: Setelah TCP aktif, Hypers akan connect via Binder.
     * Setelah Binder hidup, TCP tidak lagi diperlukan.
     *
     * @param useRoot  true = gunakan root shell, false = ADB shell biasa
     * @param callback hasil: success + pesan
     */
    public static void enableTcp(boolean useRoot, ShellCallback callback) {
        new Thread(() -> {
            // Perintah shell untuk aktifkan TCP
            String cmd = "setprop service.adb.tcp.port " + ADB_TCP_PORT
                       + " && stop adbd && start adbd"
                       + " && echo TCP_OK";

            String result = runShellCommand(cmd, useRoot);

            boolean success = result.contains("TCP_OK");
            String  msg;

            if (success) {
                msg = "✅ TCP aktif di port " + ADB_TCP_PORT + "\n"
                    + "ADB dapat terhubung via:\n"
                    + "  adb connect <IP>:" + ADB_TCP_PORT;
            } else if (result.isEmpty()) {
                msg = "❌ Gagal aktifkan TCP.\n"
                    + (useRoot ? "Pastikan perangkat sudah di-root."
                               : "Coba gunakan mode Root.");
            } else {
                msg = "⚠ Output tidak dikenal:\n" + result;
                success = false;
            }

            Log.i(TAG, "enableTcp: " + msg);
            callback.onResult(success, msg);
        }, "hypers-tcp-enable").start();
    }

    /**
     * Aktifkan TCP via Hypers RemoteShellSession (jika Binder sudah hidup).
     * Ini cara TERBAIK karena tidak perlu root terpisah.
     */
    public static void enableTcpViaHypers(boolean useRoot, ShellCallback callback) {
        new Thread(() -> {
            try {
                String cmd = "setprop service.adb.tcp.port " + ADB_TCP_PORT
                           + " && stop adbd && start adbd && echo TCP_OK";

                HypersPairingManager.RemoteShellSession session = useRoot
                        ? HypersPairingManager.startOnRoot()
                        : HypersPairingManager.startOnAdb();

                String result = session.execute(cmd);
                boolean ok = result.contains("TCP_OK");
                callback.onResult(ok, ok
                        ? "✅ TCP aktif via Hypers di port " + ADB_TCP_PORT
                        : "❌ Gagal: " + (result.isEmpty() ? "tidak ada output" : result));
            } catch (Exception e) {
                callback.onResult(false, "❌ Exception: " + e.getMessage()
                        + "\nPastikan Hypers Binder sudah hidup.");
            }
        }, "hypers-tcp-via-hypers").start();
    }

    // ================================================================
    //  HENTIKAN TCP
    // ================================================================

    /**
     * Matikan ADB over TCP (kembalikan ke USB only).
     * Aman dilakukan kapan saja — Hypers Binder tetap hidup.
     *
     * @param useRoot  true = gunakan root
     * @param callback hasil
     */
    public static void disableTcp(boolean useRoot, ShellCallback callback) {
        new Thread(() -> {
            // Set port ke -1 = nonaktifkan TCP
            String cmd = "setprop service.adb.tcp.port -1"
                       + " && stop adbd && start adbd"
                       + " && echo TCP_OFF";

            String result = runShellCommand(cmd, useRoot);
            boolean success = result.contains("TCP_OFF");

            callback.onResult(success, success
                    ? "✅ TCP dinonaktifkan. ADB hanya via USB."
                    : "❌ Gagal nonaktifkan TCP: " + result);
        }, "hypers-tcp-disable").start();
    }

    /**
     * Matikan TCP via Hypers Binder (cara terbaik jika Binder hidup).
     */
    public static void disableTcpViaHypers(boolean useRoot, ShellCallback callback) {
        new Thread(() -> {
            try {
                String cmd = "setprop service.adb.tcp.port -1"
                           + " && stop adbd && start adbd && echo TCP_OFF";

                HypersPairingManager.RemoteShellSession session = useRoot
                        ? HypersPairingManager.startOnRoot()
                        : HypersPairingManager.startOnAdb();

                String result = session.execute(cmd);
                boolean ok = result.contains("TCP_OFF");
                callback.onResult(ok, ok
                        ? "✅ TCP dimatikan via Hypers."
                        : "❌ Gagal: " + result);
            } catch (Exception e) {
                callback.onResult(false, "❌ Exception: " + e.getMessage());
            }
        }, "hypers-tcp-disable-hypers").start();
    }

    // ================================================================
    //  CEK KONEKTIVITAS TCP (ping port)
    // ================================================================

    /**
     * Cek apakah port ADB TCP bisa dijangkau di IP tertentu.
     * Berguna untuk verifikasi sebelum pairing.
     *
     * @param host     IP address target (e.g. "192.168.1.5")
     * @param port     port (default: 5555)
     * @param callback true = port terbuka
     */
    public static void checkTcpPort(String host, int port, ShellCallback callback) {
        new Thread(() -> {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), 2000); // 2 detik timeout
                callback.onResult(true, "✅ Port " + port + " di " + host + " terbuka.");
            } catch (Exception e) {
                callback.onResult(false, "❌ Port " + port + " tidak bisa dijangkau: "
                        + e.getMessage());
            }
        }, "hypers-tcp-ping").start();
    }

    // ================================================================
    //  KENAPA BISA JALAN WALAU WIFI MATI
    // ================================================================

    /**
     * Setelah Hypers Binder aktif, komunikasi berjalan via Android IPC
     * (Binder), BUKAN via TCP/WiFi. Sehingga app tetap berfungsi penuh
     * walau WiFi dimatikan.
     *
     * Method ini cek apakah Binder masih hidup.
     *
     * @return true = Hypers berfungsi normal tanpa jaringan
     */
    public static boolean isHypersAliveWithoutNetwork() {
        return com.hypers.hm.Hypers.pingBinder();
    }

    // ================================================================
    //  HELPER INTERNAL
    // ================================================================

    /** Cek apakah ADB TCP aktif via system property */
    private static boolean isTcpEnabled() {
        try {
            String port = runShellCommand("getprop service.adb.tcp.port", false);
            if (port == null || port.isEmpty() || port.equals("-1") || port.equals("0")) {
                return false;
            }
            int p = Integer.parseInt(port.trim());
            return p > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /** Ambil port TCP yang aktif */
    private static int getTcpPort() {
        try {
            String port = runShellCommand("getprop service.adb.tcp.port", false);
            if (port == null || port.isEmpty()) return ADB_TCP_PORT;
            return Integer.parseInt(port.trim());
        } catch (Exception e) {
            return ADB_TCP_PORT;
        }
    }

    /** Cek WiFi tersambung */
    private static boolean isWifiConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return false;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                android.net.Network net = cm.getActiveNetwork();
                if (net == null) return false;
                NetworkCapabilities caps = cm.getNetworkCapabilities(net);
                return caps != null && caps.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI);
            } else {
                android.net.NetworkInfo info = cm.getActiveNetworkInfo();
                return info != null
                        && info.getType() == ConnectivityManager.TYPE_WIFI
                        && info.isConnected();
            }
        } catch (Exception e) {
            return false;
        }
    }

    /** Cek apakah USB debugging aktif (via Android settings) */
    private static boolean isUsbDebuggingActive() {
        try {
            int setting = android.provider.Settings.Global.getInt(
                    null, // context tidak dibutuhkan untuk Global
                    android.provider.Settings.Global.ADB_ENABLED, 0);
            return setting == 1;
        } catch (Exception e) {
            // fallback via shell
            String result = runShellCommand(
                    "settings get global adb_enabled", false);
            return "1".equals(result != null ? result.trim() : "");
        }
    }

    /** Ambil IP lokal WiFi via shell */
    private static String getLocalIpAddress() {
        try {
            // ip route lebih reliable dari ifconfig
            String result = runShellCommand(
                    "ip route | grep wlan | awk '{print $9}' | head -1", false);
            if (result != null && !result.isEmpty()) return result.trim();

            // fallback: ifconfig
            result = runShellCommand(
                    "ifconfig wlan0 | grep 'inet ' | awk '{print $2}'", false);
            return result != null ? result.trim() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Jalankan shell command sederhana (bukan via Hypers Binder).
     * Digunakan untuk deteksi awal sebelum Binder tersedia.
     */
    private static String runShellCommand(String command, boolean useRoot) {
        try {
            String[] shell = useRoot
                    ? new String[]{"su", "-c", command}
                    : new String[]{"sh", "-c", command};

            Process process = Runtime.getRuntime().exec(shell);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            process.waitFor();
            return sb.toString().trim();
        } catch (Exception e) {
            Log.e(TAG, "runShellCommand failed: " + command, e);
            return "";
        }
    }
}
