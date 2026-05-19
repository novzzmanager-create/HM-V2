package com.hypers.hm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.os.Debug;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.app.Activity;

import java.security.MessageDigest;
import java.lang.reflect.Method;

public class SecurityChecker {

    private Context context;

    public SecurityChecker(Context context) {
        this.context = context;
    }

    private static final String ORIGINAL_SIGNATURE =
            "5FFA71DFF5F5D0E0A0259A1B6D1AB9C31DD34B4903C2E43FFF81526D69DAB015";

    public boolean isSafe() {
        return !isAppModified() && !isDebuggerDetected();
    }

    private boolean isAppModified() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES
            );

            for (int i = 0; i < info.signatures.length; i++) {
                String hash = sha256(info.signatures[i].toByteArray());
                if (!hash.equalsIgnoreCase(ORIGINAL_SIGNATURE)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    private boolean isDebuggerDetected() {
        return Debug.isDebuggerConnected()
                || Debug.waitingForDebugger();
    }

    private String sha256(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(data);

            StringBuilder sb = new StringBuilder();
            for (byte b : d) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public void showBlockDialog() {

    Activity activity = (Activity) context;

    final com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog =
            new com.google.android.material.bottomsheet.BottomSheetDialog(activity);

    View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.sec, null);
    bottomSheetDialog.setContentView(bottomSheetView);

    bottomSheetDialog.setOnShowListener(dialog -> {
        View sheet = bottomSheetDialog.findViewById(
                com.google.android.material.R.id.design_bottom_sheet
        );

        if (sheet != null) {
            int radius = (int) (28 * context.getResources().getDisplayMetrics().density);

            GradientDrawable bg = new GradientDrawable();
            bg.setColor(0xFF0E0E0E);
            bg.setCornerRadius(radius);

            sheet.setBackground(bg);
        }
    });

    LinearLayout linear1 = bottomSheetView.findViewById(R.id.linear1);
    LinearLayout linear20 = bottomSheetView.findViewById(R.id.linear20);

    TextView textview1 = bottomSheetView.findViewById(R.id.textview1);
    TextView textview2 = bottomSheetView.findViewById(R.id.textview2);
    TextView textview3 = bottomSheetView.findViewById(R.id.textview3);
    TextView textview13 = bottomSheetView.findViewById(R.id.textview13);

    // background
    int d = (int) context.getResources().getDisplayMetrics().density;

    GradientDrawable bgTop = new GradientDrawable();
    bgTop.setColor(0xFF131313);
    bgTop.setCornerRadii(new float[]{
            d*28,d*28,d*28,d*28,0,0,0,0
    });

    linear1.setElevation(d*5);
    linear1.setBackground(bgTop);

    // font
    textview1.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/gffbold.ttf"), 1);
    textview2.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/gffbold.ttf"), 1);
    textview3.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/gffregular.ttf"), 0);
    textview13.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/gffregular.ttf"), 0);

    // tombol exit (INI HARUS TEXTVIEW, BUKAN LINEAR)
    GradientDrawable btnBg = new GradientDrawable();
    btnBg.setCornerRadius(28);
    btnBg.setColor(0x7005C7C2);
    linear20.setBackground(btnBg);

    TextView btnExit = (TextView) linear20.findViewById(R.id.textview13); // pastikan ada di XML

    new CountDownTimer(5000, 1000) {

        int t = 5;

        @Override
        public void onTick(long l) {
            t--;
            if (btnExit != null) {
                btnExit.setText("Exit : " + t + "s");
            }
        }

        @Override
        public void onFinish() {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }.start();

    linear20.setOnClickListener(v -> {
        android.os.Process.killProcess(android.os.Process.myPid());
    });

    bottomSheetDialog.setCancelable(false);
    bottomSheetDialog.show();
}
}