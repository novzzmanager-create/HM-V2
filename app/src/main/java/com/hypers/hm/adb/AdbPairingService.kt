package com.hypers.hm.adb

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.wifi.WifiManager
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import com.hypers.hm.R
import com.hypers.hm.HypersSettings
import java.net.ConnectException

@TargetApi(Build.VERSION_CODES.R)
class AdbPairingService : Service() {

    companion object {
        const val notificationChannel = "adb_pairing"
        private const val tag = "HypersPairingService"

        private const val notificationId = 1998
        private const val replyRequestId = 101
        private const val stopRequestId = 102
        
        private const val startAction = "start"
        private const val stopAction = "stop"
        private const val replyAction = "reply"

        private const val remoteInputResultKey = "paring_code"
        private const val portKey = "adb_pair_port"

        fun startIntent(context: Context): Intent =
            Intent(context, AdbPairingService::class.java).setAction(startAction)

        private fun stopIntent(context: Context): Intent =
            Intent(context, AdbPairingService::class.java).setAction(stopAction)

        private fun replyIntent(context: Context, port: Int): Intent =
            Intent(context, AdbPairingService::class.java)
                .setAction(replyAction)
                .putExtra(portKey, port)
    }

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val mainHandler = Handler(Looper.getMainLooper())

    private var adbMdns: AdbMdns? = null
    @Volatile private var started = false
    private var multicastLock: WifiManager.MulticastLock? = null

    private val notificationManager: NotificationManager
        get() = getSystemService(NotificationManager::class.java)

    private val mdnsCallback = object : AdbMdns.Callback {
        override fun onChanged(host: String, port: Int) {
            if (port <= 0 || !started) return

            mainHandler.post {
                if (!started) return@post
                try {
                    // OTOMATIS: Notifikasi searching langsung berubah jadi Input Box begitu port tertangkap radar queue
                    notificationManager.notify(notificationId, createInputNotification(port))
                    Log.d(tag, "Notifikasi Input Box Berhasil Ditampilkan Otomatis untuk Port: $port")
                } catch (e: Exception) {
                    Log.e(tag, "Gagal mengupdate notifikasi", e)
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupNotificationChannel()
        acquireMulticastLock()
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannel,
                getString(R.string.notification_channel_adb_pairing),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(null, null)
                enableLights(false)
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun acquireMulticastLock() {
        try {
            val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            multicastLock = wifiManager.createMulticastLock("HypersMdnsLock").apply {
                setReferenceCounted(false)
                acquire()
            }
        } catch (e: Exception) {
            Log.e(tag, "Gagal mengaktifkan Multicast Lock", e)
        }
    }

    private fun releaseMulticastLock() {
        try {
            multicastLock?.let { if (it.isHeld) it.release() }
            multicastLock = null
        } catch (e: Exception) {
            Log.e(tag, "Gagal melepas Multicast Lock", e)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null || intent.action == null) {
            stopSelf()
            return START_NOT_STICKY
        }

        val action = intent.action
        if (action == startAction || action == replyAction) {
            val initialNotification = if (action == startAction) searchingNotification else workingNotification
            startForegroundSafe(initialNotification)
        }

        when (action) {
            startAction -> {
                startSearch()
            }

            replyAction -> {
                val results = RemoteInput.getResultsFromIntent(intent)
                val code = results?.getCharSequence(remoteInputResultKey)?.toString() ?: ""
                val port = intent.getIntExtra(portKey, -1)

                if (code.isNotEmpty() && port > 0) {
                    onInput(code, port)
                } else {
                    startForegroundSafe(searchingNotification)
                    startSearch()
                }
            }

            stopAction -> {
                startForegroundSafe(workingNotification)
                executeShutdown()
                return START_NOT_STICKY
            }

            else -> {
                stopSelf()
                return START_NOT_STICKY
            }
        }

        return START_REDELIVER_INTENT
    }

    private fun startForegroundSafe(notification: Notification) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(notificationId, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE)
            } else {
                startForeground(notificationId, notification)
            }
        } catch (e: Throwable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && e is ForegroundServiceStartNotAllowedException) {
                notificationManager.notify(notificationId, notification)
            }
        }
    }

