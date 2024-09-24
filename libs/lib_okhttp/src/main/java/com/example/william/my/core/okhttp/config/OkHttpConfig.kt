package com.example.william.my.core.okhttp.config

import android.app.Application
import okhttp3.Interceptor
import java.io.File

object OkHttpConfig {

    private var mLogTag = "OkHttp"

    private var mShowBasicLog = false
    private var mShowFormatLog = true

    private var mShowFormatLogFilters: List<String> = arrayListOf()

    private var isConnectionPool = false
    private var mMaxIdleConnections = 5
    private var mKeepAliveDuration = 5L

    private var cookieJar = false

    private var cache = false
    private var app: Application? = null

    private var cacheDir: File? = null
    private var cacheDirName: String = "cache"
    private var cacheDirSize: Long = 10L * 1024L * 1024L

    private var noProxy = false
    private var ignoreSSL = false

    private var retry = false
    private var timeout = 20L

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

        fun setApp(app: Application): Builder {
            OkHttpConfig.app = app
            return this
        }

        fun setLogTag(tag: String): Builder {
            OkHttpConfig.mLogTag = tag
            return this
        }

        fun showBasicLog(show: Boolean): Builder {
            OkHttpConfig.mShowBasicLog = show
            return this
        }

        fun showFormatLog(show: Boolean,filters: List<String>): Builder {
            OkHttpConfig.mShowFormatLog = show
            OkHttpConfig.mShowFormatLogFilters = filters
            return this
        }

        fun setConnectionPool(isConnectionPool: Boolean): Builder {
            OkHttpConfig.isConnectionPool = isConnectionPool
            return this
        }

        fun setMaxIdleConnections(connections: Int): Builder {
            OkHttpConfig.mMaxIdleConnections = connections
            return this
        }

        fun setKeepAliveDuration(duration: Long): Builder {
            OkHttpConfig.mKeepAliveDuration = duration
            return this
        }

        fun setCookieJar(cookieJar: Boolean): Builder {
            OkHttpConfig.cookieJar = cookieJar
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
            OkHttpConfig.cacheDir = dir
            return this
        }

        fun setCache(cache: Boolean, app: Application, dir: File, dirSize: Long): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            OkHttpConfig.cacheDir = dir
            OkHttpConfig.cacheDirSize = dirSize
            return this
        }


        fun setCache(cache: Boolean, app: Application, dirName: String): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            OkHttpConfig.cacheDirName = dirName
            return this
        }

        fun setCache(cache: Boolean, app: Application, dirName: String, dirSize: Long): Builder {
            OkHttpConfig.cache = cache
            OkHttpConfig.app = app
            OkHttpConfig.cacheDirName = dirName
            OkHttpConfig.cacheDirSize = dirSize
            return this
        }

        fun setNoProxy(noProxy: Boolean): Builder {
            OkHttpConfig.noProxy = noProxy
            return this
        }

        fun setIgnoreSSL(ignoreSSL: Boolean): Builder {
            OkHttpConfig.ignoreSSL = ignoreSSL
            return this
        }

        fun setRetry(retry: Boolean): Builder {
            OkHttpConfig.retry = retry
            return this
        }

        fun setTimeout(timeout: Long): Builder {
            OkHttpConfig.timeout = timeout
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            OkHttpConfig.interceptors.add(interceptor)
            return this
        }
    }
}