package com.hypers.hm.service;
import com.hypers.hm.ExecEngine;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.hypers.hm.broadcast.ResetReceiver;

public class DpiServiceUtils extends Service {

    private String pkg = "";

    private boolean isGameRunning = false;

    private Handler handler = new Handler();

    private int userDpInput = 360;
    private int userDpiInputClose = 360;

    @Override
    public void onCreate() {
        super.onCreate();

        loadPrefs();

        // Start foreground
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {

            startForeground(
                100,
                buildNotification("STATUS GAME : TIDAK AKTIF"),
                android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            );

        } else {

            startForeground(
                100,
                buildNotification("STATUS GAME : TIDAK AKTIF")
            );
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        SharedPreferences pref =
            getSharedPreferences("ServicePrefs", Context.MODE_PRIVATE);

        // Kalau service dimatikan user
        if (!pref.getBoolean("isActive", true)) {

            stopForeground(true);
            stopSelf();

            return START_NOT_STICKY;
        }

        loadPrefs();

        handler.removeCallbacks(checkTask);
        handler.post(checkTask);

        return START_STICKY;
    }

    // Load prefs
    private void loadPrefs() {

        SharedPreferences pref =
            getSharedPreferences("ServicePrefs", Context.MODE_PRIVATE);

        pkg = pref.getString(
            "pkg",
            "com.dts.freefireth"
        );

        userDpInput = pref.getInt(
            "dpi",
            360
        );

        userDpiInputClose = pref.getInt(
            "dpiC",
            360
        );
    }

    // Loop checker
    private Runnable checkTask = new Runnable() {
        @Override
        public void run() {

            checkForegroundApp();

            // realtime
            handler.postDelayed(this, 500);
        }
    };

    // Check foreground app
    private void checkForegroundApp() {

        boolean found = isPackageRunning(pkg);

        // Game open
        if (found && !isGameRunning) {

            isGameRunning = true;

            applyDpi(userDpInput);

            updateNotif("STATUS GAME : ACTIVE");

        }

        // Game close
        else if (!found && isGameRunning) {

            isGameRunning = false;

            applyDpi(userDpiInputClose);

            updateNotif("STATUS GAME : DEACTIVE");
        }
    }

    // Detect foreground package
    private boolean isPackageRunning(String targetPkg) {

        try {

            Process process = ExecEngine.newProcess(new String[]{
                        "sh",
                        "-c",
                        "dumpsys window | grep mCurrentFocus"
                    });

            BufferedReader reader =
                new BufferedReader(
                    new InputStreamReader(
                        process.getInputStream()
                    )
                );

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.contains(targetPkg)) {

                    return true;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // Apply DPI
    private void applyDpi(int inputDp) {

        if (!rikka.shizuku.Shizuku.pingBinder()) {
            return;
        }

        try {

            String cmd;

            if (inputDp == 0) {

                cmd = "wm density reset";

            } else {

                DisplayMetrics m =
                    getResources().getDisplayMetrics();

                int width =
                    Math.min(
                        m.widthPixels,
                        m.heightPixels
                    );

                int targetDensity =
                    (160 * width) / inputDp;

                cmd = "wm density " + targetDensity;
            }

            ExecEngine.newProcess(new String[]{
                    "sh",
                    "-c",
                    cmd
                });

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // Update notif
    private void updateNotif(String status) {

        NotificationManager nm =
            (NotificationManager)
            getSystemService(NOTIFICATION_SERVICE);

        nm.notify(
            100,
            buildNotification(status)
        );
    }

    // Notification
    private Notification buildNotification(String statusText) {

        String cid = "hypers-automatic";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManager nm =
                (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

            if (nm.getNotificationChannel(cid) == null) {

                nm.createNotificationChannel(
                    new NotificationChannel(
                        cid,
                        "DPI Status",
                        NotificationManager.IMPORTANCE_LOW
                    )
                );
            }
        }

        // Tombol stop
        Intent stopIntent =
            new Intent(this, ResetReceiver.class);

        PendingIntent stopPI =
            PendingIntent.getBroadcast(
                this,
                2,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT |
                PendingIntent.FLAG_IMMUTABLE
            );

        Notification.Builder nb =
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            ? new Notification.Builder(this, cid)
            : new Notification.Builder(this);

        return nb
            .setSmallIcon(android.R.drawable.ic_menu_compass)

            .setContentTitle("PENGATURAN DPI LAYAR")

            .setStyle(
                new Notification.BigTextStyle().bigText(
                    statusText +
                    "\nPKG : " + pkg +
                    "\nDPI OPEN : " + userDpInput + "dp" +
                    "\nDPI CLOSE : " + userDpiInputClose + "dp"
                )
            )

            .setOngoing(true)

            .setOnlyAlertOnce(true)

            .setShowWhen(false)

            .addAction(
                android.R.drawable.ic_delete,
                "STOP SERVICE",
                stopPI
            )

            .build();
    }

    @Override
    public void onDestroy() {

        handler.removeCallbacks(checkTask);

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}