package com.example.william.my.core.okhttp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object NetworkUtils {

    fun isConnected(context: Context): Boolean {
        val info = getActiveNetworkInfo(context)
        return info != null && info.isConnected
    }

    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }
}