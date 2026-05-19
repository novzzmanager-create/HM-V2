package com.hypers.hm.receiver

import android.Manifest.permission.WRITE_SECURE_SETTINGS
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.hypers.hm.AppConstants
import com.hypers.hm.HypersSettings
import com.hypers.hm.HypersSettings.LaunchMethod
import com.hypers.hm.adb.AdbClient
import com.hypers.hm.adb.AdbKey
import com.hypers.hm.adb.AdbMdns
import com.hypers.hm.adb.PreferenceAdbKeyStore
import com.hypers.hm.starter.Starter
import com.hypers.hm.utils.UserHandleCompat
import com.hypers.hm.Hypers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_LOCKED_BOOT_COMPLETED != intent.action
            && Intent.ACTION_BOOT_COMPLETED != intent.action) {
            return
        }

        if (UserHandleCompat.myUserId() > 0 || Hypers.pingBinder()) return

        if (HypersSettings.getLastLaunchMode() == LaunchMethod.ROOT) {
            rootStart(context)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            && context.checkSelfPermission(WRITE_SECURE_SETTINGS) == PackageManager.PERMISSION_GRANTED
            && HypersSettings.getLastLaunchMode() == LaunchMethod.ADB) {
            adbStart(context)
        } else {
            Log.w(AppConstants.TAG, "No support start on boot")
        }
    }

    private fun rootStart(context: Context) {
        val isRoot = try {
            val proc = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
            val output = proc.inputStream.bufferedReader().readText()
            proc.waitFor()
            output.contains("uid=0")
        } catch (e: Exception) {
            false
        }

        if (!isRoot) {
            Log.w(AppConstants.TAG, "rootStart: no root access")
            return
        }

        try {
            val proc = Runtime.getRuntime().exec(arrayOf("su", "-c", Starter.internalCommand))
            proc.waitFor()
        } catch (e: Exception) {
            Log.e(AppConstants.TAG, "rootStart exec failed", e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun adbStart(context: Context) {
        val cr = context.contentResolver
        Settings.Global.putInt(cr, "adb_wifi_enabled", 1)
        Settings.Global.putInt(cr, Settings.Global.ADB_ENABLED, 1)
        Settings.Global.putLong(cr, "adb_allowed_connection_time", 0L)
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            val latch = CountDownLatch(1)
            
            // Fixed: Explicitly declared AdbMdns.Callback instead of using a loose trailing lambda
            val adbMdns = AdbMdns(context, AdbMdns.TLS_CONNECT, object : AdbMdns.Callback {
                override fun onChanged(host: String, port: Int) {
                    if (port <= 0) return // Simple return statement now works cleanly here
                    try {
                        val keystore = PreferenceAdbKeyStore(HypersSettings.getPreferences())
                        val key = AdbKey(keystore, "hypers")
                        val client = AdbClient("127.0.0.1", port, key)
                        client.connect()
                        client.shellCommand(Starter.internalCommand, null)
                        client.close()
                    } catch (_: Exception) {
                    }
                    latch.countDown()
                }
            })
            
            if (Settings.Global.getInt(cr, "adb_wifi_enabled", 0) == 1) {
                adbMdns.start()
                latch.await(3, TimeUnit.SECONDS)
                adbMdns.stop()
            }
            pending.finish()
        }
    }
}
