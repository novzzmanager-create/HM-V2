package com.hypers.hm.adb

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import java.io.ByteArrayInputStream
import java.math.BigInteger
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.*
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAKeyGenParameterSpec
import java.security.spec.RSAPublicKeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLEngine
import javax.net.ssl.X509ExtendedKeyManager
import javax.net.ssl.X509ExtendedTrustManager
import javax.security.auth.x500.X500Principal

private const val TAG = "AdbKey"

class AdbKey(private val adbKeyStore: AdbKeyStore, name: String) {

    companion object {

        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val ENCRYPTION_KEY_ALIAS = "_adbkey_encryption_key_"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"

        private const val IV_SIZE_IN_BYTES = 12
        private const val TAG_SIZE_IN_BYTES = 16

        private val PADDING = byteArrayOf(
                0x00, 0x01, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0x00,
                0x30, 0x21, 0x30, 0x09, 0x06, 0x05, 0x2b, 0x0e, 0x03, 0x02, 0x1a, 0x05, 0x00,
                0x04, 0x14)
    }

    private val encryptionKey: Key
    private val privateKey: RSAPrivateKey
    private val publicKey: RSAPublicKey
    val certificate: X509Certificate

    init {
        this.encryptionKey = getOrCreateEncryptionKey()
            ?: error("Failed to generate encryption key with AndroidKeyManager.")

        this.privateKey = getOrCreatePrivateKey()
        this.publicKey = KeyFactory.getInstance("RSA")
            .generatePublic(RSAPublicKeySpec(privateKey.modulus, RSAKeyGenParameterSpec.F4)) as RSAPublicKey

        // ← FIX: cert ditandatangani dengan privateKey kita sendiri → tidak ada mismatch
        this.certificate = generateSelfSignedCertificateCompat(privateKey, publicKey)

        Log.d(TAG, privateKey.toString())
    }

