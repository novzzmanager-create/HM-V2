cmake_minimum_required(VERSION 3.22)
project("hypers")
set(CMAKE_CXX_STANDARD 17)

add_compile_options(-Werror=format -fdata-sections -ffunction-sections 
                    -fno-exceptions -fno-rtti -fno-threadsafe-statics)

if (NOT CMAKE_BUILD_TYPE STREQUAL "Debug")
    add_compile_options(-Os -flto -fvisibility=hidden -fvisibility-inlines-hidden)
    add_link_options(-flto -Wl,--exclude-libs,ALL -Wl,--gc-sections -Wl,--strip-all)
else()
    add_definitions(-DDEBUG)
endif()

# HAPUS find_package boringssl dan cxx
# Pakai c++_static dari NDK langsung

add_executable(libhypers.so starter.cpp misc.cpp selinux.cpp cgroup.cpp)
target_link_libraries(libhypers.so log c++)

add_library(adb SHARED adb_pairing.cpp misc.cpp)

# Gunakan SSL dari NDK/system bukan boringssl prefab
find_library(SSL_LIB ssl)
find_library(CRYPTO_LIB crypto)

target_link_libraries(adb log c++ ${SSL_LIB} ${CRYPTO_LIB})