package com.hypers.hm.debug;

/*
 * Originally from: LADB 2.4.3 (https://github.com/tytydraco/LADB)
 * Refactored for HypersManager — Android 11-16 compatible
 *
 * Key changes from LADB:
 * - Removed adb start-server / wait-for-device (blocked by SELinux on Android 13+)
 * - initServer() now connects directly to TCP port after TLS pairing
 * - Falls back to Hypers.newProcess() if Binder is alive
 * - Falls back to local sh if neither ADB nor Binder available
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.preference.PreferenceManager;
import java.io.File;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import com.hypers.hm.Hypers;

public class ADB {

    public static final int MAX_OUTPUT_BUFFER_SIZE = 1024 * 16;
    public static final long OUTPUT_BUFFER_DELAY_MS = 100L;

    @SuppressLint("StaticFieldLeak")
    private static volatile ADB instance = null;

    public static ADB getInstance(Context context) {
        if (instance == null) {
            synchronized (ADB.class) {
                if (instance == null) {
                    instance = new ADB(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private final Context context;
    private final android.content.SharedPreferences sharedPrefs;

    private final String adbPath;
    private final String scriptPath;

    private final MutableLiveData<Boolean> _started = new MutableLiveData<>(false);
    public final LiveData<Boolean> started = _started;

    private final MutableLiveData<Boolean> _closed = new MutableLiveData<>(false);
    public final LiveData<Boolean> closed = _closed;

    public final File outputBufferFile;

    private Process shellProcess = null;
    private com.hypers.hm.HypersRemoteProcess shellProcs;
    private Thread keepAliveThread;
    private boolean tryingToPair = false;

    private ADB(Context context) {
        this.context = context;
        this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.adbPath = context.getApplicationInfo().nativeLibraryDir + "/libadb.so";
        this.scriptPath = context.getExternalFilesDir(null) + "/script.sh";
        try {
            this.outputBufferFile = File.createTempFile("buffer", ".txt");
            this.outputBufferFile.deleteOnExit();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ── initServer ────────────────────────────────────────────────────
    /**
     * Buka shell. Urutan prioritas:
     *
     * 1. Hypers Binder hidup → pakai Hypers.newProcess("sh") — paling aman
     * 2. TCP 5555 aktif (sudah pair sebelumnya) → connect via libadb.so
     * 3. Fallback → sh lokal (tanpa privilege)
     *
     * TIDAK lagi pakai adb start-server / wait-for-device karena:
     * - Diblokir SELinux di Android 13+
     * - Tidak relevan setelah TLS pairing (adbd sudah jalan)
     */
    public boolean initServer() {
        if (Boolean.TRUE.equals(_started.getValue())) return true;
        if (tryingToPair) {
            debug("Sedang menginisialisasi shell...");
            return false;
        }
        tryingToPair = true;

        boolean secureSettingsGranted =
                context.checkSelfPermission(Manifest.permission.WRITE_SECURE_SETTINGS)
                        == PackageManager.PERMISSION_GRANTED;

        // ── Mode 1: Hypers Binder aktif ──────────────────────────────
        if (Hypers.pingBinder()) {
            debug("[BOOT]: Hypers Binder aktif → membuka shell via IPC...");
            try {
                com.hypers.hm.HypersRemoteProcess hypersProc =
                        Hypers.newProcess(new String[]{"sh", "-l"});
                if (hypersProc != null) {
                    shellProcs = hypersProc;
                    debug("[SHELL]: Shell terbuka via Hypers Binder.");
                    postInitShell(secureSettingsGranted, "HYPERS BINDER");
                    return true;
                }
            } catch (Exception e) {
                debug("[WARN]: Hypers.newProcess gagal: " + e.getMessage());
            }
        }

        // ── Mode 2: TCP 5555 aktif → pakai libadb.so ─────────────────
        if (isPort5555Active() && new File(adbPath).exists()) {
            debug("[BOOT]: Port 5555 aktif → connect via libadb.so...");
            try {
                // connect dulu (tidak perlu start-server)
                adb(false, java.util.Arrays.asList("connect", "127.0.0.1:5555")).waitFor(5, TimeUnit.SECONDS);

                // buka shell langsung
                java.util.List<String> argList;
                if (Build.SUPPORTED_ABIS[0].equals("arm64-v8a")) {
                    argList = java.util.Arrays.asList("-t", "1", "shell");
                } else {
                    argList = java.util.Arrays.asList("shell");
                }
                shellProcess = adb(true, argList);
                debug("[SHELL]: Shell terbuka via TCP 5555.");
                startKeepAlive();
                postInitShell(secureSettingsGranted, "TCP 5555");
                return true;
            } catch (Exception e) {
                debug("[WARN]: TCP connect gagal: " + e.getMessage());
            }
        }

        // ── Mode 3: Wireless debugging enabled → try libadb.so connect ──
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
                && isWirelessDebuggingEnabled()
                && new File(adbPath).exists()) {
            debug("[BOOT]: Wireless debugging ON → mencoba koneksi ADB TLS...");
            try {
                // Coba connect ke port default wireless (biasanya random, tapi coba dulu)
                adb(false, java.util.Arrays.asList("connect", "127.0.0.1:5555")).waitFor(5, TimeUnit.SECONDS);
                boolean waitOk = adb(false, java.util.Arrays.asList("wait-for-device"))
                        .waitFor(8, TimeUnit.SECONDS);

                if (waitOk) {
                    java.util.List<String> argList = Build.SUPPORTED_ABIS[0].equals("arm64-v8a")
                            ? java.util.Arrays.asList("-t", "1", "shell")
                            : java.util.Arrays.asList("shell");
                    shellProcess = adb(true, argList);
                    debug("[SHELL]: Shell terbuka via Wireless ADB.");
                    startKeepAlive();
                    postInitShell(secureSettingsGranted, "WIRELESS ADB");
                    return true;
                }
            } catch (Exception e) {
                debug("[WARN]: Wireless ADB gagal: " + e.getMessage());
            }
        }

        // ── Mode 4: Fallback → sh lokal ──────────────────────────────
        debug("[FALLBACK]: Membuka sh lokal (tanpa privilege ADB)...");
        shellProcess = shell(true, java.util.Arrays.asList("sh", "-l"));
        postInitShell(secureSettingsGranted, "LOCAL SH");
        return true;
    }

    private void postInitShell(boolean secureSettingsGranted, String mode) {
        sendToShellProcess("alias adb=\"" + adbPath + "\"");
        if (!secureSettingsGranted) {
            debug("[PERM]: Menyuntikkan WRITE_SECURE_SETTINGS...");
            sendToShellProcess("pm grant " + context.getPackageName()
                    + " android.permission.WRITE_SECURE_SETTINGS &> /dev/null");
        }
        sendToShellProcess("clear");
        sendToShellProcess("echo '[CONNECTED] HYPERS SHELL ACTIVE — MODE: " + mode + "'");
        _started.postValue(true);
        tryingToPair = false;
        debug("[STATUS]: HYPERS MANAGER AKTIF — " + mode);
    }

    // ── Shell status ──────────────────────────────────────────────────

    public int getShellPid() {
        if (shellProcess != null) {
            try {
                java.lang.reflect.Field field = shellProcess.getClass().getDeclaredField("pid");
                field.setAccessible(true);
                return field.getInt(shellProcess);
            } catch (Exception e) {
                try {
                    String s = shellProcess.toString();
                    if (s.contains("pid="))
                        return Integer.parseInt(s.split("pid=")[1].split("]")[0].trim());
                } catch (Exception ignored) {}
                return -1;
            }
        }
        return -1;
    }

    public boolean isPort5555Active() {
        java.net.Socket socket = null;
        try {
            socket = new java.net.Socket();
            socket.connect(new java.net.InetSocketAddress("127.0.0.1", 5555), 500);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (socket != null) try { socket.close(); } catch (java.io.IOException ignored) {}
        }
    }

    private boolean isWirelessDebuggingEnabled() {
        return Settings.Global.getInt(context.getContentResolver(), "adb_wifi_enabled", 0) == 1;
    }

    private boolean isUSBDebuggingEnabled() {
        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.ADB_ENABLED, 0) == 1;
    }

    // ── Server control ────────────────────────────────────────────────

    public void stopServer() {
        debug("[STOP]: Mematikan shell...");
        if (new File(adbPath).exists()) {
            try { adb(false, java.util.Arrays.asList("kill-server")).waitFor(); }
            catch (Exception e) { e.printStackTrace(); }
        }
        if (shellProcess != null) {
            try { shellProcess.destroyForcibly(); } catch (Exception e) { e.printStackTrace(); }
            shellProcess = null;
        }
        _started.postValue(false);
        tryingToPair = false;
        debug("[STATUS]: HYPERS SHELL DISCONNECTED.");
    }

    public void waitForDeathAndReset() {
        new Thread(() -> {
            while (true) {
                if (shellProcess != null) {
                    try { shellProcess.waitFor(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                _started.postValue(false);
                debug("Shell is dead, resetting");
                if (new File(adbPath).exists()) {
                    try { adb(false, java.util.Arrays.asList("kill-server")).waitFor(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
                initServer();
            }
        }).start();
    }

    // ── Pairing ───────────────────────────────────────────────────────

    public boolean pair(String host, String port, String pairingCode) {
        if (!new File(adbPath).exists()) {
            debug("[PAIR]: libadb.so tidak ada, pairing via service.");
            return false;
        }
        String target = (host == null || host.isEmpty()) ? "127.0.0.1" : host;
        String originalUser = System.getProperty("user.name");
        System.setProperty("user.name", "HypersManager@System");
        Process pairShell = null;
        try {
            pairShell = adb(false, java.util.Arrays.asList("pair", target + ":" + port));
            Thread.sleep(500);
            PrintStream ps = new PrintStream(pairShell.getOutputStream());
            ps.println(pairingCode);
            ps.flush();
            boolean paired = pairShell.waitFor(15, TimeUnit.SECONDS);
            return paired && pairShell.exitValue() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (originalUser != null) System.setProperty("user.name", originalUser);
            else System.clearProperty("user.name");
            if (pairShell != null) pairShell.destroyForcibly();
        }
    }

    public boolean connectViaRoot() {
        try {
            Process rootProcess = Runtime.getRuntime().exec("su");
            java.io.DataOutputStream os = new java.io.DataOutputStream(rootProcess.getOutputStream());
            os.writeBytes("setprop service.adb.tcp.port 5555\n");
            os.writeBytes("stop adbd\n");
            os.writeBytes("start adbd\n");
            os.flush();
            Thread.sleep(1000);
            if (new File(adbPath).exists()) {
                adb(false, java.util.Arrays.asList("connect", "127.0.0.1:5555")).waitFor();
            }
            return initServer();
        } catch (Exception e) {
            debug("Gagal akses Root: " + e.getMessage());
            return false;
        }
    }

    public boolean stopTcpPort() {
        Process os = null;
        java.io.DataOutputStream dos = null;
        try {
            os = Runtime.getRuntime().exec("su");
            dos = new java.io.DataOutputStream(os.getOutputStream());
            dos.writeBytes("setprop service.adb.tcp.port -1\n");
            dos.writeBytes("stop adbd\n");
            dos.writeBytes("start adbd\n");
            dos.writeBytes("exit\n");
            dos.flush();
            os.waitFor();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (dos != null) dos.close();
                if (os != null) os.destroy();
            } catch (Exception ignored) {}
        }
    }

    public boolean disconnectLocalTcp() {
        try {
            if (new File(adbPath).exists())
                adb(false, java.util.Arrays.asList("disconnect", "127.0.0.1:5555")).waitFor();
            debug("[DISCONNECT]: Berhasil memutus localhost:5555");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ── Process helpers ───────────────────────────────────────────────

    public Process adb(boolean redirect, java.util.List<String> command) {
        java.util.List<String> cmd = new java.util.ArrayList<>(command);
        cmd.add(0, adbPath);
        return shell(redirect, cmd);
    }

    private Process shell(boolean redirect, java.util.List<String> command) {
        ProcessBuilder pb = new ProcessBuilder(command).directory(context.getFilesDir());
        if (redirect) {
            pb.redirectErrorStream(true);
            pb.redirectOutput(outputBufferFile);
        }
        pb.environment().put("USER",   "HypersManager");
        pb.environment().put("HOME",   context.getFilesDir().getPath());
        pb.environment().put("TMPDIR", context.getCacheDir().getPath());
        try {
            return pb.start();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendToShellProcess(String msg) {
        if (shellProcess == null || shellProcess.getOutputStream() == null) return;
        PrintStream ps = new PrintStream(shellProcess.getOutputStream());
        ps.println(msg);
        ps.flush();
    }

    public void debug(String msg) {
        synchronized (outputBufferFile) {
            if (outputBufferFile.exists()) {
                try {
                    java.nio.file.Files.write(
                            outputBufferFile.toPath(),
                            ("* " + msg + System.lineSeparator()).getBytes(),
                            java.nio.file.StandardOpenOption.APPEND);
                } catch (java.io.IOException e) { e.printStackTrace(); }
            }
        }
    }

    public int getOutputBufferSize() {
        String v = sharedPrefs.getString("buffer_size", "16384");
        try { return Integer.parseInt(v); }
        catch (NumberFormatException e) { return MAX_OUTPUT_BUFFER_SIZE; }
    }

    private void startKeepAlive() {
        if (keepAliveThread != null && keepAliveThread.isAlive()) return;
        keepAliveThread = new Thread(() -> {
            while (true) {
                try {
                    if (shellProcess == null || !shellProcess.isAlive()) break;
                    sendToShellProcess("echo alive");
                    Thread.sleep(15000);
                } catch (Exception e) { break; }
            }
        });
        keepAliveThread.start();
    }
}
