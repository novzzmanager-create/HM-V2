package com.hypers.hm;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Reflection-based helper for accessing hidden Android system services.
 *
 * <pre>
 * IBinder pm = SystemServiceHelper.getSystemService("package");
 * IPackageManager ipm = IPackageManager.Stub.asInterface(new HypersBinderWrapper(pm));
 * </pre>
 *
 * Package: com.hypers.hm
 */
@SuppressLint("PrivateApi")
public class SystemServiceHelper {

    private static final String TAG = "SystemServiceHelper";

    private static final Map<String, IBinder> SERVICE_CACHE = new HashMap<>();

    private static Method getService;

    static {
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            getService = sm.getMethod("getService", String.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.w(TAG, "Failed to reflect ServiceManager", e);
        }
    }

    /**
     * Returns the IBinder for the given system service name.
     *
     * @param name service name, e.g. {@code "package"}, {@code "activity"}
     * @return IBinder or {@code null} if not found
     */
    @Nullable
    public static IBinder getSystemService(@NonNull String name) {
        IBinder binder = SERVICE_CACHE.get(name);
        if (binder == null) {
            try {
                binder = (IBinder) getService.invoke(null, name);
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.w(TAG, "getSystemService(" + name + ") failed", e);
            }
            SERVICE_CACHE.put(name, binder);
        }
        return binder;
    }

    /**
     * Clears the internal service cache. Useful after the system server restarts.
     */
    public static void clearCache() {
        SERVICE_CACHE.clear();
    }
}
