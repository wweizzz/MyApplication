package com.example.william.my.core.okhttp.compat

import com.example.william.my.core.okhttp.interceptor.InterceptorLogging
import com.example.william.my.core.okhttp.utils.HttpLog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object CompatLogging {

    fun setBasicLog(builder: OkHttpClient.Builder) {
        /*
         * 添加拦截器
         * addInterceptor,在response被调用一次
         * addNetworkInterceptor,在request和response是分别被调用一次
         * <p>
         * 当下载文件时，Level 为 Level.BODY，会发生卡死情况
         */
        builder.addInterceptor(
            HttpLoggingInterceptor { message ->
                HttpLog.debug(message)
            }.setLevel(HttpLoggingInterceptor.Level.BASIC)
        )
    }

    fun setFormatLog(builder: OkHttpClient.Builder, filters: List<String>) {
        builder.addInterceptor(
            InterceptorLogging(filters)
        )
    }
}