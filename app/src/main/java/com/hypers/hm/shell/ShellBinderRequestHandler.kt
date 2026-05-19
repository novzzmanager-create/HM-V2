package com.hypers.hm.shell

import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.Parcel
import com.hypers.hm.utils.Logger.LOGGER
import com.hypers.hm.Hypers

object ShellBinderRequestHandler {

    fun handleRequest(context: Context, intent: Intent): Boolean {
        if (intent.action != "hypers.manager.hm.intent.action.REQUEST_BINDER") {
            return false
        }

        val binder = intent.getBundleExtra("data")?.getBinder("binder") ?: return false
        val hypersBinder = Hypers.getBinder()
        if (hypersBinder == null) {
            LOGGER.w("Binder not received or Hypers service not running")
        }

        val data = Parcel.obtain()
        return try {
            data.writeStrongBinder(hypersBinder)
            data.writeString(context.applicationInfo.sourceDir)
            binder.transact(1, data, null, IBinder.FLAG_ONEWAY)
            true
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        } finally {
            data.recycle()
        }
    }
}
