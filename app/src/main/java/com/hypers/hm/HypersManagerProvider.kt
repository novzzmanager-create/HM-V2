package com.hypers.hm

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hypers.hm.api.BinderContainer
import com.hypers.hm.utils.Logger
import com.hypers.hm.Hypers
import com.hypers.hm.HypersProvider
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class HypersManagerProvider : HypersProvider() {

    companion object {
        private const val EXTRA_BINDER = "hypers.manager.hm.privileged.api.intent.extra.BINDER"
        private const val METHOD_SEND_USER_SERVICE = "sendUserService"
        // USER_SERVICE_ARG_TOKEN defined inline — was missing from HypersApiConstants
        private const val USER_SERVICE_ARG_TOKEN = "token"
        private val LOGGER get() = Logger.LOGGER
    }

    override fun onCreate(): Boolean {
        // disableAutomaticSuiInitialization() removed — Sui not used in this build
        return super.onCreate()
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        if (extras == null) return null

        return if (method == METHOD_SEND_USER_SERVICE) {
            try {
                extras.classLoader = BinderContainer::class.java.classLoader

                val token = extras.getString(USER_SERVICE_ARG_TOKEN) ?: return null
                val binder = extras.getParcelable<BinderContainer>(EXTRA_BINDER)?.binder ?: return null

                val countDownLatch = CountDownLatch(1)
                var reply: Bundle? = Bundle()

                val listener = object : Hypers.OnBinderReceivedListener {
                    override fun onBinderReceived() {
                        try {
                            // bundleOf replaced with manual Bundle to avoid androidx.core dependency issues
                            val args = Bundle()
                            args.putString(USER_SERVICE_ARG_TOKEN, token)
                            Hypers.attachUserService(binder, args)
                            reply!!.putParcelable(EXTRA_BINDER, BinderContainer(Hypers.getBinder()))
                        } catch (e: Throwable) {
                            LOGGER.e(e, "attachUserService $token")
                            reply = null
                        }

                        Hypers.removeBinderReceivedListener(this)
                        countDownLatch.countDown()
                    }
                }

                Hypers.addBinderReceivedListenerSticky(listener, Handler(Looper.getMainLooper()))

                return try {
                    countDownLatch.await(5, TimeUnit.SECONDS)
                    reply
                } catch (e: Exception) {
                    LOGGER.e(e, "Binder not received in 5s")
                    null
                }
            } catch (e: Throwable) {
                LOGGER.e(e, "sendUserService")
                null
            }
        } else {
            super.call(method, arg, extras)
        }
    }
}