package com.hypers.hm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import android.util.Log;

import rikka.shizuku.Shizuku;
import com.hypers.hm.Hypers;
import com.hypers.hm.HypersRemoteProcess;

public class ExecEngine {

    private static Boolean isRoot = null;
    private static final String TAG = "ExecEngine";

    public static boolean isRootAvailable() {
        try {
            Process p = Runtime.getRuntime().exec("su");
            p.getOutputStream().write("exit\n".getBytes());
            p.getOutputStream().flush();
            p.waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isRootAvailableCached() {
        if (isRoot == null) {
            isRoot = isRootAvailable();
        }
        return isRoot;
    }

    public static void exec(String cmd) {
        if (isRootAvailableCached()) {
            execRoot(cmd);
        } else {
            execShizuku(cmd);
        }
    }

    public static String execRead(String cmd) {
        return isRootAvailableCached() ? execRootRead(cmd) : execShizukuRead(cmd);
    }

    private static void execRoot(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            java.io.DataOutputStream os = new java.io.DataOutputStream(p.getOutputStream());
            os.writeBytes(cmd + "\nexit\n");
            os.flush();
            p.waitFor();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static String execRootRead(String cmd) {
        StringBuilder out = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec("su");
            java.io.DataOutputStream os = new java.io.DataOutputStream(p.getOutputStream());
            os.writeBytes(cmd + "\nexit\n");
            os.flush();

            java.io.BufferedReader r = new java.io.BufferedReader(
                new java.io.InputStreamReader(p.getInputStream())
            );

            String line;
            while ((line = r.readLine()) != null) {
                out.append(line).append("\n");
            }

            r.close();
            p.waitFor();
        } catch (Exception e) { e.printStackTrace(); }

        return out.toString();
    }

    private static void execShizuku(String cmd){
        try{
            rikka.shizuku.Shizuku.newProcess(
                new String[]{"sh","-c",cmd},
                null,
                null
            );
        }catch(Exception e){ e.printStackTrace(); }
    }

    private static String execShizukuRead(String cmd){
        StringBuilder out = new StringBuilder();
        try{
            Process p = rikka.shizuku.Shizuku.newProcess(
                new String[]{"sh","-c",cmd},
                null,
                null
            );

            java.io.BufferedReader r = new java.io.BufferedReader(
                new java.io.InputStreamReader(p.getInputStream())
            );

            String line;
            while((line = r.readLine()) != null){
                out.append(line).append("\n");
            }

            r.close();
            p.waitFor();

        }catch(Exception e){ e.printStackTrace(); }

        return out.toString();
    }
    
    
    private static void execHypers(String cmd) {
        try {
            if (!Hypers.pingBinder()) return;
            HypersRemoteProcess process = new HypersRemoteProcess(
                Hypers.requireService().newProcess(
                    new String[]{"sh", "-c", cmd}, null, null
                )
            );
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "execHypers failed", e);
        }
    }

    private static String execHypersRead(String cmd) {
        StringBuilder out = new StringBuilder();
        try {
            if (!Hypers.pingBinder()) return "";
            HypersRemoteProcess process = new HypersRemoteProcess(
                Hypers.requireService().newProcess(
                    new String[]{"sh", "-c", cmd}, null, null
                )
            );
            BufferedReader r = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = r.readLine()) != null) {
                out.append(line).append("\n");
            }
            r.close();
            process.waitFor();
        } catch (Exception e) {
            Log.e(TAG, "execHypersRead failed", e);
        }
        return out.toString();
    }
}