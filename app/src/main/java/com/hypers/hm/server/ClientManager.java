package com.hypers.hm.server;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks connected client apps on the server side.
 *
 * Package: com.hypers.hm
 */
public abstract class ClientManager<CM extends ConfigManager> {

    private static final String TAG = "ClientManager";

    private final List<ClientRecord> clients = new ArrayList<>();

    /** Returns the ConfigManager instance. */
    public abstract CM getConfigManager();

    /**
     * Registers a new client.
     *
     * @param record the client record to add
     */
    public synchronized void addClient(ClientRecord record) {
        clients.add(record);
        Log.i(TAG, "addClient: uid=" + record.uid + " pkg=" + record.packageName);
    }

    /**
     * Removes a previously registered client.
     *
     * @param record the client record to remove
     */
    public synchronized void removeClient(ClientRecord record) {
        clients.remove(record);
        Log.i(TAG, "removeClient: uid=" + record.uid + " pkg=" + record.packageName);
    }

    /**
     * Finds a client by UID and PID.
     *
     * @param uid calling UID
     * @param pid calling PID
     * @return {@link ClientRecord} or {@code null} if not found
     */
    @Nullable
    public synchronized ClientRecord findClient(int uid, int pid) {
        for (ClientRecord r : clients) {
            if (r.uid == uid && r.pid == pid) return r;
        }
        return null;
    }

    /** Returns a snapshot of all current clients. */
    public synchronized List<ClientRecord> getClients() {
        return new ArrayList<>(clients);
    }

    /**
     * Updates the {@link ClientRecord#allowed} flag for all clients matching the given UID,
     * based on the current config.
     *
     * @param uid target UID
     */
    public synchronized void onPermissionUpdated(int uid) {
        ConfigPackageEntry entry = getConfigManager().find(uid);
        boolean allowed = entry != null && entry.isAllowed();
        for (ClientRecord r : clients) {
            if (r.uid == uid) {
                r.allowed = allowed;
            }
        }
    }
}
