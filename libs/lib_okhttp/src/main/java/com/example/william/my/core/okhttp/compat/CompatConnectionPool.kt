package com.example.william.my.core.okhttp.compat

import com.example.william.my.core.okhttp.config.OkHttpConfig
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object CompatConnectionPool {
    fun setConnectionPool(
        builder: OkHttpClient.Builder
    ) {
        //自定义连接池最大空闲连接数和等待时间大小，否则默认最大5个空闲连接
        builder.connectionPool(
            ConnectionPool(
                OkHttpConfig.getMaxIdleConnections(),
                OkHttpConfig.getKeepAliveDuration(),
                TimeUnit.MINUTES
            )
        )
    }
}