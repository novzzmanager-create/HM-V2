package com.hypers.hm.starter

import com.hypers.hm.HypersApplication
import java.io.File

object Starter {

    private val app = HypersApplication.getInstance()

    private val starterFile = File(
        app.applicationInfo.nativeLibraryDir,
        "libhypers.so"
    )

    val userCommand: String = starterFile.absolutePath

    val adbCommand = "adb shell $userCommand"

    val internalCommand: String =
        "$userCommand --apk=${app.applicationInfo.sourceDir}"
}