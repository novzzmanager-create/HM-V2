package com.hypers.hm.service;

import android.app.*;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.PixelFormat;
import android.os.*;
import android.provider.Settings;
import android.view.*;
import android.widget.ImageView;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.hypers.hm.R;

public class CrosshairService extends Service {

    private WindowManager wm;
    private View floatView;
    private WindowManager.LayoutParams lp;

    private static boolean isAdded = false;

    public static int posX = 0;
    public static int posY = 0;

    public static int moveX = 0;
    public static int moveY = 0;

    private Handler handler;
    private ImageView crosshair;

    public static final String ACTION_STOP = "STOP_SERVICE";

    @Override
    public void onCreate() {
        super.onCreate();

        startForegroundFix();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName())
            );

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            stopSelf();
            return;
        }

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        int type = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;

        lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                type,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT
        );

        lp.gravity = Gravity.CENTER;

        if (!isAdded) {

            floatView = LayoutInflater.from(this)
                    .inflate(R.layout.float1, null);

            crosshair = floatView.findViewById(R.id.crosshair);

            wm.addView(floatView, lp);

            isAdded = true;
        } else {

            try {
                crosshair = floatView.findViewById(R.id.crosshair);
            } catch (Exception ignored) {}
        }

        handler = new Handler(Looper.getMainLooper());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                posX += moveX;
                posY += moveY;

                lp.x = posX;
                lp.y = posY;

                try {
                    if (floatView != null) {
                        wm.updateViewLayout(floatView, lp);
                    }
                } catch (Exception ignored) {}

                handler.postDelayed(this, 16);
            }
        }, 16);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {

            if (ACTION_STOP.equals(intent.getAction())) {

                stopSelf();
                return START_NOT_STICKY;
            }

            int size = intent.getIntExtra("size", -1);

            if (size != -1 && crosshair != null) {

                ViewGroup.LayoutParams lp2 = crosshair.getLayoutParams();

                lp2.width = size;
                lp2.height = size;

                crosshair.setLayoutParams(lp2);
            }

            int icon = intent.getIntExtra("icon", -1);

            if (icon != -1 && crosshair != null) {

                crosshair.setImageResource(icon);
            }

            int color = intent.getIntExtra("color", -1);

            if (color != -1 && crosshair != null) {

                crosshair.setColorFilter(
                        color,
                        android.graphics.PorterDuff.Mode.SRC_IN
                );
            }
        }

        return START_STICKY;
    }

    private void startForegroundFix() {

        String id = "crosshair_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel ch = new NotificationChannel(
                    id,
                    "hypersManager: Service",
                    NotificationManager.IMPORTANCE_LOW
            );

            getSystemService(NotificationManager.class)
                    .createNotificationChannel(ch);
        }

        Intent stopIntent = new Intent(this, CrosshairService.class);

        stopIntent.setAction(ACTION_STOP);

        PendingIntent stopPending = PendingIntent.getService(
                this,
                0,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        | PendingIntent.FLAG_IMMUTABLE
        );

        Notification n = new NotificationCompat.Builder(this, id)
                .setContentTitle("hypersManager: Service")
                .setContentText("Service sedang berjalan")
                .setSmallIcon(android.R.drawable.ic_menu_compass)
                .addAction(0, "STOP SERVICE", stopPending)
                .setOngoing(true)
                .build();

        if (Build.VERSION.SDK_INT >= 34) {

            startForeground(
                    1,
                    n,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            );

        } else {

            startForeground(1, n);
        }
    }

    @Override
    public void onDestroy() {

        if (handler != null) {

            handler.removeCallbacksAndMessages(null);
        }

        try {

            if (wm != null && floatView != null) {

                wm.removeView(floatView);
            }

        } catch (Exception ignored) {}

        floatView = null;
        crosshair = null;

        isAdded = false;

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}