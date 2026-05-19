package com.hypers.hm.server;

import java.util.List;

/**
 * Represents a per-UID permission config entry stored by the Hypers server.
 *
 * Package: com.hypers.hm
 */
public class ConfigPackageEntry {

    public int          uid;
    public List<String> packages;
    public int          flags;

    public ConfigPackageEntry(int uid, List<String> packages, int flags) {
        this.uid      = uid;
        this.packages = packages;
        this.flags    = flags;
    }

    public boolean isAllowed() {
        return (flags & ConfigManager.FLAG_ALLOWED) != 0;
    }

    public boolean isDenied() {
        return (flags & ConfigManager.FLAG_DENIED) != 0;
    }
}
