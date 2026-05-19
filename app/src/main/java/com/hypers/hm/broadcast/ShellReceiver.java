package com.hypers.hm.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ShellReceiver extends BroadcastReceiver {
    @Override
public void onReceive(Context context, Intent intent) {
    String msg = intent.getStringExtra("toast_hypers");
    if (msg != null) {
        new android.os.Handler(context.getMainLooper()).post(() ->
            Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show()
        );
    }
}
}