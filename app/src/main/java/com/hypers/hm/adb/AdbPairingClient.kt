package com.hypers.hm.adb

import android.os.Build
import android.util.Log
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import java.io.Closeable
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.net.ssl.SSLSocket

private const val TAG = "AdbPairClient"

private const val kCurrentKeyHeaderVersion = 1.toByte()
private const val kMinSupportedKeyHeaderVersion = 1.toByte()
private const val kMaxSupportedKeyHeaderVersion = 1.toByte()
private const val kMaxPeerInfoSize = 8192
private const val kMaxPayloadSize = kMaxPeerInfoSize * 2

private const val kExportedKeyLabel = "adb-label\u0000"
private const val kExportedKeySize = 64

private const val kPairingPacketHeaderSize = 6

private class PeerInfo(
    val type: Byte,
    incomingData: ByteArray
) {
    val data = ByteArray(kMaxPeerInfoSize - 1)

    init {
        incomingData.copyInto(this.data, 0, 0, incomingData.size.coerceAtMost(kMaxPeerInfoSize - 1))
    }

    enum class Type(val value: Byte) {
        ADB_RSA_PUB_KEY(0.toByte()),
        ADB_DEVICE_GUID(1.toByte()),
    }

    fun writeTo(buffer: ByteBuffer) {
        buffer.put(type)
        buffer.put(data)
        Log.d(TAG, "write PeerInfo type=$type, data size=${data.size}")
    }

    companion object {
        fun readFrom(buffer: ByteBuffer): PeerInfo {
            val type = buffer.get()
            val data = ByteArray(kMaxPeerInfoSize - 1)
            buffer.get(data)
            return PeerInfo(type, data)
        }
    }
}

