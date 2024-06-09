package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.utils.AppExecutorsHelper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * setDoOutput() 和 setDoInput()：
 * 设置是否向 HttpURLConnection 输出与读取。
 * setDoOutput()默认是false，setDoInput()默认是true
 * setUseCaches()设置缓存，POST请求不能使用缓存
 *
 *
 * InputStream & OutputStream 输入流与输出流
 * 转换流
 * InputStreamReader & OutputStreamWriter 字符与节流转换
 * 缓冲流
 * 关闭了缓冲区对象实际也关闭了与缓冲区关联的流对象
 * BufferedReader & BufferedWriter 字符缓冲流
 * BufferedInputStream & BufferedOutputStream 字节缓冲流
 * 文件流
 * FileReader & FileWriter 字符文件流
 * FileInputStream & FileOutputStream 字节文件流
 */
@Route(path = RouterPath.Network.HttpURL)
class HttpURLActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "HttpURLConnection",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                post()
            }
        }
    }

    private fun post() {
        AppExecutorsHelper.networkIO().execute {
            post(Constants.Url_Login, Constants.LoginString)
        }
    }

    private fun post(urlString: String, params: String) {
        try {
            // 1. 获取访问地址URL
            val url = URL(urlString)
            // 2. 创建HttpURLConnection对象
            val connection = url.openConnection() as HttpURLConnection
            /* 3. 设置请求参数等 */
            // 请求方式
            connection.requestMethod = "POST"
            // 超时时间
            connection.connectTimeout = 3000
            // 设置使用标准编码格式编码参数：名-值对
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            // 建立连接
            connection.connect()

            /* 4. 处理输入输出 */
            val outputStream = connection.outputStream
            outputStream.write(params.toByteArray())
            outputStream.flush()
            outputStream.close()

            // 5. 得到响应状态码的返回值 responseCode
            val code = connection.responseCode
            // 6. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
            val response = StringBuilder()
            if (code == 200) {
                val `is` = connection.inputStream
                val reader = BufferedReader(InputStreamReader(`is`))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line).append("\n")
                }
                // 关闭流
                reader.close()
            }
            // 7. 断开连接
            connection.disconnect()
            showResponse(response.toString())
        } catch (e: IOException) {
            showFailure(e.message)
        }
    }
}