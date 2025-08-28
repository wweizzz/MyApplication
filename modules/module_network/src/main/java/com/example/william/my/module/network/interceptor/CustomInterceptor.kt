package com.sceneconsole.thingstill.lib.model.interceptor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer

/**
 * 通用请求参数
 */
class CustomInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = when (originalRequest.method) {
            "GET" -> handleGetRequest(originalRequest)
            "POST" -> handlePostRequest(originalRequest)
            else -> originalRequest // 其他请求方法不处理
        }
        return chain.proceed(modifiedRequest)
    }

    private fun handleGetRequest(request: Request): Request {
        val newUrl = request.url.newBuilder()
            //.addQueryParameter("key", "value")
            .build()
        return request.newBuilder().url(newUrl).build()
    }

    private fun Request.getGetParamsMap(): MutableMap<String, String> {
        val paramsMap = mutableMapOf<String, String>()
        this.url.queryParameterNames.forEach { key ->
            paramsMap[key] = this.url.queryParameter(key).toString()
        }
        return paramsMap
    }

    private fun handlePostRequest(request: Request): Request {
        return when (val body = request.body) {
            is FormBody -> handleFormBody(request, body)
            else -> addParamsToBody(request, body)
        }
    }

    private fun handleFormBody(request: Request, formBody: FormBody): Request {
        val mergedMap = formBody.getPostParamsMap().apply {
            //put("timeStamp", "timeStamp")
        }

        // 重建请求
        val newBody = FormBody.Builder().apply {
            mergedMap.forEach { (name, value) ->
                add(name, value)
            }
        }.build()

        return request.newBuilder().post(newBody).build()
    }

    private fun FormBody.getPostParamsMap(): MutableMap<String, String> {
        val paramsMap = mutableMapOf<String, String>()
        for (i in 0 until this.size) {
            paramsMap[name(i)] = value(i)
        }
        return paramsMap
    }

    // 处理@Body类型的POST请求（需特殊处理）
    private fun addParamsToBody(request: Request, requestBody: RequestBody?): Request {
        // 如果使用@Body Map，需要将参数合并到Map中再序列化
        // 需要根据实际序列化方式（如Gson）处理
        val contentType = requestBody?.contentType()
        if (contentType?.subtype?.contains("json") != true) {
            return request // 非JSON类型直接返回原请求
        }

        // 添加公共参数
        val mergedMap = requestBody.getPostParamsMap().apply {
            //put("timeStamp", "timeStamp")
        }

        // 重建请求
        val newBody = Gson().toJson(mergedMap).toRequestBody(contentType)

        return request.newBuilder().post(newBody).build()
    }

    private fun RequestBody?.getPostParamsMap(): MutableMap<String, String> {
        var paramsMap = mutableMapOf<String, String>()
        this.toUtf8String()?.let {
            paramsMap = Gson().fromJson(it, object : TypeToken<Map<String, String>>() {}.type)
        } ?: {
            paramsMap = mutableMapOf()
        }
        return paramsMap
    }

    private fun RequestBody?.toUtf8String(): String? {
        return this?.let {
            val buffer = Buffer()
            writeTo(buffer)
            buffer.readUtf8()
        }
    }
}