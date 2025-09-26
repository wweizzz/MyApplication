package com.example.william.my.core.websocket

import android.util.Log

object WebSocketLogger {

    private val TAG = this.javaClass.simpleName

    fun debug(msg: String) {
        Log.d(TAG, msg)
    }
}