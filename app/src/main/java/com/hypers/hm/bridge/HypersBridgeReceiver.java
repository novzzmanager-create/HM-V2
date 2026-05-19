package com.hypers.hm.bridge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hypers.hm.Hypers;
import com.hypers.hm.api.HypersApiConstants;

/**
 * Optional BroadcastReceiver that handles Hypers command intents.
 *
 * <p>Register in AndroidManifest.xml (exported=false for internal use):</p>
 * <pre>{@code
 * <receiver
 *     android:name="com.hypers.hm.bridge.HypersBridgeReceiver"
 *     android:exported="false">
 *     <intent-filter>
 *         <action android:name="com.hypers.hm.action.START_PAIRING" />
 *         <action android:name="com.hypers.hm.action.START_ROOT" />
 *         <action android:name="com.hypers.hm.action.START_ADB" />
 *         <action android:name="com.hypers.hm.action.START_COMPUTER" />
 *         <action android:name="com.hypers.hm.action.STOP_SERVICE" />
 *     </intent-filter>
 * </receiver>
 * }</pre>
 *
 * Package: com.hypers.hm
 */
public class HypersBridgeReceiver extends BroadcastReceiver {

    private static final String TAG = "HypersBridgeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) return;

        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);

        switch (action) {
            case HypersApiConstants.CMD_START_PAIRING:
                HypersPairingManager.startPairing(context);
                break;

            case HypersApiConstants.CMD_START_ROOT:
                if (Hypers.pingBinder()) {
                    HypersPairingManager.startOnRoot()
                            .execute("id", result -> Log.i(TAG, "root id: " + result));
                } else {
                    Log.w(TAG, "CMD_START_ROOT: binder not ready");
                }
                break;

            case HypersApiConstants.CMD_START_ADB:
                if (Hypers.pingBinder()) {
                    HypersPairingManager.startOnAdb()
                            .execute("id", result -> Log.i(TAG, "adb id: " + result));
                } else {
                    Log.w(TAG, "CMD_START_ADB: binder not ready");
                }
                break;

            case HypersApiConstants.CMD_START_COMPUTER:
                HypersPairingManager.startOnComputer(context);
                break;

            case HypersApiConstants.CMD_STOP_SERVICE:
                HypersPairingManager.stopService(context);
                break;

            default:
                Log.w(TAG, "Unknown action: " + action);
        }
    }
}