    // Generate self-signed X.509 cert yang ditandatangani dengan privateKey kita sendiri.
    // Tidak pakai BouncyCastle, tidak pakai AndroidKeyStore keypair baru.
    // Cert dan privateKey dijamin cocok → tidak ada KEY_VALUES_MISMATCH.
    private fun generateSelfSignedCertificateCompat(
        privateKey: RSAPrivateKey,
        publicKey: RSAPublicKey
    ): X509Certificate {
        return try {
            // Encode subject DN: SEQUENCE { SET { SEQUENCE { OID(CN) UTF8String("00") } } }
            val cnOid = byteArrayOf(0x06, 0x03, 0x55, 0x04, 0x03)
            val cnVal = byteArrayOf(0x0c, 0x02, 0x30, 0x30) // UTF8String "00"
            val cnSeq = derSeq(cnOid + cnVal)
            val cnSet = byteArrayOf(0x31) + derLen(cnSeq.size) + cnSeq
            val dnSeq = derSeq(cnSet)

            // Serial number: INTEGER 1
            val serial = byteArrayOf(0x02, 0x01, 0x01)

            // AlgorithmIdentifier: SHA256withRSA
            val algId = byteArrayOf(
                0x30, 0x0d,
                0x06, 0x09, 0x2a, 0x86.toByte(), 0x48, 0x86.toByte(),
                0xf7.toByte(), 0x0d, 0x01, 0x01, 0x0b,
                0x05, 0x00
            )

            // Validity encoding helper
            fun encodeTime(date: Date): ByteArray {
                val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                cal.time = date
                val year = cal.get(Calendar.YEAR)
                val s = "%04d%02d%02d%02d%02d%02dZ".format(
                    year,
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND)
                )
                // GeneralizedTime (0x18) for year >= 2050, UTCTime (0x17) otherwise
                val tag = if (year >= 2050) 0x18.toByte() else 0x17.toByte()
                val bytes = if (year >= 2050) s.toByteArray() else s.substring(2).toByteArray()
                return byteArrayOf(tag) + derLen(bytes.size) + bytes
            }

            val notBefore = Date(0)                  // 1970-01-01
            val notAfter = Date(99999999999000L)      // year ~5138, safe upper bound

            val validity = derSeq(encodeTime(notBefore) + encodeTime(notAfter))

            // SubjectPublicKeyInfo — already DER encoded
            val spki = publicKey.encoded

            // TBSCertificate
            val tbs = derSeq(serial + algId + dnSeq + validity + dnSeq + spki)

            // Sign TBSCertificate dengan privateKey kita ← kunci fix-nya
            val sig = Signature.getInstance("SHA256withRSA")
            sig.initSign(privateKey)
            sig.update(tbs)
            val sigBytes = sig.sign()

            // BIT STRING: prepend 0x00 (no unused bits)
            val sigBitStr = byteArrayOf(0x03) + derLen(sigBytes.size + 1) + byteArrayOf(0x00) + sigBytes

            // Full Certificate DER = SEQUENCE { tbs + algId + sigBitStr }
            val certDer = derSeq(tbs + algId + sigBitStr)

            CertificateFactory.getInstance("X.509")
                .generateCertificate(ByteArrayInputStream(certDer)) as X509Certificate

        } catch (e: Exception) {
            Log.e(TAG, "generateSelfSignedCertificateCompat failed", e)
            throw RuntimeException("Cannot generate ADB certificate", e)
        }
    }

    private fun derLen(len: Int): ByteArray = when {
        len < 0x80 -> byteArrayOf(len.toByte())
        len < 0x100 -> byteArrayOf(0x81.toByte(), len.toByte())
        else -> byteArrayOf(0x82.toByte(), (len shr 8).toByte(), (len and 0xff).toByte())
    }

    private fun derSeq(content: ByteArray): ByteArray =
        byteArrayOf(0x30) + derLen(content.size) + content

    val adbPublicKey: ByteArray by lazy(LazyThreadSafetyMode.NONE) {
        publicKey.adbEncoded(name)
    }

    private fun getOrCreateEncryptionKey(): Key? {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)

        return keyStore.getKey(ENCRYPTION_KEY_ALIAS, null) ?: run {
            val parameterSpec = KeyGenParameterSpec.Builder(
                ENCRYPTION_KEY_ALIAS,
                KeyProperties.PURPOSE_DECRYPT or KeyProperties.PURPOSE_ENCRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
            keyGenerator.init(parameterSpec)
            keyGenerator.generateKey()
        }
    }

    private fun encrypt(plaintext: ByteArray, aad: ByteArray?): ByteArray? {
        if (plaintext.size > Int.MAX_VALUE - IV_SIZE_IN_BYTES - TAG_SIZE_IN_BYTES) {
            return null
        }
        val ciphertext = ByteArray(IV_SIZE_IN_BYTES + plaintext.size + TAG_SIZE_IN_BYTES)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, encryptionKey)
        cipher.updateAAD(aad)
        cipher.doFinal(plaintext, 0, plaintext.size, ciphertext, IV_SIZE_IN_BYTES)
        System.arraycopy(cipher.iv, 0, ciphertext, 0, IV_SIZE_IN_BYTES)
        return ciphertext
    }

    private fun decrypt(ciphertext: ByteArray, aad: ByteArray?): ByteArray? {
        if (ciphertext.size < IV_SIZE_IN_BYTES + TAG_SIZE_IN_BYTES) {
            return null
        }
        val params = GCMParameterSpec(8 * TAG_SIZE_IN_BYTES, ciphertext, 0, IV_SIZE_IN_BYTES)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, encryptionKey, params)
        cipher.updateAAD(aad)
        return cipher.doFinal(ciphertext, IV_SIZE_IN_BYTES, ciphertext.size - IV_SIZE_IN_BYTES)
    }

    private fun getOrCreatePrivateKey(): RSAPrivateKey {
        var privateKey: RSAPrivateKey? = null

        val aad = ByteArray(16)
        "adbkey".toByteArray().copyInto(aad)

        var ciphertext = adbKeyStore.get()
        if (ciphertext != null) {
            try {
                val plaintext = decrypt(ciphertext, aad)
                val keyFactory = KeyFactory.getInstance("RSA")
                privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(plaintext)) as RSAPrivateKey
            } catch (e: Exception) {
                Log.w(TAG, "Failed to decrypt existing key, generating new one", e)
            }
        }
        if (privateKey == null) {
            val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA)
            keyPairGenerator.initialize(RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4))
            val keyPair = keyPairGenerator.generateKeyPair()
            privateKey = keyPair.private as RSAPrivateKey

            ciphertext = encrypt(privateKey.encoded, aad)
            if (ciphertext != null) {
                adbKeyStore.put(ciphertext)
            }
        }
        return privateKey
    }

    fun sign(data: ByteArray?): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)
        cipher.update(PADDING)
        return cipher.doFinal(data)
    }

    private val keyManager
        get() = object : X509ExtendedKeyManager() {
            private val alias = "key"

            override fun chooseClientAlias(
                keyTypes: Array<out String>,
                issuers: Array<out Principal>?,
                socket: Socket?
            ): String? {
                Log.d(TAG, "chooseClientAlias: keyType=${keyTypes.contentToString()}")
                for (keyType in keyTypes) {
                    if (keyType == "RSA") return alias
                }
                return null
            }

            override fun getCertificateChain(alias: String?): Array<X509Certificate>? {
                Log.d(TAG, "getCertificateChain: alias=$alias")
                return if (alias == this.alias) arrayOf(certificate) else null
            }

            override fun getPrivateKey(alias: String?): PrivateKey? {
                Log.d(TAG, "getPrivateKey: alias=$alias")
                return if (alias == this.alias) privateKey else null
            }

            override fun getClientAliases(keyType: String?, issuers: Array<out Principal>?): Array<String>? = null
            override fun getServerAliases(keyType: String, issuers: Array<out Principal>?): Array<String>? = null
            override fun chooseServerAlias(keyType: String, issuers: Array<out Principal>?, socket: Socket?): String? = null
        }

    private val trustManager
        get() =
            @RequiresApi(Build.VERSION_CODES.R)
            object : X509ExtendedTrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?, socket: Socket?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?, engine: SSLEngine?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?, socket: Socket?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?, engine: SSLEngine?) {}
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
            }

    @delegate:RequiresApi(Build.VERSION_CODES.R)