private class PairingPacketHeader(
    val version: Byte,
    val type: Byte,
    val payload: Int
) {
    enum class Type(val value: Byte) {
        SPAKE2_MSG(0.toByte()),
        PEER_INFO(1.toByte())
    }

    fun writeTo(buffer: ByteBuffer) {
        buffer.put(version)
        buffer.put(type)
        buffer.putInt(payload)
    }

    companion object {
        fun readFrom(buffer: ByteBuffer): PairingPacketHeader? {
            if (buffer.remaining() < kPairingPacketHeaderSize) return null
            val version = buffer.get()
            val type = buffer.get()
            val payload = buffer.int

            if (version < kMinSupportedKeyHeaderVersion || version > kMaxSupportedKeyHeaderVersion) {
                Log.e(TAG, "Version mismatch: us=$kCurrentKeyHeaderVersion them=$version")
                return null
            }
            if (type != Type.SPAKE2_MSG.value && type != Type.PEER_INFO.value) {
                Log.e(TAG, "Unknown type=$type")
                return null
            }
            if (payload <= 0 || payload > kMaxPayloadSize) {
                Log.e(TAG, "Unsafe payload size=$payload")
                return null
            }
            return PairingPacketHeader(version, type, payload)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
class AdbPairingClient(
    private val host: String, // Menjaga relasi parameter panggulan dari service
    private val port: Int,
    private val pairCode: String,
    private val key: AdbKey
) : Closeable {

    private enum class State {
        Ready, ExchangingMsgs, ExchangingPeerInfo, Stopped
    }

    private var socket: Socket? = null
    private var inputStream: DataInputStream? = null
    private var outputStream: DataOutputStream? = null
    private var pairingContext: PairingContext? = null
    private var state: State = State.Ready

    private val peerInfo: PeerInfo = PeerInfo(PeerInfo.Type.ADB_RSA_PUB_KEY.value, key.adbPublicKey)

    @Keep
    class PairingContext(private val nativePtr: Long) {
        val msg: ByteArray = nativeMsg(nativePtr)

        fun initCipher(theirMsg: ByteArray) = nativeInitCipher(nativePtr, theirMsg)
        fun encrypt(`in`: ByteArray) = nativeEncrypt(nativePtr, `in`)
        fun decrypt(`in`: ByteArray) = nativeDecrypt(nativePtr, `in`)
        fun destroy() = nativeDestroy(nativePtr)

        private external fun nativeMsg(nativePtr: Long): ByteArray
        private external fun nativeInitCipher(nativePtr: Long, theirMsg: ByteArray): Boolean
        private external fun nativeEncrypt(nativePtr: Long, inbuf: ByteArray): ByteArray?
        private external fun nativeDecrypt(nativePtr: Long, inbuf: ByteArray): ByteArray?
        private external fun nativeDestroy(nativePtr: Long)

        companion object {
            @JvmStatic
            external fun nativeConstructor(isClient: Boolean, password: ByteArray): Long

            fun create(password: ByteArray): PairingContext? {
                val nativePtr = nativeConstructor(true, password)
                return if (nativePtr != 0L) PairingContext(nativePtr) else null
            }
        }
    }

    fun start(): Boolean {
        Log.d(TAG, "Memulai jabat tangan JNI ADB di internal localhost port:$port")
        try {
            setupTlsConnection()
        } catch (e: Exception) {
            Log.e(TAG, "Koneksi awal TLS gagal dibangun", e)
            state = State.Stopped
            return false
        }

        state = State.ExchangingMsgs
        if (!doExchangeMsgs()) {
            Log.e(TAG, "Pertukaran pesan SPAKE2 gagal — Kode salah / Timeout")
            state = State.Stopped
            return false
        }

        state = State.ExchangingPeerInfo
        if (!doExchangePeerInfo()) {
            Log.e(TAG, "Pertukaran sertifikat RSA gagal dilakukan")
            state = State.Stopped
            return false
        }

        Log.i(TAG, "======= PAIRING SUCCESS SUKSES TOTAL =======")
        state = State.Stopped
        return true
    }

    private fun setupTlsConnection() {
        // Dinamisasi host target, fallback otomatis ke loopback lokal jika kosong
        val targetHost = if (host.isEmpty()) "127.0.0.1" else host
        
        val rawSocket = Socket(targetHost, port).apply {
            tcpNoDelay = true
            soTimeout = 15000 // Menghindari drop socket pada perangkat cellular ROM ketat
        }
        socket = rawSocket

        val sslSocket = key.sslContext.socketFactory
            .createSocket(rawSocket, targetHost, port, true) as SSLSocket
        sslSocket.startHandshake()
        Log.d(TAG, "Jalur pipa aman SSL/TLS internal berhasil dikunci pada $targetHost:$port")

        inputStream = DataInputStream(sslSocket.inputStream)
        outputStream = DataOutputStream(sslSocket.outputStream)

        val keyMaterial = exportKeyingMaterial(sslSocket)
        val pairCodeBytes = pairCode.toByteArray(Charsets.UTF_8)
        val passwordBytes = ByteArray(pairCodeBytes.size + keyMaterial.size)
        
        pairCodeBytes.copyInto(passwordBytes)
        keyMaterial.copyInto(passwordBytes, pairCodeBytes.size)

        val ctx = PairingContext.create(passwordBytes)
        checkNotNull(ctx) { "Gagal menginisialisasi Native PairingContext C++" }
        pairingContext = ctx
    }

    private fun exportKeyingMaterial(sslSocket: SSLSocket): ByteArray {
        val classNames = listOf(
            "com.android.org.conscrypt.Conscrypt",
            "org.conscrypt.Conscrypt"
        )
        for (className in classNames) {
            try {
                val cls = Class.forName(className)
                val method = cls.getMethod(
                    "exportKeyingMaterial",
                    SSLSocket::class.java,
                    String::class.java,
                    ByteArray::class.java,
                    Int::class.java
                )
                val result = method.invoke(null, sslSocket, kExportedKeyLabel, null, kExportedKeySize) as? ByteArray
                if (result != null) {
                    Log.d(TAG, "exportKeyingMaterial lewat driver $className SUKSES")
                    return result
                }
            } catch (_: Exception) {}
        }
        throw RuntimeException("Perangkat tidak mendukung modul Conscrypt.")
    }

    private fun createHeader(type: PairingPacketHeader.Type, payloadSize: Int): PairingPacketHeader {
        return PairingPacketHeader(kCurrentKeyHeaderVersion, type.value, payloadSize)
    }

    private fun readHeader(): PairingPacketHeader? {
        val stream = inputStream ?: return null
        val bytes = ByteArray(kPairingPacketHeaderSize)
        stream.readFully(bytes)
        val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN)
        return PairingPacketHeader.readFrom(buffer)
    }

    private fun writeHeader(header: PairingPacketHeader, payload: ByteArray) {
        val stream = outputStream ?: return
        val buffer = ByteBuffer.allocate(kPairingPacketHeaderSize).order(ByteOrder.BIG_ENDIAN)
        header.writeTo(buffer)
        stream.write(buffer.array())
        stream.write(payload)
        stream.flush()
    }

    private fun doExchangeMsgs(): Boolean {
        val ctx = pairingContext ?: return false
        val msg = ctx.msg
        val ourHeader = createHeader(PairingPacketHeader.Type.SPAKE2_MSG, msg.size)
        writeHeader(ourHeader, msg)

        val theirHeader = readHeader() ?: return false
        if (theirHeader.type != PairingPacketHeader.Type.SPAKE2_MSG.value) return false

        val theirMessage = ByteArray(theirHeader.payload)
        inputStream?.readFully(theirMessage)

        // Penyelarasan format Endianness biner sebelum memproses pesan SPAKE2 ke Native C++
        val safeBuffer = ByteBuffer.wrap(theirMessage).order(ByteOrder.BIG_ENDIAN)
        return ctx.initCipher(safeBuffer.array())
    }

    private fun doExchangePeerInfo(): Boolean {
        val ctx = pairingContext ?: return false
        val buf = ByteBuffer.allocate(kMaxPeerInfoSize).order(ByteOrder.BIG_ENDIAN)
        peerInfo.writeTo(buf)

        val outbuf = ctx.encrypt(buf.array()) ?: return false
        val ourHeader = createHeader(PairingPacketHeader.Type.PEER_INFO, outbuf.size)
        writeHeader(ourHeader, outbuf)

        val theirHeader = readHeader() ?: return false
        if (theirHeader.type != PairingPacketHeader.Type.PEER_INFO.value) return false

        val theirMessage = ByteArray(theirHeader.payload)
        inputStream?.readFully(theirMessage)

        // Membungkus buffer enkripsi masuk ke format BIG_ENDIAN mencegah korupsi pemetaan bit
        val encryptedBuffer = ByteBuffer.wrap(theirMessage).order(ByteOrder.BIG_ENDIAN)
        val decrypted = ctx.decrypt(encryptedBuffer.array()) ?: throw AdbInvalidPairingCodeException()

        val alignedDecrypted = if (decrypted.size != kMaxPeerInfoSize) {
            ByteArray(kMaxPeerInfoSize).apply {
                decrypted.copyInto(this, 0, 0, decrypted.size.coerceAtMost(kMaxPeerInfoSize))
            }
        } else {
            decrypted
        }

        val theirPeerInfo = PeerInfo.readFrom(ByteBuffer.wrap(alignedDecrypted).order(ByteOrder.BIG_ENDIAN))
        Log.d(TAG, "Sukses membaca informasi sistem target. Tipe: ${theirPeerInfo.type}")
        return true
    }

    override fun close() {
        try { inputStream?.close() } catch (_: Throwable) {}
        try { outputStream?.close() } catch (_: Throwable) {}
        try { socket?.close() } catch (_: Throwable) {}
        try { pairingContext?.destroy() } catch (_: Throwable) {}
        inputStream = null
        outputStream = null
        socket = null
        pairingContext = null
    }


    companion object {
        init {
            System.loadLibrary("adb")
            
        }

        @JvmStatic
        external fun available(): Boolean
    }
}
