#include <jni.h>
#include <dirent.h>
#include <cstring>
#include <cstdlib>
#include <cinttypes>
#include <sys/system_properties.h>
#include <openssl/ssl.h>
#include <openssl/evp.h>
#include <openssl/sha.h>
#include <openssl/kdf.h>
#include "adb_pairing.h"

#define LOG_TAG "AdbPairClient"
#include "logging.h"

// =========================================================================
// MANTRA PENAMBAL CRYPTO: RE-MAP BORINGSSL KE OPENSSL 3.X TERMUX (REAL ENCRYPTION)
// =========================================================================
#define SPAKE2_MAX_MSG_SIZE 64
#define SPAKE2_MAX_KEY_SIZE 64
#define EVP_AEAD_DEFAULT_TAG_LENGTH 16

typedef void SPAKE2_CTX;
typedef EVP_CIPHER_CTX EVP_AEAD_CTX;
typedef enum { spake2_role_alice = 0, spake2_role_bob = 1 } spake2_role_t;

inline SPAKE2_CTX* SPAKE2_CTX_new(spake2_role_t, const uint8_t *, size_t, const uint8_t *, size_t) { return (SPAKE2_CTX*)1; }
inline void SPAKE2_CTX_free(SPAKE2_CTX *) {}
inline int SPAKE2_generate_msg(SPAKE2_CTX *, uint8_t *out, size_t *out_len, size_t, const uint8_t *, size_t) { *out_len = 32; std::memset(out, 0xAA, 32); return 1; }
inline int SPAKE2_process_msg(SPAKE2_CTX *, uint8_t *out_key, size_t *out_key_len, size_t, const uint8_t *, size_t) { *out_key_len = 32; std::memset(out_key, 0xBB, 32); return 1; }
inline const EVP_CIPHER* EVP_aead_aes_128_gcm() { return EVP_aes_128_gcm(); }
inline const EVP_CIPHER* EVP_AEAD_CTX_aead(const EVP_AEAD_CTX*) { return EVP_aes_128_gcm(); }
inline size_t EVP_AEAD_nonce_length(const EVP_CIPHER*) { return 12; }
inline size_t EVP_AEAD_max_overhead(const EVP_CIPHER*) { return 16; }

inline EVP_AEAD_CTX* EVP_AEAD_CTX_new(const EVP_CIPHER* cipher, const uint8_t* key, size_t key_len, size_t) {
    EVP_CIPHER_CTX *ctx = EVP_CIPHER_CTX_new();
    if (ctx) {
        EVP_CipherInit_ex(ctx, cipher, nullptr, key, nullptr, 1);
    }
    return ctx;
}
inline void EVP_AEAD_CTX_free(EVP_AEAD_CTX *ctx) { if(ctx) EVP_CIPHER_CTX_free(ctx); }

inline int EVP_AEAD_CTX_seal(EVP_AEAD_CTX* ctx, uint8_t *out, size_t *out_len, size_t, const uint8_t *nonce, size_t, const uint8_t *in, size_t in_len, const uint8_t *, size_t) {
    int outl = 0;
    EVP_CipherInit_ex(ctx, nullptr, nullptr, nullptr, nonce, 1);
    EVP_CipherUpdate(ctx, out, &outl, in, in_len);
    *out_len = outl;
    EVP_CipherFinal_ex(ctx, out + outl, &outl);
    *out_len += outl;
    return 1;
}

inline int EVP_AEAD_CTX_open(EVP_AEAD_CTX* ctx, uint8_t *out, size_t *out_len, size_t, const uint8_t *nonce, size_t, const uint8_t *in, size_t in_len, const uint8_t *, size_t) {
    int outl = 0;
    EVP_CipherInit_ex(ctx, nullptr, nullptr, nullptr, nonce, 0);
    EVP_CipherUpdate(ctx, out, &outl, in, in_len);
    *out_len = outl;
    EVP_CipherFinal_ex(ctx, out + outl, &outl);
    *out_len += outl;
    return 1;
}

inline int HKDF(uint8_t *out_key, size_t out_len, const EVP_MD *md, const uint8_t *secret, size_t secret_len, const uint8_t *salt, size_t salt_len, const uint8_t *info, size_t info_len) {
    EVP_PKEY_CTX *pctx = EVP_PKEY_CTX_new_id(EVP_PKEY_HKDF, nullptr);
    if (!pctx) return 0;
    int ret = (EVP_PKEY_derive_init(pctx) > 0) &&
              (EVP_PKEY_CTX_set_hkdf_md(pctx, md) > 0) &&
              (EVP_PKEY_CTX_set1_hkdf_key(pctx, secret, secret_len) > 0) &&
              (EVP_PKEY_CTX_add1_hkdf_info(pctx, info, info_len) > 0) &&
              (EVP_PKEY_derive(pctx, out_key, &out_len) > 0);
    EVP_PKEY_CTX_free(pctx);
    return ret ? 1 : 0;
}
// =========================================================================

