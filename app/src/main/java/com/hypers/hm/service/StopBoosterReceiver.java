package com.hypers.hm.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StopBoosterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("STOP_BOOST".equals(intent.getAction())) {
            context.stopService(new Intent(context, ProBoosterService.class));
        }
    }
}