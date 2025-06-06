package com.example.william.my.core.okhttp.config

import android.app.Application
import okhttp3.Interceptor
import java.io.File

object OkHttpConfig {

    private var mLogTag = "OkHttp"

    private var timeout = 20L
    private var retry = false
    private var ignoreSSL = false
    private var noProxy = false

    private var isConnectionPool = false
    private var mMaxIdleConnections = 5 // 最大空闲连接数，可提升至 10-20，以支持更多连接复用
    private var mKeepAliveDuration = 5L // 空闲连接存活时间，可适当延长存活时间，因为其支持多路复用

    private var cookieJar = false

    private var cache = false
    private var app: Application? = null
    private var cacheDir: File? = null
    private var cacheDirName: String = "cache"
    private var cacheDirSize: Long = 10L * 1024L * 1024L

    private var mShowBasicLog = false
    private var mShowFormatLog = true
    private var mShowFormatLogFilters: List<String> = arrayListOf()

    private var interceptors = arrayListOf<Interceptor>()

    fun getLogTag(): String {
        return mLogTag
    }

    fun isShowBasicLog(): Boolean {
        return mShowBasicLog
    }

    fun isShowFormatLog(): Boolean {
        return mShowFormatLog
    }

    fun getShowFormatLogFilters(): List<String> {
        return mShowFormatLogFilters
    }

    fun isConnectionPool(): Boolean {
        return isConnectionPool
    }

    fun getMaxIdleConnections(): Int {
        return mMaxIdleConnections
    }

    fun getKeepAliveDuration(): Long {
        return mKeepAliveDuration
    }

    fun cookieJar(): Boolean {
        return cookieJar
    }

    fun cache(): Boolean {
        return cache
    }

    fun getApp(): Application? {
        return app
    }

    fun getCacheDirSize(): Long {
        return cacheDirSize
    }

    fun getCacheDir(): File? {
        return cacheDir
    }

    fun getCacheDirName(): String {
        return cacheDirName
    }

    fun ignoreSSL(): Boolean {
        return ignoreSSL
    }

    fun noProxy(): Boolean {
        return noProxy
    }

    fun retry(): Boolean {
        return retry
    }

    fun getTimeout(): Long {
        return timeout
    }

    fun getInterceptors(): List<Interceptor> {
        return interceptors
    }

    open class Builder {

        fun setTimeout(timeout: Long): Builder {
            OkHttpConfig.timeout = timeout
            return this
        }

        fun setRetry(retry: Boolean): Builder {
            OkHttpConfig.retry = retry
            return this
        }

        fun setConnectionPool(isConnectionPool: Boolean): Builder {
            OkHttpConfig.isConnectionPool = isConnectionPool
            return this
        }

        fun setMaxIdleConnections(connections: Int): Builder {
            mMaxIdleConnections = connections
            return this
        }

        fun setKeepAliveDuration(duration: Long): Builder {
            mKeepAliveDuration = duration
            return this
        }

        fun setCookieJar(cookieJar: Boolean): Builder {
            OkHttpConfig.cookieJar = cookieJar
            return this
        }

        fun setIgnoreSSL(ignoreSSL: Boolean): Builder {
            OkHttpConfig.ignoreSSL = ignoreSSL
            return this
        }

        fun setNoProxy(noProxy: Boolean): Builder {
            OkHttpConfig.noProxy = noProxy
            return this
        }

        fun setCache(cache: Boolean, app: Application): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            return this
        }

        fun setCache(cache: Boolean, app: Application, dir: File): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            cacheDir = dir
            return this
        }

        fun setCache(cache: Boolean, app: Application, dir: File, dirSize: Long): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            cacheDir = dir
            cacheDirSize = dirSize
            return this
        }

        fun setCache(cache: Boolean, app: Application, dirName: String): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            cacheDirName = dirName
            return this
        }

        fun setCache(cache: Boolean, app: Application, dirName: String, dirSize: Long): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            cacheDirName = dirName
            cacheDirSize = dirSize
            return this
        }

        fun setLogTag(tag: String): Builder {
            mLogTag = tag
            return this
        }

        fun showBasicLog(show: Boolean): Builder {
            mShowBasicLog = show
            return this
        }

        fun showFormatLog(show: Boolean, filters: List<String>): Builder {
            mShowFormatLog = show
            mShowFormatLogFilters = filters
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }
    }
}