package com.hypers.hm.server;

import androidx.annotation.Nullable;

import com.hypers.hm.api.HypersApiConstants;

import java.util.List;

/**
 * Abstract base for the Hypers server-side permission configuration store.
 *
 * Package: com.hypers.hm
 */
public abstract class ConfigManager {

    public static final int FLAG_ALLOWED    = HypersApiConstants.FLAG_ALLOWED;
    public static final int FLAG_DENIED     = HypersApiConstants.FLAG_DENIED;
    public static final int MASK_PERMISSION = HypersApiConstants.MASK_PERMISSION;

    /** Find config entry for the given UID. */
    @Nullable
    public abstract ConfigPackageEntry find(int uid);

    /** Update permission flags for the given UID and package list. */
    public abstract void update(int uid, List<String> packages, int mask, int values);

    /** Remove the config entry for the given UID. */
    public abstract void remove(int uid);
}
