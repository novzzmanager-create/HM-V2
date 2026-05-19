package com.hypers.hm;

import rikka.shizuku.Shizuku;
import rikka.shizuku.ShizukuRemoteProcess;
import com.hypers.hm.Hypers;
import com.hypers.hm.HypersRemoteProcess;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShizukuUtil {

private static final String TAG = "ShizukuUtil";

    public static String exec(String cmd) {
        StringBuilder output = new StringBuilder();

        try {
            ShizukuRemoteProcess process = new ShizukuRemoteProcess(
                new String[]{"sh", "-c", cmd},
                null,
                null
            );

            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return output.toString();
    }
    
    
    public static String execHm(String cmd) {
        StringBuilder output = new StringBuilder();
        try {
            if (!Hypers.pingBinder()) {
                // Fallback ke Runtime jika Hypers belum aktif
                java.lang.Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", cmd});
                java.io.BufferedReader reader = new java.io.BufferedReader(
                    new InputStreamReader(p.getInputStream())
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                p.waitFor();
                return output.toString();
            }

            HypersRemoteProcess process = new HypersRemoteProcess(
                Hypers.requireService().newProcess(
                    new String[]{"sh", "-c", cmd}, null, null
                )
            );
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (Throwable e) {
            Log.e(TAG, "exec failed: " + cmd, e);
        }
        return output.toString();
    }
}