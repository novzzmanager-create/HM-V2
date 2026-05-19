package com.hypers.hm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hypers.hm.shell.ShellBinderRequestHandler

class HypersReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if ("hypers.manager.hm.intent.action.REQUEST_BINDER" == intent.action) {
            ShellBinderRequestHandler.handleRequest(context, intent)
        }
    }
}
