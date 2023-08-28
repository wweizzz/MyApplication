package com.example.william.my.core.okhttp.utils

import android.util.Log
import com.example.william.my.core.okhttp.config.OkHttpConfig

object HttpLog {

    fun debug(msg: String) {
        Log.d(OkHttpConfig.logTag, msg)
    }

    fun error(msg: String) {
        Log.e(OkHttpConfig.logTag, msg)
    }
}