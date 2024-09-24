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
import java.io.File

object OkHttpHelper {

    private var mOkHttpClient: OkHttpClient? = null

    fun client(): OkHttpClient {
        return mOkHttpClient ?: createOkHttpClient()
    }

    fun setLogTag(tag: String): OkHttpHelper {
        OkHttpConfig.Builder().setLogTag(tag)
        return this@OkHttpHelper
    }

    fun showBasicLog(show: Boolean): OkHttpHelper {
        OkHttpConfig.Builder().showBasicLog(show)
        return this@OkHttpHelper
    }

    fun showFormatLog(show: Boolean, filters: List<String>): OkHttpHelper {
        OkHttpConfig.Builder().showFormatLog(show, filters)
        return this@OkHttpHelper
    }

    fun setMaxIdleConnections(maxIdleConnections: Int): OkHttpHelper {
        OkHttpConfig.Builder().setMaxIdleConnections(maxIdleConnections)
        return this@OkHttpHelper
    }

    fun setKeepAliveDuration(keepAliveDuration: Long): OkHttpHelper {
        OkHttpConfig.Builder().setKeepAliveDuration(keepAliveDuration)
        return this@OkHttpHelper
    }

    fun setCookieJar(cookieJar: Boolean): OkHttpHelper {
        OkHttpConfig.Builder().setCookieJar(cookieJar)
        return this@OkHttpHelper
    }

    fun setCache(cache: Boolean, app: Application): OkHttpHelper {
        OkHttpConfig.Builder().setCache(cache, app)
        return this@OkHttpHelper
    }

    fun setCache(cache: Boolean, app: Application, dir: File): OkHttpHelper {
        OkHttpConfig.Builder().setCache(cache, app, dir)
        return this@OkHttpHelper
    }

    fun setCache(cache: Boolean, app: Application, dir: File, dirSize: Long): OkHttpHelper {
        OkHttpConfig.Builder().setCache(cache, app, dir, dirSize)
        return this@OkHttpHelper
    }

    fun setCache(cache: Boolean, app: Application, dirName: String): OkHttpHelper {
        OkHttpConfig.Builder().setCache(cache, app, dirName)
        return this@OkHttpHelper
    }

    fun setCache(cache: Boolean, app: Application, dirName: String, dirSize: Long): OkHttpHelper {
        OkHttpConfig.Builder().setCache(cache, app, dirName, dirSize)
        return this@OkHttpHelper
    }

    fun setNoProxy(noProxy: Boolean): OkHttpHelper {
        OkHttpConfig.Builder().setNoProxy(noProxy)
        return this@OkHttpHelper
    }

    fun setIgnoreSSL(ignoreSSL: Boolean): OkHttpHelper {
        OkHttpConfig.Builder().setIgnoreSSL(ignoreSSL)
        return this@OkHttpHelper
    }

    fun setRetry(retry: Boolean): OkHttpHelper {
        OkHttpConfig.Builder().setRetry(retry)
        return this@OkHttpHelper
    }

    fun setTimeout(timeout: Long): OkHttpHelper {
        OkHttpConfig.Builder().setTimeout(timeout)
        return this@OkHttpHelper
    }

    fun setInterceptor(interceptor: Interceptor): OkHttpHelper {
        OkHttpConfig.Builder().addInterceptor(interceptor)
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
        if (OkHttpConfig.isShowBasicLog()) {
            CompatLogging.setBasicLog(builder)
        }
        if (OkHttpConfig.isShowFormatLog()) {
            CompatLogging.setFormatLog(builder, OkHttpConfig.getShowFormatLogFilters())
        }

        // 自定义连接池最大空闲连接数
        if (OkHttpConfig.isConnectionPool()) {
            CompatConnectionPool.setConnectionPool(builder)
        }

        // 携带cookie
        if (OkHttpConfig.cookieJar()) {
            CompatCookieJar.cookieJar(builder)
        }

        // 设置缓存
        if (OkHttpConfig.cache()) {
            CompatCache.setCache(builder)
        }

        // 禁用代理
        if (OkHttpConfig.noProxy()) {
            CompatProxy.noProxy(builder)
        }

        // 忽略https证书
        if (OkHttpConfig.ignoreSSL()) {
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