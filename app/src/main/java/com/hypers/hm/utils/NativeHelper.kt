package com.hypers.hm.utils

object NativeHelper {
    @JvmStatic
    external fun setSELinuxContext(name: String): Int

    init {
        System.loadLibrary("adb")
    }
}
