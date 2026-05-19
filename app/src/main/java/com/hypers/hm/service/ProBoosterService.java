package com.hypers.hm.service;
import com.hypers.hm.ExecEngine;

import android.app.*;
import android.content.*;
import android.os.*;
import android.os.Process;
import com.hypers.hm.R;
import java.io.*;
import java.util.*;

public class ProBoosterService extends Service {

    private NotificationManager nm;

    private HandlerThread workerThread;
    private Handler worker;

    private static final String PLUGIN_PATH =
            "/data/local/tmp/HYPERS/data/Plugins/";

    private Boolean isRoot = null;

    private final Map<String, List<String>> pluginScripts =
            new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();

        nm = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        startForeground(
                1,
                buildNotif("HYPERS MODE", "Running Plugins")
        );

        workerThread = new HandlerThread(
                "HypersWorker",
                Process.THREAD_PRIORITY_BACKGROUND
        );

        workerThread.start();

        worker = new Handler(workerThread.getLooper());

        worker.post(new Runnable() {
            @Override
            public void run() {

                cachePlugins();
                applyAllPlugins();
                startWatchdog();
            }
        });
    }

    // ================= ROOT =================

    private boolean isRootAvailableCached() {

        if (isRoot == null) {
            isRoot = isRootAvailable();
        }

        return isRoot;
    }

    private boolean isRootAvailable() {

        try {

            java.lang.Process p =
                    Runtime.getRuntime().exec("su");

            p.getOutputStream().write("exit\n".getBytes());
            p.getOutputStream().flush();

            p.waitFor();

            return p.exitValue() == 0;

        } catch (Exception e) {
            return false;
        }
    }

    // ================= EXEC =================

    private void exec(String cmd) {

        try {

            if (isRootAvailableCached()) {

                Runtime.getRuntime().exec(
                        new String[]{
                                "su",
                                "-c",
                                cmd
                        }
                );

            } else {

                ExecEngine.newProcess(new String[]{
                                "sh",
                                "-c",
                                cmd
                        });
            }

        } catch (Exception ignored) {
        }
    }

    private String execRead(String cmd) {

        StringBuilder out = new StringBuilder();

        try {

            java.lang.Process p;

            if (isRootAvailableCached()) {

                p = Runtime.getRuntime().exec(
                        new String[]{
                                "su",
                                "-c",
                                cmd
                        }
                );

            } else {

                p = ExecEngine.newProcess(new String[]{
                                "sh",
                                "-c",
                                cmd
                        });
            }

            BufferedReader r =
                    new BufferedReader(
                            new InputStreamReader(
                                    p.getInputStream()
                            )
                    );

            String line;

            while ((line = r.readLine()) != null) {
                out.append(line).append("\n");
            }

            r.close();

        } catch (Exception ignored) {
        }

        return out.toString();
    }

    // ================= CACHE =================

    private void cachePlugins() {

        pluginScripts.clear();

        File base = new File(PLUGIN_PATH);

        if (!base.exists()) return;

        File[] folders = base.listFiles();

        if (folders == null) return;

        for (File folder : folders) {

            if (!folder.isDirectory()) continue;

            File[] files = folder.listFiles();

            if (files == null) continue;

            List<String> scripts =
                    new ArrayList<>();

            for (File f : files) {

                if (f.getName().endsWith(".sh")) {
                    scripts.add(f.getAbsolutePath());
                }
            }

            if (!scripts.isEmpty()) {
                pluginScripts.put(
                        folder.getAbsolutePath(),
                        scripts
                );
            }
        }
    }

    // ================= PREF =================

    private boolean isPluginEnabled(String name) {

        return getSharedPreferences(
                "modules",
                MODE_PRIVATE
        ).getBoolean(
                "plugin_" + name,
                false
        );
    }

    // ================= START =================

    private void startPlugin(String folder) {

        List<String> scripts =
                pluginScripts.get(folder);

        if (scripts == null) return;

        StringBuilder cmd =
                new StringBuilder();

        cmd.append("export MODDIR='")
                .append(folder)
                .append("'; ");

        cmd.append("export PATH='/system/bin:/vendor/bin:$PATH'; ");

        for (String s : scripts) {

            if (s.endsWith("post-fs-data.sh")) {

                cmd.append("chmod 755 '")
                        .append(s)
                        .append("'; ");

                cmd.append("sh '")
                        .append(s)
                        .append("' >/dev/null 2>&1; ");
            }
        }

        for (String s : scripts) {

            if (!s.endsWith("post-fs-data.sh")
                    && !s.endsWith("service.sh")) {

                cmd.append("chmod 755 '")
                        .append(s)
                        .append("'; ");

                cmd.append("sh '")
                        .append(s)
                        .append("' >/dev/null 2>&1; ");
            }
        }

        for (String s : scripts) {

            if (s.endsWith("service.sh")) {

                String name =
                        new File(s).getName();

                cmd.append("pkill -f '")
                        .append(name)
                        .append("' 2>/dev/null; ");

                cmd.append("chmod 755 '")
                        .append(s)
                        .append("'; ");

                cmd.append("nohup setsid sh '")
                        .append(s)
                        .append("' >/dev/null 2>&1 & ");
            }
        }

        exec(cmd.toString());
    }

    // ================= STOP =================

    private void stopPlugin(String folder) {

        List<String> scripts =
                pluginScripts.get(folder);

        if (scripts == null) return;

        StringBuilder cmd =
                new StringBuilder();

        for (String s : scripts) {

            String name =
                    new File(s).getName();

            cmd.append("pkill -f '")
                    .append(name)
                    .append("' 2>/dev/null; ");
        }

        exec(cmd.toString());
    }

    // ================= APPLY =================

    private void applyAllPlugins() {

        for (String folder :
                pluginScripts.keySet()) {

            String name =
                    new File(folder).getName();

            if (isPluginEnabled(name)) {
                startPlugin(folder);
            } else {
                stopPlugin(folder);
            }
        }
    }

    // ================= WATCHDOG =================

    private void startWatchdog() {

        worker.postDelayed(new Runnable() {

            @Override
            public void run() {

                for (String folder :
                        pluginScripts.keySet()) {

                    String name =
                            new File(folder).getName();

                    if (!isPluginEnabled(name)) {

                        stopPlugin(folder);
                        continue;
                    }

                    List<String> scripts =
                            pluginScripts.get(folder);

                    if (scripts == null) continue;

                    for (String s : scripts) {

                        if (s.endsWith("service.sh")) {

                            String processName =
                                    new File(s).getName();

                            String check = execRead(
                                    "pidof " + processName
                            );

                            if (check == null
                                    || check.trim().isEmpty()) {

                                startPlugin(folder);
                            }
                        }
                    }
                }

                worker.postDelayed(this, 30000);
            }

        }, 30000);
    }

    // ================= NOTIF =================

    private Notification buildNotif(
            String title,
            String msg
    ) {

        String id = "boost";

        if (Build.VERSION.SDK_INT >= 26) {

            NotificationChannel ch =
                    new NotificationChannel(
                            id,
                            "Booster",
                            NotificationManager.IMPORTANCE_MIN
                    );

            ch.setSound(null, null);
            ch.enableVibration(false);

            nm.createNotificationChannel(ch);
        }

        return new Notification.Builder(this, id)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.boosting)
                .setOngoing(true)
                .build();
    }

    // ================= DESTROY =================

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (worker != null) {
            worker.removeCallbacksAndMessages(null);
        }

        if (workerThread != null) {
            workerThread.quitSafely();
        }

        for (String folder :
                pluginScripts.keySet()) {

            stopPlugin(folder);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}