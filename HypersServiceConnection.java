package com.hypers.hm;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

import com.hypers.hm.server.IHypersServiceConnection;

/**
 * Internal Binder implementation of {@link IHypersServiceConnection}.
 * Dispatches connected / died callbacks to registered {@link ServiceConnection}s.
 *
 * Package: com.hypers.hm
 */
class HypersServiceConnection extends IHypersServiceConnection.Stub {

    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    private final Set<ServiceConnection> connections = new HashSet<>();
    private final ComponentName componentName;
    private IBinder binder;
    private boolean dead = false;

    HypersServiceConnection(Hypers.UserServiceArgs args) {
        this.componentName = args.componentName;
    }

    void addConnection(@Nullable ServiceConnection conn) {
        if (conn != null) connections.add(conn);
    }

    void removeConnection(@Nullable ServiceConnection conn) {
        if (conn != null) connections.remove(conn);
    }

    void clearConnections() {
        connections.clear();
    }

    @Override
    public void connected(IBinder binder) {
        MAIN_HANDLER.post(() -> {
            for (ServiceConnection conn : connections) {
                conn.onServiceConnected(componentName, binder);
            }
        });

        this.binder = binder;
        try {
            this.binder.linkToDeath(this::died, 0);
        } catch (RemoteException ignored) {
        }
    }

    @Override
    public void died() {
        binder = null;
        if (dead) return;
        dead = true;

        MAIN_HANDLER.post(() -> {
            for (ServiceConnection conn : connections) {
                conn.onServiceDisconnected(componentName);
            }
            connections.clear();
            HypersServiceConnections.remove(this);
        });
    }
}
