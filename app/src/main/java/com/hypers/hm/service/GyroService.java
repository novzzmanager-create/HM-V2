package com.hypers.hm.service;

import android.app.*;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Build;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.*;
import android.widget.ImageView;

import androidx.core.app.NotificationCompat;

import com.hypers.hm.R;

public class GyroService extends Service
        implements SensorEventListener {

    private WindowManager wm;
    private View gyroView;
    private WindowManager.LayoutParams lp;

    private SensorManager sensorManager;
    private Sensor gravitySensor;

    private ImageView gyroCircle;

    public static int posX = 0;
    public static int posY = 0;

    private float currentX = 0f;
    private float currentY = 0f;

    private float targetX = 0f;
    private float targetY = 0f;

    private final float SENSITIVITY = 9f;

    private final float SMOOTHING = 0.08f;

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

        wm = (WindowManager)
                getSystemService(WINDOW_SERVICE);

        sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        int type;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            type =
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        } else {

            type =
                    WindowManager.LayoutParams.TYPE_PHONE;
        }

        lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                type,

                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,

                PixelFormat.TRANSLUCENT
        );

        lp.gravity = Gravity.CENTER;

        lp.x = posX;
        lp.y = posY;

        gyroView = LayoutInflater.from(this)
                .inflate(R.layout.gyro_layout, null);

        gyroCircle =
                gyroView.findViewById(
                        R.id.gyro_guide_circle
                );

        gyroCircle.setVisibility(View.VISIBLE);

        gyroCircle.setAlpha(1f);

        try {

            wm.addView(gyroView, lp);

        } catch (Exception e) {

            android.util.Log.e(
                    "GYRO",
                    "ADD VIEW ERROR",
                    e
            );
        }

        gravitySensor =
                sensorManager.getDefaultSensor(
                        Sensor.TYPE_GRAVITY
                );

        if (gravitySensor != null) {

            sensorManager.registerListener(
                    this,
                    gravitySensor,
                    SensorManager.SENSOR_DELAY_GAME
            );

        } else {

            android.util.Log.e(
                    "GYRO",
                    "NO GRAVITY SENSOR"
            );
        }
    }

    @Override
public void onSensorChanged(SensorEvent event) {

    if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {

        float x = event.values[0];
        float y = event.values[1];

        targetX = -x * SENSITIVITY * 6f;
        targetY = y * SENSITIVITY * 6f;

        currentX += (targetX - currentX) * SMOOTHING;
        currentY += (targetY - currentY) * SMOOTHING;

        float centerX =
                (gyroView.getWidth() - gyroCircle.getWidth()) / 2f;

        float centerY =
                (gyroView.getHeight() - gyroCircle.getHeight()) / 2f;

        gyroCircle.setX(centerX + currentX);

        gyroCircle.setY(centerY + currentY);

        gyroCircle.setRotation(
                -currentX * 0.8f
        );
    }
}

    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId
    ) {

        lp.x = posX;
        lp.y = posY;

        try {

            if (gyroView != null) {

                wm.updateViewLayout(
                        gyroView,
                        lp
                );
            }

        } catch (Exception ignored) {
        }

        return START_STICKY;
    }

    private void startForegroundFix() {

        String id = "gyro_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel ch =
                    new NotificationChannel(
                            id,
                            "Gyro Service",
                            NotificationManager.IMPORTANCE_LOW
                    );

            getSystemService(
                    NotificationManager.class
            ).createNotificationChannel(ch);
        }

        Notification n =
                new NotificationCompat.Builder(
                        this,
                        id
                )
                        .setSmallIcon(
                                android.R.drawable.ic_menu_compass
                        )
                        .setContentTitle(
                                "Gyro Service"
                        )
                        .setContentText(
                                "Gyro aktif"
                        )
                        .setOngoing(true)
                        .build();

        if (Build.VERSION.SDK_INT >= 34) {

            startForeground(
                    2,
                    n,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            );

        } else {

            startForeground(2, n);
        }
    }

    @Override
    public void onDestroy() {

        if (sensorManager != null) {

            sensorManager.unregisterListener(
                    this
            );
        }

        try {

            if (wm != null &&
                    gyroView != null) {

                wm.removeView(
                        gyroView
                );
            }

        } catch (Exception ignored) {
        }

        gyroView = null;
        gyroCircle = null;

        super.onDestroy();
    }

    @Override
    public void onAccuracyChanged(
            Sensor sensor,
            int accuracy
    ) {
    }

    @Override
    public IBinder onBind(
            Intent intent
    ) {
        return null;
    }
}