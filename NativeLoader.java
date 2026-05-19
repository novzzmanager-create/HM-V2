package com.hypers.hm;

public class NativeLoader {

    static {
        System.loadLibrary("nativehypers");
    }

    public native void optimizeArray(float[] data);
    public native void neonBoost(float[] a, float[] b, float[] result);
    public native String getCPUInfo();

    public void smoothArray(float[] data) {
        optimizeArray(data);
    }

    public void addArrays(float[] a, float[] b, float[] result) {
        neonBoost(a, b, result);
    }
}