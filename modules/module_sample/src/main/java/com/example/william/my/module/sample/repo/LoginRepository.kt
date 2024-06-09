package com.example.william.my.module.sample.repo

import com.example.william.my.basic.basic_data.bean.Login
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.module.sample.data.NetworkResult
import com.example.william.my.module.sample.utils.ThreadUtils
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Android 上的 Kotlin 协程
 * https://developer.android.google.cn/kotlin/coroutines
 * <p>
 * suspend -> Result
 */
@Deprecated(message = "HttpURLConnection")
class LoginRepository(private val defaultDispatcher: CoroutineDispatcher) {

    /**
     * 2. 使用协程确保主线程安全
     * <p>
     * 将 协程 切换到 I/O 调度，确保主线程安全
     * Move the execution of the coroutine to the I/O dispatcher
     */
    suspend fun login(username: String, password: String): NetworkResult<Login> {

        return withContext(defaultDispatcher) {
            //打印线程
            ThreadUtils.isMainThread("LoginRepository login")

            // 阻塞网络请求
            // Blocking network request code
            makeLoginRequest(username, password)
        }
    }

    /**
     * 1. 在后台线程中执行
     * <p>
     * 发出网络请求，阻塞当前线程
     * Function that makes the network request, blocking the current thread
     */
    private fun makeLoginRequest(username: String, password: String): NetworkResult<Login> {
        //打印线程
        ThreadUtils.isMainThread("CoroutinesRepository makeLoginRequest")

        val url = URL(Constants.Url_Login)
        val jsonString = "username=$username&password=$password"
        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            //setRequestProperty("Content-Type", "application/json; utf-8")//发送的实体数据的数据类型
            //setRequestProperty("Accept", "application/json") //希望接受的数据类型
            doOutput = true
            outputStream.write(jsonString.toByteArray())
            return NetworkResult.Success(parseInputStream(inputStream))
        }
        return NetworkResult.Error(Exception("Cannot open HttpURLConnection"))
    }

    private fun parseInputStream(input: InputStream): Login {
        val msg = StringBuilder()
        val reader = BufferedReader(InputStreamReader(input))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            msg.append(line)
        }
        reader.close()
        val response = msg.toString()
        return Gson().fromJson(response, Login::class.java)
    }
}