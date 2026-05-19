package com.hypers.hm.broadcast;

import android.app.*;
import android.content.*;
import android.os.Build;
import android.widget.Toast;
import android.util.Log;
import com.hypers.hm.R;

public class NotifyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("PHANTOM", "RECEIVER KE TRIGGER");

        String title = intent.getStringExtra("title");
        String msg = intent.getStringExtra("msg");

        if (title == null) title = "Phantom";
        if (msg == null) msg = "Optimization Done";

        boolean showToast = intent.getBooleanExtra("show_toast", false);
        boolean showNotif = intent.getBooleanExtra("show_notif", false);

        // TOAST
        if (showToast) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }

        // NOTIFICATION
        if (showNotif) {
            NotificationManager nm =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = "phantom channel";

            // Android 8+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        channelId,
                        "Phantom Channel",
                        NotificationManager.IMPORTANCE_HIGH
                );
                nm.createNotificationChannel(channel);
            }

            Notification.Builder builder = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId(channelId);
            }

            nm.notify(1, builder.build());
        }
    }
}