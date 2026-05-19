package com.hypers.hm.authorization

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Parcel
import com.hypers.hm.Manifest
import com.hypers.hm.utils.Logger.LOGGER
import com.hypers.hm.utils.HypersSystemApis
import com.hypers.hm.Hypers

object AuthorizationManager {

    private const val FLAG_ALLOWED = 1 shl 1
    private const val FLAG_DENIED = 1 shl 2
    private const val MASK_PERMISSION = FLAG_ALLOWED or FLAG_DENIED

    // Konstanta Binder transaction — sesuaikan dengan ServerConstants di server lib
    private const val BINDER_TRANSACTION_getApplications = 3

    private fun getApplications(userId: Int): List<PackageInfo> {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()
        return try {
            data.writeInterfaceToken("com.hypers.hm.server.IHypersService")
            data.writeInt(userId)
            try {
                Hypers.getBinder()!!.transact(BINDER_TRANSACTION_getApplications, data, reply, 0)
            } catch (e: Throwable) {
                throw RuntimeException(e)
            }
            reply.readException()
            // ParcelableListSlice tidak tersedia — return empty
            ArrayList()
        } finally {
            reply.recycle()
            data.recycle()
        }
    }

    fun getPackages(): List<PackageInfo> {
        val packages: MutableList<PackageInfo> = ArrayList()
        // isPreV11 / getVersion — fallback ke getApplications
        try {
            packages.addAll(getApplications(-1))
        } catch (e: Throwable) {
            LOGGER.w(e, "getPackages failed")
        }
        return packages
    }

    fun granted(packageName: String, uid: Int): Boolean {
        return try {
            (Hypers.getFlagsForUid(uid, MASK_PERMISSION) and FLAG_ALLOWED) == FLAG_ALLOWED
        } catch (e: Throwable) {
            false
        }
    }

    fun grant(packageName: String, uid: Int) {
        try {
            Hypers.updateFlagsForUid(uid, MASK_PERMISSION, FLAG_ALLOWED)
        } catch (e: Throwable) {
            LOGGER.w(e, "grant failed")
        }
    }

    fun revoke(packageName: String, uid: Int) {
        try {
            Hypers.updateFlagsForUid(uid, MASK_PERMISSION, 0)
        } catch (e: Throwable) {
            LOGGER.w(e, "revoke failed")
        }
    }
}
