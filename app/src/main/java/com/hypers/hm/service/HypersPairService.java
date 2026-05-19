package com.hypers.hm.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.hypers.hm.R;
import com.hypers.hm.HypersSettings;
import com.hypers.hm.adb.AdbPairingClient;
import com.hypers.hm.adb.AdbKey;
import com.hypers.hm.adb.PreferenceAdbKeyStore;

public class HypersPairService extends Service {

    private static final String TAG            = "HypersPairService";
    private static final String CHANNEL_ID     = "hypers_pair_chan";
    private static final String ACTION_DO_PAIR = "com.hypers.hm.action.DO_PAIR";
    private static final String KEY_CODE       = "key_pair_code";
    private static final int    NOTIF_SEARCH   = 1000;
    private static final int    NOTIF_PAIR     = 1001;
    private static final int    NOTIF_OK       = 1002;
    private static final int    NOTIF_FAIL     = 1003;

    private NsdManager nsdManager;
    private NsdManager.DiscoveryListener discoveryListener;
    private volatile String foundHost = null;
    private volatile int    foundPort = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        HypersSettings.initialize(this); // ← TAMBAHAN: pastikan preferences siap sebelum dipakai
        createChannel();
        startForeground(NOTIF_SEARCH, buildSearchNotif());
        startDiscovery();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && ACTION_DO_PAIR.equals(intent.getAction())) {
            Bundle extras = RemoteInput.getResultsFromIntent(intent);
            if (extras != null) {
                CharSequence seq = extras.getCharSequence(KEY_CODE);
                if (seq != null) {
                    String code = seq.toString().trim();
                    int port  = intent.getIntExtra("port", foundPort);
                    String host = intent.getStringExtra("host");
                    if (host == null) host = foundHost;
                    if (host == null) host = "127.0.0.1";
                    executePairing(host, port, code);
                }
            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopDiscovery();
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    // ── mDNS discovery ───────────────────────────────────────────────

    private void startDiscovery() {
        nsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);
        if (nsdManager == null) return;

        discoveryListener = new NsdManager.DiscoveryListener() {
            @Override public void onDiscoveryStarted(String s) {
                Log.i(TAG, "mDNS discovery started");
            }
            @Override public void onServiceFound(NsdServiceInfo info) {
                try {
                    nsdManager.resolveService(info, new NsdManager.ResolveListener() {
                        @Override public void onServiceResolved(NsdServiceInfo res) {
                            String host = res.getHost() != null
                                    ? res.getHost().getHostAddress() : "127.0.0.1";
                            int port = res.getPort();
                            foundHost = host;
                            foundPort = port;
                            showPairNotif(host, port);
                        }
                        @Override public void onResolveFailed(NsdServiceInfo s, int e) {}
                    });
                } catch (Exception e) {
                    Log.e(TAG, "resolveService error", e);
                }
            }
            @Override public void onServiceLost(NsdServiceInfo s) {}
            @Override public void onDiscoveryStopped(String s) {}
            @Override public void onStartDiscoveryFailed(String s, int e) {}
            @Override public void onStopDiscoveryFailed(String s, int e) {}
        };

        try {
            nsdManager.discoverServices(
                    "_adb-tls-pairing._tcp",
                    NsdManager.PROTOCOL_DNS_SD,
                    discoveryListener);
        } catch (Exception e) {
            Log.e(TAG, "discoverServices error", e);
        }
    }

    private void stopDiscovery() {
        if (nsdManager != null && discoveryListener != null) {
            try { nsdManager.stopServiceDiscovery(discoveryListener); }
            catch (Exception ignored) {}
        }
    }

    // ── SPAKE2 Pairing via libadb.so ─────────────────────────────────

    private void executePairing(String host, int port, String code) {
        new Thread(() -> {
            Log.i(TAG, "executePairing host=" + host + " port=" + port);
            AdbPairingClient client = null;
            try {
                AdbKey key = new AdbKey(
                        new PreferenceAdbKeyStore(HypersSettings.getPreferences()),
                        "hypers"
                );

                client = new AdbPairingClient(host, port, code, key);
                boolean success = client.start();
                Log.i(TAG, "Pairing status: " + success);

                if (success) {
                    showSuccessNotif();
                } else {
                    showFailNotif("Server menolak kode pairing. Coba lagi.");
                }

            } catch (Exception e) {
                Log.e(TAG, "Pairing error", e);
                String msg = e.getMessage();
                if (msg != null && msg.contains("ECONNREFUSED")) {
                    showFailNotif("Port " + port + " tidak terjangkau.\nPastikan Wireless Debugging aktif.");
                } else {
                    showFailNotif(msg != null ? msg : e.toString());
                }
            } finally {
                if (client != null) {
                    try { client.close(); } catch (Exception ignored) {}
                }
            }
        }, "hypers-pairing").start();
    }

    // ── Notifications ────────────────────────────────────────────────

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(
                    CHANNEL_ID, "Hypers Pairing", NotificationManager.IMPORTANCE_HIGH);
            ch.setShowBadge(false);
            ch.enableVibration(false);
            ch.setSound(null, null);
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (nm != null) nm.createNotificationChannel(ch);
        }
    }

    private Notification buildSearchNotif() {
        return new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.hypers)
                .setContentTitle("HypersManager — Mencari perangkat")
                .setContentText("Menunggu Wireless Debugging aktif...")
                .setOngoing(true)
                .build();
    }

    private void showPairNotif(String host, int port) {
        RemoteInput ri = new RemoteInput.Builder(KEY_CODE)
                .setLabel("Masukkan kode 6-digit dari Wireless Debugging")
                .build();

        Intent replyIntent = new Intent(this, HypersPairService.class);
        replyIntent.setAction(ACTION_DO_PAIR);
        replyIntent.putExtra("port", port);
        replyIntent.putExtra("host", host);

        PendingIntent pi;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getForegroundService(this, 1, replyIntent,
                    PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pi = PendingIntent.getService(this, 1, replyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        Notification.Action action = new Notification.Action.Builder(
                null, "Masukkan Kode Pairing", pi)
                .addRemoteInput(ri)
                .build();

        Notification notif = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.hypers)
                .setContentTitle("✅ Wireless Debugging ditemukan!")
                .setContentText("Port: " + port + " | Masukkan kode dari menu 'Pair device with pairing code'")
                .setColor(0xFF00C853)
                .setOngoing(true)
                .addAction(action)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm != null) {
            nm.cancel(NOTIF_SEARCH);
            nm.notify(NOTIF_PAIR, notif);
        }
        startForeground(NOTIF_PAIR, notif);
    }

    private void showSuccessNotif() {
        Notification notif = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.hypers)
                .setContentTitle("✅ Pairing Berhasil!")
                .setContentText("HypersManager terhubung via Wireless ADB")
                .setColor(0xFF00C853)
                .setAutoCancel(true)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm != null) {
            nm.cancel(NOTIF_PAIR);
            nm.notify(NOTIF_OK, notif);
        }
        stopForeground(true);
        try {
            Intent broadcast = new Intent("com.hypers.hm.action.PAIR_SUCCESS");
            broadcast.setPackage(getPackageName());
            sendBroadcast(broadcast);
        } catch (Exception ignored) {}
        stopSelf();
    }

    private void showFailNotif(String error) {
        Notification notif = new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.hypers)
                .setContentTitle("❌ Pairing Gagal")
                .setContentText(error == null || error.isEmpty()
                        ? "Kode salah atau koneksi terputus" : error)
                .setColor(0xFFFF5252)
                .setAutoCancel(true)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (nm != null) nm.notify(NOTIF_FAIL, notif);
    }
}