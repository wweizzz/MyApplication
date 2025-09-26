package com.example.william.my.module.network.utils

import com.example.william.my.basic.basic_module.utils.Utils
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

object HttpURLUtils {

    const val DEFAULT_PARAMS_ENCODING: String = "UTF-8"

    const val CONTENT_TYPE_JSON = "application/json; charset=utf-8"
    const val CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=utf-8"

    fun postForm(
        url: String,
        params: Map<String, String>,
        listener: Listener<String>,
        errorListener: ErrorListener
    ) {
        try {
            // 1. 获取访问地址URL
            val url = URL(url)
            // 2. 创建HttpURLConnection对象
            val connection = url.openConnection() as HttpURLConnection

            /* 3. 设置请求参数等 */

            // 请求方式
            connection.requestMethod = "POST"

            // 超时时间
            connection.connectTimeout = 3000
            connection.readTimeout = 3000

            // 打印请求头
            logcat("HTTP_REQUEST", "Request Method: " + connection.requestMethod)
            logcat("HTTP_REQUEST", "Request Headers: " + connection.requestProperties.toString())

            // 设置使用标准编码格式编码参数：名-值对
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_FORM)

            // 建立连接
            connection.connect()

            /* 4. 处理输入输出 */

            val outputStream = connection.outputStream

            outputStream.write(encodedParams(params, getParamsEncoding()))
            outputStream.flush()
            outputStream.close()

            val headers = connection.headerFields
            logcat("HTTP_RESPONSE", "Response Headers: $headers")


            // 5. 得到响应状态码的返回值 responseCode
            val code = connection.responseCode
            logcat("HTTP_RESPONSE", "Response Code: $code")

            // 6. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
            val response = StringBuilder()
            if (code == 200) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line).append("\n")
                }
                // 关闭流
                reader.close()
                logcat("HTTP_RESPONSE", "Response Body: $response")
            }
            // 7. 断开连接
            connection.disconnect()

            listener.onResponse(response.toString())
        } catch (e: Exception) {
            errorListener.onErrorResponse(e)
        }
    }

    fun postJson(
        url: String,
        json: JSONObject,
        listener: Listener<String>,
        errorListener: ErrorListener
    ) {
        try {
            // 1. 获取访问地址URL
            val url = URL(url)
            // 2. 创建HttpURLConnection对象
            val connection = url.openConnection() as HttpURLConnection

            /* 3. 设置请求参数等 */

            // 请求方式
            connection.requestMethod = "POST"

            // 超时时间
            connection.connectTimeout = 3000
            connection.readTimeout = 3000

            // 打印请求头
            logcat("HTTP_REQUEST", "Request Method: " + connection.requestMethod)
            logcat("HTTP_REQUEST", "Request Headers: " + connection.requestProperties.toString())

            // 设置使用标准编码格式编码参数：名-值对
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON)

            // 建立连接
            connection.connect()

            /* 4. 处理输入输出 */

            val outputStream = connection.outputStream

            outputStream.write(json.toString().toByteArray())
            outputStream.flush()
            outputStream.close()

            val headers = connection.headerFields
            logcat("HTTP_RESPONSE", "Response Headers: $headers")


            // 5. 得到响应状态码的返回值 responseCode
            val code = connection.responseCode
            logcat("HTTP_RESPONSE", "Response Code: $code")

            // 6. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
            val response = StringBuilder()
            if (code == 200) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line).append("\n")
                }
                // 关闭流
                reader.close()
                logcat("HTTP_RESPONSE", "Response Body: $response")
            }
            // 7. 断开连接
            connection.disconnect()

            listener.onResponse(response.toString())
        } catch (e: Exception) {
            errorListener.onErrorResponse(e)
        }
    }

    private fun getParamsEncoding(): String {
        return DEFAULT_PARAMS_ENCODING
    }

    private fun encodedParams(params: Map<String, String>, paramsEncoding: String): ByteArray {
        val encodedParams = StringBuilder()
        for ((key, value) in params) {
            encodedParams.append(URLEncoder.encode(key, paramsEncoding))
            encodedParams.append('=')
            encodedParams.append(URLEncoder.encode(value, paramsEncoding))
            encodedParams.append('&')
        }
        return encodedParams.toString().toByteArray(charset(paramsEncoding))
    }

    /** Callback interface for delivering parsed responses. */
    fun interface Listener<T> {
        /**
         * Called when a response is received.
         */
        fun onResponse(response: T)
    }

    /** Callback interface for delivering error responses.  */
    fun interface ErrorListener {
        /**
         * Callback method that an error has been occurred with the provided error code and optional
         * user-readable message.
         */
        fun onErrorResponse(error: Exception?)
    }

    fun logcat(tag: String, message: String) {
        Utils.logcat(tag, message)
    }
}