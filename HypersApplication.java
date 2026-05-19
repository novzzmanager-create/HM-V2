package com.hypers.hm;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
                    Log.e("HypersApp", "Uncaught exception", throwable);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });
    super.onCreate();
    instance = this;
}
}
