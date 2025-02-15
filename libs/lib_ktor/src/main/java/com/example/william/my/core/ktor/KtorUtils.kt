package com.example.william.my.core.ktor

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion

object KtorUtils {

    val httpClient = HttpClient(Android) {

        engine {
            // this: HttpClientEngineConfig
            pipelining = true
        }

        install(ContentNegotiation) {
            gson()
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
        }

        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay() // 指定重试之间的指数延迟，该延迟使用 Exponential backoff 算法计算。
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Ktor Logging : ", message)
                }
            }
            level = LogLevel.ALL
            filter { request ->
                request.url.host.contains("ktor.io")
            }
            sanitizeHeader { header ->
                header == HttpHeaders.Authorization
            }
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("Ktor ResponseObserver : ", "${response.status.value}")
            }
        }

        defaultRequest {
            header("X-Custom-Header", "Authorization")
        }
    }

    fun close() {
        httpClient.close()
    }

    inline fun <reified T> get(url: String, params: Map<String, String> = emptyMap()): Flow<T> {
        return flow {
            val response = httpClient.get(url) {
                params.forEach {
                    parameter(it.key, it.value)
                }
            }
            val result = response.body<T>()
            emit(result)
        }.catch { throwable: Throwable ->
            throw throwable
        }.onCompletion { cause ->
            close()
        }.flowOn(Dispatchers.IO)
    }

    inline fun <reified T> post(url: String, params: Map<String, String> = emptyMap()): Flow<T> {
        return flow {
            val response = httpClient.post(url) {
                params.forEach {
                    parameter(it.key, it.value)
                }
            }
            val result = response.body<T>()
            emit(result)
        }.catch { throwable: Throwable ->
            throw throwable
        }.onCompletion { cause ->
            close()
        }.flowOn(Dispatchers.IO)
    }
}
