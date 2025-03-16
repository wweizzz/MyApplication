package com.example.william.my.core.ktor

import android.util.Log
import com.example.william.my.core.ktor.exception.ApiException
import com.example.william.my.core.ktor.settings.SslSettings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.append
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.serialization.json.Json
import java.io.File
import java.net.ConnectException
import java.net.Proxy
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

object KtorUtils {

    val httpClient = HttpClient(OkHttp) {

        // 禁用重定向
        followRedirects = false

        engine {

            // SSL
            config {
                hostnameVerifier(SslSettings.ignoreHostnameVerifier)
                sslSocketFactory(SslSettings.ignoreSSLSocketFactory, SslSettings.ignoreTrustManager)
            }
            // Proxy
            proxy = Proxy.NO_PROXY
        }

        // 1. 基础配置
        install(ContentNegotiation) {
            Json {
                ignoreUnknownKeys = true    // 忽略未知字段
                coerceInputValues = true    // 强制类型兼容
                encodeDefaults = true       // 序列化默认值
            }
            gson {
                setPrettyPrinting() // 格式化输出
                serializeNulls()    // 保留 null 值
            }
        }

        // 2. 超时设置
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        // 3. 重定向机制
        install(HttpRedirect) {
            //maxJumps  = 10 // 最大重定向次数
            checkHttpMethod = false // 允许 POST 重定向
        }

        // 4. 重试机制
        install(HttpRequestRetry) {
            maxRetries = 5
            retryIf { request, response ->
                // 当状态码是 5xx 服务器错误时重试
                response.status.value in 500..599
            }
            retryOnServerErrors(maxRetries = 3)
            retryOnExceptionIf { _, cause ->
                cause is HttpRequestTimeoutException
            }
            delayMillis { retry ->
                retry * 3000L
            } // retries in 3, 6, 9, etc. seconds
            exponentialDelay() // 指定重试之间的指数延迟，该延迟使用 Exponential backoff 算法计算。
        }

        // 5. Cookie管理
        install(HttpCookies) {

        }

        // 6. 统一请求头
        install(DefaultRequest) {
            //header("Platform", "Android")
            //header("Version", BuildConfig.VERSION_NAME)
        }

        // 7. 日志
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.e("Ktor Logging : ", message)
                }
            }

            level = LogLevel.ALL

            // 过滤
            filter { request ->
                request.url.host.contains("ktor.io")
            }

            // 忽略请求头
            sanitizeHeader { header ->
                header == HttpHeaders.Authorization
            }
        }
    }

    fun close() {
        httpClient.close()
    }

    // 统一响应处理（新增，参考思想）
    inline fun <reified T> handleException(e: Throwable): Result<T> {
        return when (e) {
            // 处理请求超时错误
            is HttpRequestTimeoutException -> Result.failure(ApiException.TimeoutError(e))
            // 处理网络连接错误
            is ConnectException -> Result.failure(ApiException.ConnectError(e))
            // 处理DNS解析失败错误
            is UnknownHostException -> Result.failure(ApiException.UnknownHostError(e))
            // 处理客户端请求错误（4xx）
            is ClientRequestException -> Result.failure(ApiException.ClientRequestError(e))
            // 处理服务端错误（5xx）
            is ServerResponseException -> Result.failure(ApiException.ServerResponseError(e))
            // 处理SSL证书错误
            is SSLHandshakeException -> Result.failure(ApiException.SSLHandshakeError(e))
            // 未知错误
            else -> Result.failure(ApiException.UnknownException(e))
        }
    }

    inline fun <reified T> get(
        url: String, params: Map<String, String> = emptyMap()
    ): Flow<Result<T>> {
        return flow {
            val response = httpClient.get(url) {
                setBody(
                    FormDataContent(parameters {
                        params.forEach {
                            append(it.key, it.value)
                        }
                    })
                )
            }
            val result = response.body<T>()
            emit(Result.success(result))
        }.catch { cause: Throwable ->
            emit(handleException(cause))
        }.onCompletion {
            close()
        }.flowOn(Dispatchers.IO)
    }

    inline fun <reified T> post(
        url: String, params: Map<String, String> = emptyMap()
    ): Flow<Result<T>> {
        return flow {
            val response = httpClient.post(url) {
                setBody(
                    MultiPartFormDataContent(formData {
                        params.forEach {
                            append(it.key, it.value)
                        }
                    })
                )
            }
            val result = response.body<T>()
            emit(Result.success(result))
        }.catch { cause: Throwable ->
            emit(handleException(cause))
        }.onCompletion {
            close()
        }.flowOn(Dispatchers.IO)
    }

    inline fun <reified T> upload(url: String, key: String, file: File): Flow<Result<T>> {
        return flow {
            val response = httpClient.post(url) {
                MultiPartFormDataContent(formData {
                    //append(key, file.readBytes(), Headers.build {
                    //    append(HttpHeaders.ContentType, "image/*")
                    //    append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
                    //})
                    append(key, file.name, ContentType.Image.Any, file.length()) {
                        file.inputStream().let { input ->
                            this.write(input.readBytes())
                        }
                    }
                })
            }
            val result = response.body<T>()
            emit(Result.success(result))
        }.catch { cause: Throwable ->
            emit(handleException(cause))
        }.onCompletion {
            close()
        }.flowOn(Dispatchers.IO)
    }
}
