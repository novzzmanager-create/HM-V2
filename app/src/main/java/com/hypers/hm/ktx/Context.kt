package com.hypers.hm.ktx

import android.content.Context
import android.os.Build
import android.os.UserManager
import com.hypers.hm.HypersApplication

val Context.application: HypersApplication
    get() {
        return applicationContext as HypersApplication
    }

fun Context.createDeviceProtectedStorageContextCompat(): Context {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        createDeviceProtectedStorageContext()
    } else {
        this
    }
}

fun Context.createDeviceProtectedStorageContextCompatWhenLocked(): Context {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && getSystemService(UserManager::class.java)?.isUserUnlocked != true) {
        createDeviceProtectedStorageContext()
    } else {
        this
    }
}