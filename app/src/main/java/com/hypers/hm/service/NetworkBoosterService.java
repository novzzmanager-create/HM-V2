package com.hypers.hm.service;

import android.app.Service;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

public class NetworkBoosterService extends Service {

    private Handler handler = new Handler();
    private WifiManager.WifiLock wifiLock;

    @Override
    public void onCreate() {
        super.onCreate();

        startForeground(2, buildNotification("🚀 Network Booster Aktif", "Optimizing Wi-Fi & background apps"));

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "GameBoostLock");
        wifiLock.acquire();

        handler.post(optimizeLoop);
    }

    private Runnable optimizeLoop = new Runnable() {
        @Override
        public void run() {
            ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo proc : am.getRunningAppProcesses()) {
                if (proc.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    try {
                        android.os.Process.killProcess(proc.pid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            handler.postDelayed(this, 2000);
        }
    };

    private Notification buildNotification(String title, String msg) {
        String channelId = "network_booster";
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(channelId, "Network Booster", NotificationManager.IMPORTANCE_LOW);
            nm.createNotificationChannel(ch);
        }

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }

        return builder.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (wifiLock != null && wifiLock.isHeld()) {
            wifiLock.release();
        }

        handler.removeCallbacks(optimizeLoop);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}