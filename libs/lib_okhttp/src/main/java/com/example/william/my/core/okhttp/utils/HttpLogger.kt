package com.example.william.my.core.okhttp.utils

import android.util.Log
import com.example.william.my.core.okhttp.config.OkHttpConfig

object HttpLogger {

    private val TAG: String = OkHttpConfig.getLogTag()

    fun debug(msg: String) {
        Log.d(TAG, msg)
    }

    fun error(msg: String) {
        Log.e(TAG, msg)
    }
}