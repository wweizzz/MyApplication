package com.example.william.my.core.okhttp.compat

import com.example.william.my.core.okhttp.config.OkHttpConfig
import okhttp3.OkHttpClient

object CompatInterceptor {

    fun addInterceptor(builder: OkHttpClient.Builder) {
        OkHttpConfig.interceptor?.let {
            builder.addInterceptor(it)
        }
    }
}