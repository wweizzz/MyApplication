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
 * 1. HTTP协议错误
 * 4xx/5xx状态码：如401（未授权）、404（资源不存在）、500（服务器内部错误）等，需根据状态码提示用户或重试。
 * 自定义业务错误：服务器返回的success=false或自定义错误码（如code=1001），需解析并统一处理。
 * 2. 网络连接异常
 * ConnectException：无网络或DNS解析失败。
 * SocketTimeoutException：请求超时。
 * SSLHandshakeException：证书验证失败。
 * 3. 数据解析错误
 * JsonParseException或JSONException：JSON格式与数据模型不匹配。
 * 服务器返回非约定格式（如错误时返回字符串而非对象）。
 * 4. 其他异常
 * UnknownHostException：域名解析失败。
 * IOException：网络请求过程中断（如用户取消请求）。
 */
object ExceptionHandler {

    fun handleException(e: Throwable): ApiException {
        val exception: ApiException
        return when (e) {
            is HttpException -> {
                exception = ApiException(e, ApiException.Error.HTTP_ERROR)
                exception.code = e.code()
                try {
                    e.response()?.errorBody()?.let { errorBody ->
                        val error = Gson().fromJson(errorBody.string(), HttpException::class.java)
                        error.message?.let { message ->
                            exception.message = message
                        } ?: {
                            exception.message = "请求网络失败，请检查您的网络设置或稍后重试！"
                        }
                    } ?: {
                        exception.message = "请求网络失败，请检查您的网络设置或稍后重试！"
                    }
                } catch (e1: Exception) {
                    exception.message = "请求网络失败，请检查您的网络设置或稍后重试！"
                }
                exception
            }

            is ServerResultException -> {
                exception = ApiException(e, e.code)
                exception.message = e.message
                exception
            }

            is ConnectException -> {
                exception = ApiException(e, ApiException.Error.CONNECT_ERROR)
                exception.message = "连接失败，请稍后再试"
                exception
            }

            is SocketTimeoutException -> {
                exception = ApiException(e, ApiException.Error.TIMEOUT_ERROR)
                exception.message = "连接超时，请稍后再试"
                exception
            }

            is SSLHandshakeException -> {
                exception = ApiException(e, ApiException.Error.SSL_ERROR)
                exception.message = "证书校验失败，请稍后再试"
                exception
            }

            is JsonParseException, is JSONException, is ParseException -> {
                exception = ApiException(e, ApiException.Error.PARSE_ERROR)
                exception.message = "解析错误，请稍后再试"
                exception
            }

            else -> {
                exception = ApiException(e, ApiException.Error.UNKNOWN)
                exception.message = "：未知错误，请稍后再试"
                exception
            }
        }
    }
}