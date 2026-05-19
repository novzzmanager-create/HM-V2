package com.hypers.hm.shell;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;

import com.hypers.hm.rish.Rish;
import com.hypers.hm.rish.RishConfig;
import com.hypers.hm.Hypers;
import com.hypers.hm.api.HypersApiConstants;

public class Shell extends Rish {

    @Override
    public void requestPermission(Runnable onGrantedRunnable) {
        if (Hypers.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
            onGrantedRunnable.run();
        } else if (Hypers.shouldShowRequestPermissionRationale()) {
            System.err.println("Permission denied");
            System.err.flush();
            System.exit(1);
        } else {
            Hypers.addRequestPermissionResultListener(new Hypers.OnRequestPermissionResultListener() {
                @Override
                public void onRequestPermissionResult(int requestCode, int grantResult) {
                    Hypers.removeRequestPermissionResultListener(this);

                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        onGrantedRunnable.run();
                    } else {
                        System.err.println("Permission denied");
                        System.err.flush();
                        System.exit(1);
                    }
                }
            });
            Hypers.requestPermission(0);
        }
    }

    public static void main(String[] args, String packageName, IBinder binder, Handler handler) {
        RishConfig.init(binder, HypersApiConstants.BINDER_DESCRIPTOR, 30000);
        Hypers.onBinderReceived(binder, packageName);
        Hypers.addBinderReceivedListenerSticky(() -> {
            int version = Hypers.getVersion();
            if (version < 12) {
                System.err.println("Rish requires server 12 (running " + version + ")");
                System.err.flush();
                System.exit(1);
            }
            new Shell().start(args);
        });
    }
}
