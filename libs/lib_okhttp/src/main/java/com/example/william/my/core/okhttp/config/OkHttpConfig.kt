package com.example.william.my.core.okhttp.config

import android.app.Application
import okhttp3.Interceptor
import java.io.File

object OkHttpConfig {

    var app: Application? = null

    var logShow = true
    var logTag = "OkHttp"

    var maxIdleConnections = 5
    var keepAliveDuration = 5L

    var cookieJar = true

    var cache = false
    var cacheFile: File? = null
    var cacheDirName: String = "cache"
    var cacheDirSize: Long = 10L * 1024L * 1024L

    var noProxy = false

    var ignoreSSL = true

    var retry = false
    var timeout = 60L

    var interceptor: Interceptor? = null
}