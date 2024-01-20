package com.example.william.my.core.okhttp.utils

import android.util.Log
import com.example.william.my.core.okhttp.config.OkHttpConfig

object HttpLog {

    fun debug(msg: String) {
        Log.d(OkHttpConfig.getLogTag(), msg)
    }

    fun error(msg: String) {
        Log.e(OkHttpConfig.getLogTag(), msg)
    }
}