    private fun startSearch() {
        if (started) return
        started = true
        try {
            adbMdns = AdbMdns(this, AdbMdns.TLS_PAIRING, mdnsCallback).apply { start() }
            Log.d(tag, "Radar mDNS dinyalakan.")
        } catch (e: Exception) {
            handleResult(false, e)
        }
    }

    private fun stopSearch() {
        if (!started) return
        started = false
        try { adbMdns?.stop() } catch (e: Exception) {}
        adbMdns = null
    }

    private fun executeShutdown() {
        stopSearch()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        started = false
        stopSearch()
        releaseMulticastLock()
        serviceScope.cancel() 
        mainHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun onInput(code: String, port: Int) {
        stopSearch()
        serviceScope.launch {
            var client: AdbPairingClient? = null
            try {
                val key = AdbKey(PreferenceAdbKeyStore(HypersSettings.getPreferences()), "hypers")
                client = AdbPairingClient("127.0.0.1", port, code, key)
                val success = client.start()
                mainHandler.post { handleResult(success, null) }
            } catch (e: Throwable) {
                mainHandler.post { handleResult(false, e) }
            } finally {
                try { client?.close() } catch (ignored: Exception) {}
            }
        }
    }

    private fun handleResult(success: Boolean, exception: Throwable?) {
        mainHandler.post {
            stopForeground(STOP_FOREGROUND_REMOVE)
            val title: String
            val text: String?

            if (success) {
                title = getString(R.string.notification_adb_pairing_succeed_title)
                text = getString(R.string.notification_adb_pairing_succeed_text)
            } else {
                title = getString(R.string.notification_adb_pairing_failed_title)
                text = when (exception) {
                    is ConnectException -> getString(R.string.cannot_connect_port)
                    is AdbInvalidPairingCodeException -> getString(R.string.paring_code_is_wrong)
                    is AdbKeyException -> getString(R.string.adb_error_key_store)
                    else -> exception?.let { Log.getStackTraceString(it) } ?: "Timeout"
                }
            }

            val resultNotification = Notification.Builder(this, notificationChannel)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(notificationId, resultNotification)
            stopSelf()
        }
    }

    private val searchingNotification: Notification
        get() = Notification.Builder(this, notificationChannel)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.notification_adb_pairing_searching_for_service_title))
            .setContentText("Buka Wireless Debugging -> Klik 'Pasangkan perangkat dengan kode'")
            .addAction(buildStopAction())
            .setOngoing(true)
            .build()

    private val workingNotification: Notification
        get() = Notification.Builder(this, notificationChannel)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.notification_adb_pairing_working_title))
            .setOngoing(true)
            .build()

    private fun createInputNotification(port: Int): Notification {
        return Notification.Builder(this, notificationChannel)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.notification_adb_pairing_service_found_title))
            .setContentText("Port pairing $port berhasil ditangkap radar!")
            .addAction(buildReplyAction(port))
            .setOngoing(true)
            .build()
    }

    private fun buildReplyAction(port: Int): Notification.Action {
        val remoteInput = RemoteInput.Builder(remoteInputResultKey)
            .setLabel(getString(R.string.dialog_adb_pairing_paring_code))
            .build()

        val pendingIntent = PendingIntent.getForegroundService(
            this,
            replyRequestId,
            replyIntent(this, port),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        return Notification.Action.Builder(
            null,
            getString(R.string.notification_adb_pairing_input_paring_code),
            pendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()
    }

    private fun buildStopAction(): Notification.Action {
        val pendingIntent = PendingIntent.getService(
            this,
            stopRequestId,
            stopIntent(this),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        return Notification.Action.Builder(
            null,
            getString(R.string.notification_adb_pairing_stop_searching),
            pendingIntent
        ).build()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
