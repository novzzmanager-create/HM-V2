package com.hypers.hm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.AttributeSet;
import android.widget.TextView;

public class BatteryView extends TextView {

    private BroadcastReceiver batteryReceiver;
    private boolean isRegistered = false;

    public BatteryView(Context context) {
        super(context);
        init();
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                if (level >= 0 && scale > 0) {
                    int percent = (level * 100) / scale;
                    setText(percent + "%");
                }
            }
        };
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isRegistered) {
            getContext().registerReceiver(batteryReceiver,
                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            isRegistered = true;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isRegistered) {
            try {
                getContext().unregisterReceiver(batteryReceiver);
            } catch (IllegalArgumentException ignored) {
            }
            isRegistered = false;
        }
    }
}