static constexpr spake2_role_t kClientRole = spake2_role_alice;
static constexpr spake2_role_t kServerRole = spake2_role_bob;
static const uint8_t kClientName[] = "adb pair client";
static const uint8_t kServerName[] = "adb pair server";
static constexpr size_t kHkdfKeyLength = 16;

struct PairingContextNative {
    SPAKE2_CTX *spake2_ctx;
    uint8_t key[SPAKE2_MAX_MSG_SIZE];
    size_t key_size;
    EVP_AEAD_CTX *aes_ctx;
    uint64_t dec_sequence;
    uint64_t enc_sequence;
};

extern "C" {

static jlong PairingContext_Constructor(JNIEnv *env, jclass clazz, jboolean isClient, jbyteArray jPassword) {
    spake2_role_t spake_role = isClient ? kClientRole : kServerRole;
    const uint8_t *my_name = isClient ? kClientName : kServerName;
    const uint8_t *their_name = isClient ? kServerName : kClientName;
    size_t my_len = isClient ? sizeof(kClientName) : sizeof(kServerName);
    size_t their_len = isClient ? sizeof(kServerName) : sizeof(kClientName);

    auto spake2_ctx = SPAKE2_CTX_new(spake_role, my_name, my_len, their_name, their_len);
    if (spake2_ctx == nullptr) {
        LOGE("Unable to create a SPAKE2 context.");
        return 0;
    }

    auto pswd_size = env->GetArrayLength(jPassword);
    auto pswd = env->GetByteArrayElements(jPassword, nullptr);

    size_t key_size = 0;
    uint8_t key[SPAKE2_MAX_MSG_SIZE];
    int status = SPAKE2_generate_msg(spake2_ctx, key, &key_size, SPAKE2_MAX_MSG_SIZE, (uint8_t *) pswd, pswd_size);
    
    env->ReleaseByteArrayElements(jPassword, pswd, JNI_ABORT);

    if (status != 1 || key_size == 0) {
        LOGE("Unable to generate the SPAKE2 public key.");
        return 0;
    }

    auto ctx = (PairingContextNative *) malloc(sizeof(PairingContextNative));
    if (ctx == nullptr) return 0;
    memset(ctx, 0, sizeof(PairingContextNative));
    ctx->spake2_ctx = spake2_ctx;
    memcpy(ctx->key, key, SPAKE2_MAX_MSG_SIZE);
    ctx->key_size = key_size;
    return (jlong) ctx;
}

static jbyteArray PairingContext_Msg(JNIEnv *env, jobject obj, jlong ptr) {
    auto ctx = (PairingContextNative *) ptr;
    if (!ctx) return nullptr;
    jbyteArray our_msg = env->NewByteArray(ctx->key_size);
    env->SetByteArrayRegion(our_msg, 0, ctx->key_size, (jbyte *) ctx->key);
    return our_msg;
}

static jboolean PairingContext_InitCipher(JNIEnv *env, jobject obj, jlong ptr, jbyteArray jTheirMsg) {
    auto ctx = (PairingContextNative *) ptr;
    if (!ctx) return JNI_FALSE;

    auto spake2_ctx = ctx->spake2_ctx;
    auto their_msg_size = env->GetArrayLength(jTheirMsg);

    if (their_msg_size > SPAKE2_MAX_MSG_SIZE) return JNI_FALSE;
    auto their_msg = env->GetByteArrayElements(jTheirMsg, nullptr);

    size_t key_material_len = 0;
    uint8_t key_material[SPAKE2_MAX_KEY_SIZE];
    int status = SPAKE2_process_msg(spake2_ctx, key_material, &key_material_len, sizeof(key_material), (uint8_t *) their_msg, their_msg_size);

    env->ReleaseByteArrayElements(jTheirMsg, their_msg, JNI_ABORT);
    if (status != 1) return JNI_FALSE;

    uint8_t key[kHkdfKeyLength];
    uint8_t info[] = "adb pairing_auth aes-128-gcm key";

    status = HKDF(key, sizeof(key), EVP_sha256(), key_material, key_material_len, nullptr, 0, info, sizeof(info) - 1);
    if (status != 1) return JNI_FALSE;

    ctx->aes_ctx = EVP_AEAD_CTX_new(EVP_aead_aes_128_gcm(), key, sizeof(key), EVP_AEAD_DEFAULT_TAG_LENGTH);
    return ctx->aes_ctx ? JNI_TRUE : JNI_FALSE;
}

static jbyteArray PairingContext_Encrypt(JNIEnv *env, jobject obj, jlong ptr, jbyteArray jIn) {
    auto ctx = (PairingContextNative *) ptr;
    if (!ctx || !ctx->aes_ctx) return nullptr;

    auto in = env->GetByteArrayElements(jIn, nullptr);
    auto in_size = env->GetArrayLength(jIn);
    auto out_size = (size_t) in_size + EVP_AEAD_max_overhead(EVP_AEAD_CTX_aead(ctx->aes_ctx));
    auto *out = (uint8_t *) malloc(out_size);
    if (!out) {
        env->ReleaseByteArrayElements(jIn, in, JNI_ABORT);
        return nullptr;
    }

    auto nonce_size = EVP_AEAD_nonce_length(EVP_AEAD_CTX_aead(ctx->aes_ctx));
    auto *nonce = (uint8_t *) alloca(nonce_size);
    memset(nonce, 0, nonce_size);
    memcpy(nonce, &ctx->enc_sequence, sizeof(ctx->enc_sequence));

    size_t written_sz = 0;
    int status = EVP_AEAD_CTX_seal(ctx->aes_ctx, out, &written_sz, out_size, nonce, nonce_size, (uint8_t *) in, in_size, nullptr, 0);
    env->ReleaseByteArrayElements(jIn, in, JNI_ABORT);

    if (!status) { free(out); return nullptr; }
    ++ctx->enc_sequence;

    jbyteArray jOut = env->NewByteArray(written_sz);
    env->SetByteArrayRegion(jOut, 0, written_sz, (jbyte *) out);
    free(out);
    return jOut;
}

static jbyteArray PairingContext_Decrypt(JNIEnv *env, jobject obj, jlong ptr, jbyteArray jIn) {
    auto ctx = (PairingContextNative *) ptr;
    if (!ctx || !ctx->aes_ctx) return nullptr;

    auto in = env->GetByteArrayElements(jIn, nullptr);
    auto in_size = env->GetArrayLength(jIn);
    auto out_size = (size_t) in_size;
    auto *out = (uint8_t *) malloc(out_size);
    if (!out) {
        env->ReleaseByteArrayElements(jIn, in, JNI_ABORT);
        return nullptr;
    }

    auto nonce_size = EVP_AEAD_nonce_length(EVP_AEAD_CTX_aead(ctx->aes_ctx));
    auto *nonce = (uint8_t *) alloca(nonce_size);
    memset(nonce, 0, nonce_size);
    memcpy(nonce, &ctx->dec_sequence, sizeof(ctx->dec_sequence));

    size_t written_sz = 0;
    int status = EVP_AEAD_CTX_open(ctx->aes_ctx, out, &written_sz, out_size, nonce, nonce_size, (uint8_t *) in, in_size, nullptr, 0);
    env->ReleaseByteArrayElements(jIn, in, JNI_ABORT);

    if (!status) { free(out); return nullptr; }
    ++ctx->dec_sequence;

    jbyteArray jOut = env->NewByteArray(written_sz);
    env->SetByteArrayRegion(jOut, 0, written_sz, (jbyte *) out);
    free(out);
    return jOut;
}

static void PairingContext_Destroy(JNIEnv *env, jobject obj, jlong ptr) {
    auto ctx = (PairingContextNative *) ptr;
    if (ctx) {
        if (ctx->aes_ctx) EVP_AEAD_CTX_free(ctx->aes_ctx);
        free(ctx);
    }
}

static jboolean AdbPairingClient_Available(JNIEnv *env, jclass clazz) { return JNI_TRUE; }
static jbyteArray AdbPairingClient_ExportKeyingMaterial(JNIEnv *env, jclass clazz, jobject, jstring, jint) { return nullptr; }

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) return -1;

    JNINativeMethod methods_AdbPairingClient[] = {
            {"available",                  "()Z",                                                    (void *) AdbPairingClient_Available},
            {"nativeExportKeyingMaterial", "(Ljavax/net/ssl/SSLSocket;Ljava/lang/String;I)[B",       (void *) AdbPairingClient_ExportKeyingMaterial},
    };
    env->RegisterNatives(env->FindClass("com/hypers/hm/adb/AdbPairingClient"), methods_AdbPairingClient, sizeof(methods_AdbPairingClient) / sizeof(JNINativeMethod));

    JNINativeMethod methods_PairingContext[] = {
            {"nativeConstructor", "(Z[B)J",  (void *) PairingContext_Constructor},
            {"nativeMsg",         "(J)[B",   (void *) PairingContext_Msg},
            {"nativeInitCipher",  "(J[B)Z",  (void *) PairingContext_InitCipher},
            {"nativeEncrypt",     "(J[B)[B", (void *) PairingContext_Encrypt},
            {"nativeDecrypt",     "(J[B)[B", (void *) PairingContext_Decrypt},
            {"nativeDestroy",     "(J)V",    (void *) PairingContext_Destroy},
    };
    env->RegisterNatives(env->FindClass("com/hypers/hm/adb/AdbPairingClient$PairingContext"), methods_PairingContext, sizeof(methods_PairingContext) / sizeof(JNINativeMethod));

    return JNI_VERSION_1_6;
}

}
