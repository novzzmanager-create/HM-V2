package com.hypers.hm;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry that maps {@link Hypers.UserServiceArgs} to their
 * {@link HypersServiceConnection} instances.
 *
 * Package: com.hypers.hm
 */
class HypersServiceConnections {

    private static final Map<String, HypersServiceConnection> MAP = new HashMap<>();

    private static String key(Hypers.UserServiceArgs args) {
        StringBuilder sb = new StringBuilder();
        sb.append(args.componentName.flattenToString());
        if (args.tag != null) {
            sb.append(':').append(args.tag);
        }
        return sb.toString();
    }

    static synchronized HypersServiceConnection get(Hypers.UserServiceArgs args) {
        String k = key(args);
        HypersServiceConnection conn = MAP.get(k);
        if (conn == null) {
            conn = new HypersServiceConnection(args);
            MAP.put(k, conn);
        }
        return conn;
    }

    static synchronized void remove(HypersServiceConnection conn) {
        MAP.values().remove(conn);
    }
}
