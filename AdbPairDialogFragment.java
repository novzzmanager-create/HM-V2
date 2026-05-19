package com.hypers.hm;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hypers.hm.adb.AdbPairingService;

@RequiresApi(api = Build.VERSION_CODES.R)
public class AdbPairDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context ctx = requireContext();

        return new MaterialAlertDialogBuilder(ctx)
                .setTitle("Wireless Debugging Pairing")
                .setMessage("1. Tap \"Mulai Pairing\"\n2. Buka Wireless Debugging → Pair device with pairing code\n3. Masukkan kode 6 digit dari notifikasi yang muncul")
                .setNegativeButton("Batal", (d, w) -> stopService(ctx))
                .setPositiveButton("Mulai Pairing", (d, w) -> {
                    startService(ctx);
                    openWirelessDebugging(ctx);
                })
                .create();
    }

    private void startService(Context ctx) {
        // Amankan pemanggilan menggunakan helper startIntent dari Kotlin
        Intent intent = AdbPairingService.Companion.startIntent(ctx);
        
        // Aturan ketat Android 12+ (Wajib startForegroundService)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ctx.startForegroundService(intent);
        } else {
            ctx.startService(intent);
        }
    }

    private void stopService(Context ctx) {
        // Gunakan intent stop yang benar agar tidak memicu crash di background
        Intent intent = new Intent(ctx, AdbPairingService.class);
        intent.setAction("stop");
        
        // Untuk stop, startService biasa aman, atau startForegroundService juga boleh
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ctx.startForegroundService(intent);
        } else {
            ctx.startService(intent);
        }
    }

    private void openWirelessDebugging(Context ctx) {
        try {
            Intent i = new Intent("com.android.settings.WIRELESS_DEBUGGING_SETTINGS");
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(i);
        } catch (ActivityNotFoundException e1) {
            try {
                Intent i = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i);
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(ctx,
                        "Buka Settings → Developer Options → Wireless Debugging",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void show(FragmentManager fragmentManager) {
        if (fragmentManager.isStateSaved()) return;
        show(fragmentManager, getClass().getSimpleName());
    }

    @Nullable
    @Override
    public AlertDialog getDialog() {
        return (AlertDialog) super.getDialog();
    }
}
