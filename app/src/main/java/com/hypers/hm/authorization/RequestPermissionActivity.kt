package com.hypers.hm.authorization

import android.app.Dialog
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hypers.hm.Hypers
import com.hypers.hm.api.HypersApiConstants.REQUEST_PERMISSION_REPLY_ALLOWED
import com.hypers.hm.utils.Logger.LOGGER
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class RequestPermissionActivity : AppCompatActivity() {

    companion object {
        // Defined locally — missing from HypersApiConstants.java
        private const val REQUEST_PERMISSION_REPLY_IS_ONETIME = "is_onetime"
    }

    private lateinit var dialog: Dialog

    private fun setResult(requestUid: Int, requestPid: Int, requestCode: Int, allowed: Boolean, onetime: Boolean) {
        val data = Bundle()
        data.putBoolean(REQUEST_PERMISSION_REPLY_ALLOWED, allowed)
        data.putBoolean(REQUEST_PERMISSION_REPLY_IS_ONETIME, onetime)
        try {
            Hypers.dispatchPermissionConfirmationResult(requestUid, requestPid, requestCode, data)
        } catch (e: Throwable) {
            LOGGER.e("dispatchPermissionConfirmationResult: ${e.message}")
        }
    }

    private fun checkSelfPermission(): Boolean {
        return Hypers.checkRemotePermission("android.permission.GRANT_RUNTIME_PERMISSIONS") == PackageManager.PERMISSION_GRANTED
    }

    private fun waitForBinder(): Boolean {
        if (Hypers.pingBinder()) return true

        val countDownLatch = CountDownLatch(1)
        val listener = object : Hypers.OnBinderReceivedListener {
            override fun onBinderReceived() {
                countDownLatch.countDown()
                Hypers.removeBinderReceivedListener(this)
            }
        }
        Hypers.addBinderReceivedListenerSticky(listener, Handler(Looper.getMainLooper()))

        return try {
            countDownLatch.await(5, TimeUnit.SECONDS)
            true
        } catch (e: Exception) {
            LOGGER.e("Binder not received in 5s")
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!waitForBinder()) {
            finish()
            return
        }

        val uid = intent.getIntExtra("uid", -1)
        val pid = intent.getIntExtra("pid", -1)
        val requestCode = intent.getIntExtra("requestCode", -1)
        val ai = intent.getParcelableExtra<ApplicationInfo>("applicationInfo")

        if (uid == -1 || pid == -1 || ai == null) {
            finish()
            return
        }

        if (!checkSelfPermission()) {
            setResult(uid, pid, requestCode, allowed = false, onetime = true)
            MaterialAlertDialogBuilder(this)
                .setTitle("Hypers: ADB permission limited")
                .setMessage("ADB shell tidak punya izin GRANT_RUNTIME_PERMISSIONS.")
                .setPositiveButton(android.R.string.ok, null)
                .setOnDismissListener { finish() }
                .show()
            return
        }

        val label = try {
            ai.loadLabel(packageManager)
        } catch (e: Exception) {
            ai.packageName
        }

        dialog = MaterialAlertDialogBuilder(this)
            .setTitle("Izinkan $label menggunakan Hypers?")
            .setMessage("Aplikasi ini akan mendapat akses privilege melalui Hypers Manager.")
            .setPositiveButton("Izinkan") { _, _ ->
                setResult(uid, pid, requestCode, allowed = true, onetime = false)
                dialog.dismiss()
            }
            .setNegativeButton("Tolak (Sekali)") { _, _ ->
                setResult(uid, pid, requestCode, allowed = false, onetime = true)
                dialog.dismiss()
            }
            .setCancelable(false)
            .setOnDismissListener { finish() }
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}