val sslContext: SSLContext by lazy(LazyThreadSafetyMode.NONE) {
    val provider = try {
        Class.forName("com.android.org.conscrypt.OpenSSLProvider")
            .getDeclaredConstructor()
            .newInstance() as java.security.Provider
    } catch (e: Exception) {
        null
    }
    val sslContext = if (provider != null) {
        SSLContext.getInstance("TLSv1.3", provider)
    } else {
        SSLContext.getInstance("TLSv1.3")
    }
    sslContext.init(arrayOf(keyManager), arrayOf(trustManager), SecureRandom())
    sslContext
}
}

interface AdbKeyStore {
    fun put(bytes: ByteArray)
    fun get(): ByteArray?
}

class PreferenceAdbKeyStore(private val preference: SharedPreferences) : AdbKeyStore {

    private val preferenceKey = "adbkey"

    override fun put(bytes: ByteArray) {
        preference.edit { putString(preferenceKey, String(Base64.encode(bytes, Base64.NO_WRAP))) }
    }

    override fun get(): ByteArray? {
        if (!preference.contains(preferenceKey)) return null
        return Base64.decode(preference.getString(preferenceKey, null), Base64.NO_WRAP)
    }
}

const val ANDROID_PUBKEY_MODULUS_SIZE = 2048 / 8
const val ANDROID_PUBKEY_MODULUS_SIZE_WORDS = ANDROID_PUBKEY_MODULUS_SIZE / 4
const val RSAPublicKey_Size = 524

private fun BigInteger.toAdbEncoded(): IntArray {
    val encoded = IntArray(ANDROID_PUBKEY_MODULUS_SIZE_WORDS)
    val r32 = BigInteger.ZERO.setBit(32)
    var tmp = this.add(BigInteger.ZERO)
    for (i in 0 until ANDROID_PUBKEY_MODULUS_SIZE_WORDS) {
        val out = tmp.divideAndRemainder(r32)
        tmp = out[0]
        encoded[i] = out[1].toInt()
    }
    return encoded
}

private fun RSAPublicKey.adbEncoded(name: String): ByteArray {
    val r32 = BigInteger.ZERO.setBit(32)
    val n0inv = modulus.remainder(r32).modInverse(r32).negate()
    val r = BigInteger.ZERO.setBit(ANDROID_PUBKEY_MODULUS_SIZE * 8)
    val rr = r.modPow(BigInteger.valueOf(2), modulus)

    val buffer = ByteBuffer.allocate(RSAPublicKey_Size).order(ByteOrder.LITTLE_ENDIAN)
    buffer.putInt(ANDROID_PUBKEY_MODULUS_SIZE_WORDS)
    buffer.putInt(n0inv.toInt())
    modulus.toAdbEncoded().forEach { buffer.putInt(it) }
    rr.toAdbEncoded().forEach { buffer.putInt(it) }
    buffer.putInt(publicExponent.toInt())

    val base64Bytes = Base64.encode(buffer.array(), Base64.NO_WRAP)
    val nameBytes = " $name\u0000".toByteArray()
    val bytes = ByteArray(base64Bytes.size + nameBytes.size)
    base64Bytes.copyInto(bytes)
    nameBytes.copyInto(bytes, base64Bytes.size)
    return bytes
}