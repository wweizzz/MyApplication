package com.example.william.my.core.okhttp.compat

import com.example.william.my.core.okhttp.config.OkHttpConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object CompatRetry {
    fun setRetry(builder: OkHttpClient.Builder) {
        //是否允许失败重试
        builder.retryOnConnectionFailure(OkHttpConfig.retry())
    }
}