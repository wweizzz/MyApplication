package com.example.william.my.module.sample.utils

import android.os.Looper
import com.example.william.my.lib.utils.Utils

object ThreadUtils {

    private val TAG = this.javaClass.simpleName

    fun isMainThread(name: String) {
        val isMainThread = Looper.myLooper() == Looper.getMainLooper()
        Utils.e(TAG, "$name isMainThread : $isMainThread")
    }
}