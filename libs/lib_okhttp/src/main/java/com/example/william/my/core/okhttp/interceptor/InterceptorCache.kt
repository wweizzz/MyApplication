package com.example.william.my.core.okhttp.interceptor

import android.content.Context
import android.text.TextUtils
import com.example.william.my.core.okhttp.base.Header
import com.example.william.my.core.okhttp.utils.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * 缓存拦截器
 */
class InterceptorCache(private val mContext: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        if (!TextUtils.equals(request.method, "GET")) {
            return chain.proceed(request)
        }

        if (request.headers(Header.RETROFIT_CACHE_ALIVE_SECOND).isEmpty()) {
            return chain.proceed(request)
        }
        val age = request.headers(Header.RETROFIT_CACHE_ALIVE_SECOND)[0].toInt()
        return buildResponse(chain.proceed(buildRequest(request, age)), age)
    }

    /**
     * 设置由缓存还是网络请求
     */
    private fun buildRequest(request: Request, age: Int): Request {
        val builder: Request.Builder = request.newBuilder()
        return if (NetworkUtils.isConnected(mContext)) {
            if (age <= 0) {
                builder
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build()
            } else {
                builder
                    .cacheControl(CacheControl.Builder().maxAge(age, TimeUnit.SECONDS).build())
                    .build()
            }
        } else {
            builder
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
    }

    /**
     * max-age=，缓存时长 60秒
     * max-stale，响应时长，缓存时长 = max-age + max-stale
     * only-if-cached，仅使用缓存
     * 移除pragma消息头，因为pragma也是控制缓存的一个消息头属性
     */
    private fun buildResponse(response: Response, age: Int): Response {
        val builder: Response.Builder = response.newBuilder()
        return if (NetworkUtils.isConnected(mContext)) {
            if (age <= 0) {
                builder
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .build()
            } else {
                builder
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=$age")
                    .build()
            }
        } else {
            builder
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + Int.MAX_VALUE
                )
                .build()
        }
    }
}