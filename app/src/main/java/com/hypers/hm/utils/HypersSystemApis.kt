package com.hypers.hm.utils

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.RemoteException
import com.hypers.hm.Hypers
import com.hypers.hm.HypersBinderWrapper

object HypersSystemApis {

    init {
        // SystemServiceBinder.setOnGetBinderListener — rikka.hidden tidak tersedia
        // Inisialisasi dilakukan via Hypers binder langsung
    }

    private val users = arrayListOf<UserInfoCompat>()

    private fun getUsers(): List<UserInfoCompat> {
        return if (!Hypers.pingBinder()) {
            arrayListOf(UserInfoCompat(UserHandleCompat.myUserId(), "Owner"))
        } else try {
            // UserManagerApis tidak tersedia — fallback ke user tunggal
            arrayListOf(UserInfoCompat(UserHandleCompat.myUserId(), "Owner"))
        } catch (tr: Throwable) {
            arrayListOf(UserInfoCompat(UserHandleCompat.myUserId(), "Owner"))
        }
    }

    fun getUsers(useCache: Boolean = true): List<UserInfoCompat> {
        synchronized(users) {
            if (!useCache || users.isEmpty()) {
                users.clear()
                users.addAll(getUsers())
            }
            return users
        }
    }

    fun getUserInfo(userId: Int): UserInfoCompat {
        return getUsers(useCache = true).firstOrNull { it.id == userId }
            ?: UserInfoCompat(UserHandleCompat.myUserId(), "Unknown")
    }

    fun getInstalledPackages(flags: Long, userId: Int): List<PackageInfo> {
        return if (!Hypers.pingBinder()) {
            ArrayList()
        } else try {
            // PackageManagerApis (rikka.hidden) tidak tersedia — fallback
            // Jika project punya akses root/ADB bisa pakai IPackageManager via binder
            ArrayList()
        } catch (tr: RemoteException) {
            throw RuntimeException(tr.message, tr)
        }
    }

    fun checkPermission(permName: String, pkgName: String, userId: Int): Int {
        return if (!Hypers.pingBinder()) {
            PackageManager.PERMISSION_DENIED
        } else try {
            // PermissionManagerApis tidak tersedia — fallback DENIED
            PackageManager.PERMISSION_DENIED
        } catch (tr: RemoteException) {
            throw RuntimeException(tr.message, tr)
        }
    }

    fun grantRuntimePermission(packageName: String, permissionName: String, userId: Int) {
        if (!Hypers.pingBinder()) return
        try {
            // PermissionManagerApis tidak tersedia — no-op atau gunakan IPC langsung
        } catch (tr: RemoteException) {
            throw RuntimeException(tr.message, tr)
        }
    }

    fun revokeRuntimePermission(packageName: String, permissionName: String, userId: Int) {
        if (!Hypers.pingBinder()) return
        try {
            // PermissionManagerApis tidak tersedia — no-op
        } catch (tr: RemoteException) {
            throw RuntimeException(tr.message, tr)
        }
    }
}
