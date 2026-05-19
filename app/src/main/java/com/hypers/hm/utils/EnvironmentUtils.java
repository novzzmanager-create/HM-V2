package com.hypers.hm.utils;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import java.io.File;
import java.lang.reflect.Method;

public class EnvironmentUtils {

    // Sekarang sudah menjadi static murni, aman dipanggil dari HypersSettings
    public static boolean isWatch(Context context) {
        if (context == null) return false;
        UiModeManager uiModeManager = context.getSystemService(UiModeManager.class);
        if (uiModeManager != null) {
            return uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_WATCH;
        }
        return false;
    }

    public static boolean isRooted() {
        String path = System.getenv("PATH");
        if (path != null) {
            String[] dirs = path.split(String.valueOf(File.pathSeparatorChar));
            for (String dir : dirs) {
                if (new File(dir, "su").exists()) {
                    return true;
                }
            }
        }
        return false;
    }

    // PERBAIKAN UTAMA: Ditambahkan "static" agar Error 4 & 13 di AdbpairActivity hilang!
    public static int getAdbTcpPort() {
        int port = getSystemPropertyInt("service.adb.tcp.port", -1);
        if (port == -1) {
            port = getSystemPropertyInt("persist.adb.tcp.port", -1);
        }
        return port;
    }

    private static int getSystemPropertyInt(String key, int defaultValue) {
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("getInt", String.class, int.class);
            Object result = method.invoke(null, key, defaultValue);
            if (result instanceof Integer) {
                return (Integer) result;
            }
        } catch (Exception e) {
            // Abaikan jika terjadi error reflection
        }
        return defaultValue;
    }
}
