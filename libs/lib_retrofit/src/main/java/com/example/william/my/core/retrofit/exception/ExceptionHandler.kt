package com.example.william.my.core.retrofit.exception

import android.net.ParseException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

/**
 * ApiException
 */
object ExceptionHandler {

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        return when (e) {
            is HttpException -> {
                ex = ApiException(e, ApiException.Error.HTTP_ERROR)
                ex.code = e.code()
                try {
                    e.response()?.let { response ->
                        response.errorBody()?.let { errorBody ->
                            val exception =
                                Gson().fromJson(errorBody.string(), HttpException::class.java)
                            exception.message?.let { message ->
                                ex.message = message
                            } ?: {
                                ex.message = "请求网络失败，请检查您的网络设置或稍后重试！"
                            }
                        } ?: {
                            ex.message = "请求网络失败，请检查您的网络设置或稍后重试！"
                        }
                    } ?: {
                        ex.message = "请求网络失败，请检查您的网络设置或稍后重试！"
                    }
                } catch (e1: Exception) {
                    ex.message = "请求网络失败，请检查您的网络设置或稍后重试！"
                }
                ex
            }

            is ServerResultException -> {
                ex = ApiException(e, e.code)
                ex.message = e.message
                ex
            }

            is JsonParseException, is JSONException, is ParseException -> {
                ex = ApiException(e, ApiException.Error.PARSE_ERROR)
                ex.message = "解析错误，请稍后再试"
                ex
            }

            is ConnectException -> {
                ex = ApiException(e, ApiException.Error.NETWORK_ERROR)
                ex.message = "连接失败，请稍后再试"
                ex
            }

            is SocketTimeoutException -> {
                ex = ApiException(e, ApiException.Error.CONNECT_ERROR)
                ex.message = "连接超时，请稍后再试"
                ex
            }

            is SSLHandshakeException -> {
                ex = ApiException(e, ApiException.Error.SSL_ERROR)
                ex.message = "证书验证失败，请稍后再试"
                ex
            }

            else -> {
                ex = ApiException(e, ApiException.Error.UNKNOWN)
                ex.message = "：未知错误，请稍后再试"
                ex
            }
        }
    }
}