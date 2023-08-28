package com.example.william.my.core.okhttp.compat

import android.app.Application
import android.os.Environment
import com.example.william.my.core.okhttp.config.OkHttpConfig
import com.example.william.my.core.okhttp.interceptor.InterceptorCache
import com.example.william.my.core.okhttp.utils.HttpLog
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File

object CompatCache {

    fun setCache(
        builder: OkHttpClient.Builder
    ) {
        OkHttpConfig.app?.let { app ->
            val cacheFile: File =
                OkHttpConfig.cacheFile ?: File(getCacheDir(app), OkHttpConfig.cacheDirName)
            builder.cache(Cache(cacheFile, OkHttpConfig.cacheDirSize))
            builder.addNetworkInterceptor(InterceptorCache(app))
        } ?: {
            HttpLog.error("context == null. 缓存未启用.")
        }
    }

    private fun getCacheDir(context: Application): File {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            //外部存储可用
            context.externalCacheDir!!
        } else {
            //外部存储不可用
            context.cacheDir
        }
    }
}