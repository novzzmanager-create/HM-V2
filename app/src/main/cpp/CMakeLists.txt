cmake_minimum_required(VERSION 3.18)
project("hypers")

set(CMAKE_CXX_STANDARD 17)
add_compile_options(
    -fdata-sections -ffunction-sections
    -fno-exceptions -fno-rtti -fno-threadsafe-statics
    -Os -fvisibility=hidden
)

# ── OpenSSL: cari dari NDK prefab ATAU fallback sysroot ─────────────────────
find_library(SSL_LIB ssl)
find_library(CRYPTO_LIB crypto)

if(SSL_LIB AND CRYPTO_LIB)
    message(STATUS "Found SSL via NDK: ${SSL_LIB}")
    message(STATUS "Found Crypto via NDK: ${CRYPTO_LIB}")
    set(OPENSSL_LIBS ${SSL_LIB} ${CRYPTO_LIB})
else()
    # Fallback: cari di prefab path (Termux / AGP)
    find_path(OPENSSL_INCLUDE_DIR openssl/ssl.h
        PATHS
            "${CMAKE_SYSROOT}/usr/include"
            "${ANDROID_NDK}/toolchains/llvm/prebuilt/linux-x86_64/sysroot/usr/include"
    )
    find_library(SSL_LIB NAMES ssl
        PATHS
            "${CMAKE_SYSROOT}/usr/lib/${ANDROID_ABI}"
            "${CMAKE_SYSROOT}/usr/lib"
    )
    find_library(CRYPTO_LIB NAMES crypto
        PATHS
            "${CMAKE_SYSROOT}/usr/lib/${ANDROID_ABI}"
            "${CMAKE_SYSROOT}/usr/lib"
    )
    if(SSL_LIB AND CRYPTO_LIB)
        set(OPENSSL_LIBS ${SSL_LIB} ${CRYPTO_LIB})
        if(OPENSSL_INCLUDE_DIR)
            include_directories(${OPENSSL_INCLUDE_DIR})
        endif()
    else()
        message(WARNING "OpenSSL tidak ditemukan - libadb.so tidak akan di-build")
        set(OPENSSL_FOUND FALSE)
    endif()
endif()

# ── Log library (wajib ada di Android NDK) ──────────────────────────────────
find_library(LOG_LIB log)

# ── Interface lib (stub, tidak pakai libc++ shared) ─────────────────────────
add_library(cxx_interface INTERFACE)
set(CXX_LIB cxx_interface)

# ── TARGET 1: libhypers.so (server utama, tidak butuh OpenSSL) ───────────────
add_executable(libhypers.so
    starter_hypers.cpp
    misc.cpp
    selinux.cpp
    cgroup.cpp
)
target_link_libraries(libhypers.so ${LOG_LIB} ${CXX_LIB})
# Pastikan tidak link libc++ shared agar stabil di Sketchware
set_target_properties(libhypers.so PROPERTIES
    LINK_FLAGS "-Wl,--gc-sections"
)

# ── TARGET 2: libadb.so (JNI ADB pairing, butuh OpenSSL) ─────────────────────
if(NOT DEFINED OPENSSL_FOUND OR NOT OPENSSL_FOUND STREQUAL "FALSE")
    add_library(adb SHARED
        adb_pairing.cpp
        misc.cpp
    )
    target_link_libraries(adb ${LOG_LIB} ${OPENSSL_LIBS} ${CXX_LIB})
    set_target_properties(adb PROPERTIES
        LINK_FLAGS "-Wl,--gc-sections"
    )
endif()
