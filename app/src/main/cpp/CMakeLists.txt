cmake_minimum_required(VERSION 3.31)

project("hypers")

set(CMAKE_CXX_STANDARD 17)

add_compile_options(-Werror=format -fdata-sections -ffunction-sections -fno-exceptions -fno-rtti -fno-threadsafe-statics)

if (NOT CMAKE_BUILD_TYPE STREQUAL "Debug")
    message("Builing Release...")

    add_compile_options(-Os -flto -fvisibility=hidden -fvisibility-inlines-hidden)
    add_link_options(-flto -Wl,--exclude-libs,ALL -Wl,--gc-sections -Wl,--strip-all)
else ()
    message("Builing Debug...")

    add_definitions(-DDEBUG)
endif ()

find_package(OpenSSL REQUIRED)
set(BORINGSSL_SSL_LIB OpenSSL::SSL)
set(BORINGSSL_CRYPTO_LIB OpenSSL::Crypto)

add_library(cxx_interface INTERFACE)
set(CXX_LIB cxx_interface)

# Debug: print semua target boringssl yang tersedia
message(STATUS "=== Checking boringssl targets ===")
if (TARGET boringssl::ssl_static)
    message(STATUS "boringssl::ssl_static -> AVAILABLE")
else()
    message(STATUS "boringssl::ssl_static -> NOT FOUND")
endif()
if (TARGET boringssl::ssl)
    message(STATUS "boringssl::ssl -> AVAILABLE")
else()
    message(STATUS "boringssl::ssl -> NOT FOUND")
endif()
if (TARGET boringssl::crypto_static)
    message(STATUS "boringssl::crypto_static -> AVAILABLE")
else()
    message(STATUS "boringssl::crypto_static -> NOT FOUND")
endif()
if (TARGET boringssl::crypto)
    message(STATUS "boringssl::crypto -> AVAILABLE")
else()
    message(STATUS "boringssl::crypto -> NOT FOUND")
endif()
if (TARGET boringssl::boringssl)
    message(STATUS "boringssl::boringssl -> AVAILABLE")
else()
    message(STATUS "boringssl::boringssl -> NOT FOUND")
endif()
message(STATUS "=== End boringssl targets ===")

# Tentukan ssl target yang tersedia
if (TARGET boringssl::ssl_static)
    set(BORINGSSL_SSL_LIB boringssl::ssl_static)
elseif (TARGET boringssl::ssl)
    set(BORINGSSL_SSL_LIB boringssl::ssl)
else()
    message(WARNING "No boringssl SSL target found! SSL_export_keying_material may not link.")
    set(BORINGSSL_SSL_LIB "")
endif()

# -------------------------------------------------------

add_executable(libhypers.so
        starter.cpp misc.cpp selinux.cpp cgroup.cpp)

target_link_libraries(libhypers.so log cxx::cxx)

if (NOT CMAKE_BUILD_TYPE STREQUAL "Debug")
    add_custom_command(TARGET libhypers.so POST_BUILD
            COMMAND ${CMAKE_STRIP} --remove-section=.comment "${CMAKE_LIBRARY_OUTPUT_DIRECTORY}/libhypers.so")
endif ()

# -------------------------------------------------------

add_library(adb SHARED
        adb_pairing.cpp misc.cpp)

target_link_libraries(adb log ${BORINGSSL_SSL_LIB} boringssl::crypto_static cxx::cxx)

if (NOT CMAKE_BUILD_TYPE STREQUAL "Debug")
    add_custom_command(TARGET adb POST_BUILD
            COMMAND ${CMAKE_STRIP} --remove-section=.comment "${CMAKE_LIBRARY_OUTPUT_DIRECTORY}/libadb.so")
endif ()
