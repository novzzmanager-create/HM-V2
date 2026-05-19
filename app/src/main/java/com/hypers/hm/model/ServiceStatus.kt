package com.hypers.hm.model

import com.hypers.hm.Hypers

data class ServiceStatus(
        val uid: Int = -1,
        val apiVersion: Int = -1,
        val patchVersion: Int = -1,
        val seContext: String? = null,
        val permission: Boolean = false
) {
    val isRunning: Boolean
        get() = uid != -1 && Hypers.pingBinder()
}