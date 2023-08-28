package com.example.william.my.core.okhttp.interceptor

import com.example.william.my.core.okhttp.body.ResponseProgressBody
import com.example.william.my.core.okhttp.listener.ResponseProgressListener
import com.example.william.my.core.okhttp.utils.HttpLog
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 下载进度
 */
class InterceptorProgress(
    private val listener: ResponseProgressListener = object : ResponseProgressListener {
        override fun onProgress(url: String, currentSize: Long, totalSize: Long) {
            HttpLog.debug("url : " + url + " , progress : " + (+currentSize * 100 / totalSize))
        }
    }
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        return response.newBuilder()
            .body(ResponseProgressBody(request.url.toString(), response.body!!, listener))
            .build()
    }
}