package com.example.william.my.core.okhttp.helper

import android.app.Application
import com.example.william.my.core.okhttp.builder.RequestBody
import com.example.william.my.core.okhttp.compat.CompatCache
import com.example.william.my.core.okhttp.compat.CompatConnectionPool
import com.example.william.my.core.okhttp.compat.CompatCookieJar
import com.example.william.my.core.okhttp.compat.CompatHttpsSSL
import com.example.william.my.core.okhttp.compat.CompatInterceptor
import com.example.william.my.core.okhttp.compat.CompatLogging
import com.example.william.my.core.okhttp.compat.CompatProxy
import com.example.william.my.core.okhttp.compat.CompatTimeout
import com.example.william.my.core.okhttp.config.OkHttpConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

object OkHttpHelper {

    private var mOkHttpClient: OkHttpClient? = null

    fun client(): OkHttpClient {
        return mOkHttpClient ?: createOkHttpClient()
    }

    fun setApp(app: Application): OkHttpHelper {
        OkHttpConfig.app = app
        return this@OkHttpHelper
    }

    fun setLog(logShow: Boolean): OkHttpHelper {
        OkHttpConfig.logShow = logShow
        return this@OkHttpHelper
    }

    fun setLogTag(logTag: String): OkHttpHelper {
        OkHttpConfig.logTag = logTag
        return this@OkHttpHelper
    }

    fun setMaxIdleConnections(maxIdleConnections: Int): OkHttpHelper {
        OkHttpConfig.maxIdleConnections = maxIdleConnections
        return this@OkHttpHelper
    }

    fun setKeepAliveDuration(keepAliveDuration: Long): OkHttpHelper {
        OkHttpConfig.keepAliveDuration = keepAliveDuration
        return this@OkHttpHelper
    }

    fun setCookieJar(cookieJar: Boolean): OkHttpHelper {
        OkHttpConfig.cookieJar = cookieJar
        return this@OkHttpHelper
    }

    fun setCache(app: Application): OkHttpHelper {
        OkHttpConfig.cache = true
        OkHttpConfig.app = app
        return this@OkHttpHelper
    }

    fun setCache(app: Application, cacheDir: String, cacheSize: Long): OkHttpHelper {
        OkHttpConfig.cache = true
        OkHttpConfig.app = app
        OkHttpConfig.cacheDirName = cacheDir
        OkHttpConfig.cacheDirSize = cacheSize
        return this@OkHttpHelper
    }

    fun setNoProxy(): OkHttpHelper {
        OkHttpConfig.noProxy = true
        return this@OkHttpHelper
    }

    fun setIgnoreSSL(ignoreSSL: Boolean): OkHttpHelper {
        OkHttpConfig.ignoreSSL = ignoreSSL
        return this@OkHttpHelper
    }

    fun setRetry(retry: Boolean): OkHttpHelper {
        OkHttpConfig.retry = retry
        return this@OkHttpHelper
    }

    fun setTimeout(timeout: Long): OkHttpHelper {
        OkHttpConfig.timeout = timeout
        return this@OkHttpHelper
    }

    fun setInterceptor(interceptor: Interceptor): OkHttpHelper {
        OkHttpConfig.interceptor = interceptor
        return this@OkHttpHelper
    }

    private fun createOkHttpClient(): OkHttpClient {
        val okHttpClient = mOkHttpClient ?: createBuilder().build()
        mOkHttpClient = okHttpClient
        return okHttpClient
    }

    fun createBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()

        // 显示log
        if (OkHttpConfig.logShow) {
            CompatLogging.setBasicLog(builder)
            CompatLogging.setFormatLog(builder)
        }

        // 自定义连接池最大空闲连接数
        CompatConnectionPool.setConnectionPool(builder)

        // 携带cookie
        if (OkHttpConfig.cookieJar) {
            CompatCookieJar.cookieJar(builder)
        }

        // 设置缓存
        if (OkHttpConfig.cache) {
            CompatCache.setCache(builder)
        }

        // 禁用代理
        if (OkHttpConfig.noProxy) {
            CompatProxy.noProxy(builder)
        }

        // 忽略https证书
        if (OkHttpConfig.ignoreSSL) {
            CompatHttpsSSL.ignoreSSLForOkHttp(builder)
            CompatHttpsSSL.ignoreSSLForHttpsURLConnection()
        }

        // 设置超时时间
        CompatTimeout.setTimeOut(builder)

        // 添加拦截器
        CompatInterceptor.addInterceptor(builder)

        return builder
    }


    fun requestBodyBuilder(): RequestBody.Builder {
        return RequestBody.Builder()
    }

    fun requestBuilder(): Request.Builder {
        return Request.Builder()
    }
}