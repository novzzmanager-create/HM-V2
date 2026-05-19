package com.hypers.hm;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.hypers.hm.DebugActivity;
import com.hypers.hm.SketchLogger;
import com.google.android.material.color.DynamicColors;
import android.content.res.Configuration;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public class HypersApplication extends Application {

    private static HypersApplication instance;

    public static HypersApplication getInstance() {
        return instance;
    }
    
    private static Context mApplicationContext;

    public static Context getContext() {
        return mApplicationContext;
    }

    @Override
public void onCreate() {
    mApplicationContext = getApplicationContext();
    HypersSettings.initialize(this); // ← TAMBAHKAN INI
    DynamicColors.applyToActivitiesIfAvailable(this);

    Thread.setDefaultUncaughtExceptionHandler(
            new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable throwable) {
                    Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("error", Log.getStackTraceString(throwable));
                    startActivity(intent);
                    SketchLogger.broadcastLog(Log.getStackTraceString(throwable));
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
    SketchLogger.startLogging();
    super.onCreate();
    instance = this;
}
}
