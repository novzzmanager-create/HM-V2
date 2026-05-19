package com.hypers.hm.adb

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

@RequiresApi(Build.VERSION_CODES.R)
class AdbMdns(
    context: Context,
    private val serviceType: String,
    private val callback: Callback
) {

    interface Callback {
        fun onChanged(host: String, port: Int)
    }

    companion object {
        private const val TAG = "AdbMdns"
        const val TLS_CONNECT = "_adb-tls-connect._tcp."
        const val TLS_PAIRING = "_adb-tls-pairing._tcp."
    }

    private val appContext = context.applicationContext
    private val nsdManager: NsdManager = appContext.getSystemService(NsdManager::class.java)
    private val mainHandler = Handler(Looper.getMainLooper())

    private val _running = AtomicBoolean(false)
    private val _registered = AtomicBoolean(false)
    val running get() = _running.get()

    private var serviceName: String? = null
    private val retryCount = AtomicInteger(0)
    private val MAX_RETRIES = 5

    private val resolveRetryTracker = ConcurrentHashMap<String, Int>()
    private val MAX_RESOLVE_RETRIES = 5

    private val resolveQueue = LinkedBlockingQueue<NsdServiceInfo>()
    @Volatile private var isResolving = false

    private var multicastLock: WifiManager.MulticastLock? = null

    // Inner class DiscoveryListener
    private inner class DiscoveryListener : NsdManager.DiscoveryListener {
        override fun onDiscoveryStarted(regType: String) {
            onDiscoveryStart()
        }
        override fun onDiscoveryStopped(serviceType: String) {
            onDiscoveryStop()
        }
        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            onStartDiscoveryFailed(errorCode)
        }
        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.w(TAG, "onStopDiscoveryFailed: $errorCode")
        }
        override fun onServiceFound(serviceInfo: NsdServiceInfo) {
            onServiceFound(serviceInfo)
        }
        override fun onServiceLost(serviceInfo: NsdServiceInfo) {
            onServiceLost(serviceInfo)
        }
    }

    private val listener = DiscoveryListener()

    private val BYPASS_SERVICE_TYPE = "_services._dns-sd._udp."

    fun start() {
        if (_running.getAndSet(true)) return
        retryCount.set(0)
        resolveRetryTracker.clear()
        resolveQueue.clear()
        isResolving = false
        acquireMulticastLock()
        startDiscovery()
    }

    fun stop() {
        if (!_running.getAndSet(false)) return
        mainHandler.removeCallbacksAndMessages(null)
        stopDiscovery()
        releaseMulticastLock()
        resolveRetryTracker.clear()
        resolveQueue.clear()
        isResolving = false
    }

    private fun acquireMulticastLock() {
        try {
            val wifi = appContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager ?: return
            val lock = wifi.createMulticastLock("HypersAdbMdnsLock")
            lock.setReferenceCounted(false)
            lock.acquire()
            multicastLock = lock
            Log.d(TAG, "MulticastLock berhasil di-acquire, jalur mDNS terbuka lebar.")
        } catch (e: Exception) {
            Log.w(TAG, "MulticastLock gagal: ${e.message}")
        }
    }

    private fun releaseMulticastLock() {
        try {
            multicastLock?.let { if (it.isHeld) it.release() }
        } catch (e: Exception) {
            Log.w(TAG, "MulticastLock release gagal: ${e.message}")
        } finally {
            multicastLock = null
        }
    }

    private fun startDiscovery() {
        if (!_running.get() || _registered.get()) return
        try {
            nsdManager.discoverServices(BYPASS_SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, listener)
            Log.d(TAG, "Mulai pemindaian radar mDNS komparatif...")
        } catch (e: Exception) {
            Log.e(TAG, "Gagal memicu discoverServices: ${e.message}")
            scheduleRetryDiscover()
        }
    }

    private fun stopDiscovery() {
        if (!_registered.getAndSet(false)) return
        try {
            nsdManager.stopServiceDiscovery(listener)
        } catch (e: Exception) {
            Log.w(TAG, "stopServiceDiscovery gagal: ${e.message}")
        }
    }

    internal fun onDiscoveryStart() {
        _registered.set(true)
        retryCount.set(0)
        Log.d(TAG, "Radar nirkabel mDNS aktif sepenuhnya!")
    }

    internal fun onDiscoveryStop() {
        _registered.set(false)
    }

    internal fun onStartDiscoveryFailed(errorCode: Int) {
        _registered.set(false)
        scheduleRetryDiscover()
    }

    internal fun onServiceFound(info: NsdServiceInfo) {
        if (!_running.get()) return
        val sType = info.serviceType
        if (sType.contains("adb") || info.serviceName.contains("adb") || sType.contains("tls")) {
            Log.d(TAG, "Sinyal ADB Terdeteksi -> ${info.serviceName}. Memasukkan ke antrean resolve...")
            resolveQueue.add(info)
            processNextResolve()
        }
    }

    internal fun onServiceLost(info: NsdServiceInfo) {
        if (info.serviceName == serviceName) {
            serviceName = null
            mainHandler.post { callback.onChanged("", -1) }
        }
    }

    private fun processNextResolve() {
        if (isResolving || !_running.get()) return
        val nextService = resolveQueue.poll() ?: return
        isResolving = true
        mainHandler.post { executeResolve(nextService) }
    }

    private fun executeResolve(info: NsdServiceInfo) {
        if (!_running.get()) {
            isResolving = false
            return
        }

        val currentRetries = resolveRetryTracker.getOrDefault(info.serviceName, 0)
        if (currentRetries >= MAX_RESOLVE_RETRIES) {
            Log.w(TAG, "Resolve menyerah untuk ${info.serviceName} setelah $MAX_RESOLVE_RETRIES percobaan.")
            isResolving = false
            processNextResolve()
            return
        }

        resolveRetryTracker[info.serviceName] = currentRetries + 1

        try {
            nsdManager.resolveService(info, object : NsdManager.ResolveListener {
                override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                    Log.w(TAG, "Resolve gagal (${serviceInfo.serviceName}) errorCode=$errorCode, retry ke-${currentRetries + 1}")
                    mainHandler.postDelayed({
                        isResolving = false
                        executeResolve(info)
                    }, 500L)
                }

                override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
                    val host = serviceInfo.host?.hostAddress ?: "127.0.0.1"
                    val port = serviceInfo.port
                    Log.d(TAG, "Resolve sukses: ${serviceInfo.serviceName} -> $host:$port")
                    serviceName = serviceInfo.serviceName
                    resolveRetryTracker.remove(serviceInfo.serviceName)
                    mainHandler.post { callback.onChanged(host, port) }
                    isResolving = false
                    processNextResolve()
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "resolveService exception: ${e.message}")
            mainHandler.postDelayed({
                isResolving = false
                processNextResolve()
            }, 500L)
        }
    }

    private fun scheduleRetryDiscover() {
        if (!_running.get()) return
        val count = retryCount.incrementAndGet()
        if (count > MAX_RETRIES) {
            Log.w(TAG, "Radar mDNS menyerah setelah $MAX_RETRIES percobaan.")
            return
        }
        val delay = (count * 2000L).coerceAtMost(10000L)
        Log.d(TAG, "Retry discover ke-$count dalam ${delay}ms...")
        mainHandler.postDelayed({
            _registered.set(false)
            startDiscovery()
        }, delay)
    }
}
