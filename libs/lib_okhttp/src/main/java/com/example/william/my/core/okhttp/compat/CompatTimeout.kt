package com.example.william.my.core.okhttp.compat

import com.example.william.my.core.okhttp.config.OkHttpConfig
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object CompatTimeout {
    fun setTimeOut(builder: OkHttpClient.Builder) {
        //不允许失败重试
        builder.retryOnConnectionFailure(OkHttpConfig.retry)
        //设置连接超时时间
        builder.connectTimeout(OkHttpConfig.timeout, TimeUnit.SECONDS)
        //设置写的超时时间
        builder.writeTimeout(OkHttpConfig.timeout, TimeUnit.SECONDS)
        //设置读取超时时间
        builder.readTimeout(OkHttpConfig.timeout, TimeUnit.SECONDS)
        //设置调用超时时间
        builder.callTimeout(OkHttpConfig.timeout, TimeUnit.SECONDS)
    }
}