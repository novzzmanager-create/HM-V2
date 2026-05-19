package com.hypers.hm;

import android.os.RemoteException;

/**
 * Provides read/write access to Android system properties through the
 * elevated Hypers server process.
 *
 * Package: com.hypers.hm
 */
public class HypersSystemProperties {

    public static String get(String key) throws RemoteException {
        return Hypers.requireService().getSystemProperty(key, null);
    }

    public static String get(String key, String def) throws RemoteException {
        return Hypers.requireService().getSystemProperty(key, def);
    }

    public static int getInt(String key, int def) throws RemoteException {
        String val = Hypers.requireService().getSystemProperty(key, Integer.toString(def));
        return Integer.decode(val);
    }

    public static long getLong(String key, long def) throws RemoteException {
        String val = Hypers.requireService().getSystemProperty(key, Long.toString(def));
        return Long.decode(val);
    }

    public static boolean getBoolean(String key, boolean def) throws RemoteException {
        String val = Hypers.requireService().getSystemProperty(key, Boolean.toString(def));
        return Boolean.parseBoolean(val);
    }

    public static void set(String key, String value) throws RemoteException {
        Hypers.requireService().setSystemProperty(key, value);
    }
}
