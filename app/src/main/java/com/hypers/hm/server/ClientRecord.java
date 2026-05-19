package com.hypers.hm.server;

import android.os.Bundle;
import android.util.Log;

import com.hypers.hm.api.HypersApiConstants;

/**
 * Holds per-client state tracked by the Hypers server.
 *
 * Package: com.hypers.hm
 */
public class ClientRecord {

    private static final String TAG = "ClientRecord";

    public final int    uid;
    public final int    pid;
    public final IHypersApplication client;
    public final String packageName;
    public final int    apiVersion;
    public boolean      allowed;

    public ClientRecord(int uid, int pid, IHypersApplication client,
                        String packageName, int apiVersion) {
        this.uid         = uid;
        this.pid         = pid;
        this.client      = client;
        this.packageName = packageName;
        this.apiVersion  = apiVersion;
        this.allowed     = false;
    }

    public void dispatchRequestPermissionResult(int requestCode, boolean allowed) {
        Bundle reply = new Bundle();
        reply.putBoolean(HypersApiConstants.REQUEST_PERMISSION_REPLY_ALLOWED, allowed);
        try {
            client.dispatchRequestPermissionResult(requestCode, reply);
        } catch (Throwable e) {
            Log.w(TAG, "dispatchRequestPermissionResult failed for " + packageName
                    + " (uid=" + uid + ", pid=" + pid + ")", e);
        }
    }
}
