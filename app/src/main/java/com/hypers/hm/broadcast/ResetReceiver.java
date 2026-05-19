package com.hypers.hm.broadcast;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import com.hypers.hm.service.DpiServiceUtils;

public class ResetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("STOP_SERVICE".equals(intent.getAction())) {
            // Simpan status agar tidak muncul lagi otomatis
            SharedPreferences pref = context.getSharedPreferences("ServicePrefs", Context.MODE_PRIVATE);
            pref.edit().putBoolean("isActive", false).apply();

            context.stopService(new Intent(context, DpiServiceUtils.class));
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(100);
        }
    }
}
