package com.hypers.hm.debug;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

public class AdbMdns {
    private static final String TAG = "AdbMdns";
    public static final String TLS_PAIRING = "_adb-tls-pairing._tcp.";
    public static final String TLS_CONNECT = "_adb-tls-connect._tcp.";

    private final NsdManager nsdManager;
    private NsdManager.DiscoveryListener listener;
    private final OnPortFoundListener callback;
    private final String serviceType;
    private boolean isRunning = false;

    public interface OnPortFoundListener {
        void onPortFound(String host, int port);
    }

    public AdbMdns(Context context, String serviceType, OnPortFoundListener callback) {
        this.nsdManager  = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        this.serviceType = serviceType;
        this.callback    = callback;
    }

    public void start() {
        if (isRunning) return;
        isRunning = true;

        listener = new NsdManager.DiscoveryListener() {

            @Override
            public void onDiscoveryStarted(String s) {
                Log.d(TAG, "Discovery started: " + s);
            }

            @Override
            public void onServiceFound(NsdServiceInfo info) {
                Log.d(TAG, "Service found: " + info.getServiceName());
                try {
                    nsdManager.resolveService(info, new NsdManager.ResolveListener() {
                        @Override
                        public void onServiceResolved(NsdServiceInfo res) {
                            String host = res.getHost() != null
                                    ? res.getHost().getHostAddress()
                                    : "127.0.0.1";
                            int port = res.getPort();
                            Log.d(TAG, "Resolved: " + host + ":" + port);

                            // Terima semua IP — tidak dibatasi localhost saja
                            if (port > 0) {
                                callback.onPortFound(host, port);
                            }
                        }

                        @Override
                        public void onResolveFailed(NsdServiceInfo s, int e) {
                            Log.w(TAG, "Resolve failed: " + e);
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "resolveService error", e);
                }
            }

            @Override public void onServiceLost(NsdServiceInfo s) {}
            @Override public void onDiscoveryStopped(String s) { isRunning = false; }
            @Override public void onStartDiscoveryFailed(String s, int e) {
                Log.e(TAG, "Start discovery failed: " + e);
                isRunning = false;
            }
            @Override public void onStopDiscoveryFailed(String s, int e) {}
        };

        try {
            nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, listener);
        } catch (Exception e) {
            Log.e(TAG, "discoverServices error", e);
            isRunning = false;
        }
    }

    public void stop() {
        if (nsdManager != null && listener != null && isRunning) {
            try {
                nsdManager.stopServiceDiscovery(listener);
            } catch (Exception ignored) {}
        }
        isRunning = false;
    }
}
