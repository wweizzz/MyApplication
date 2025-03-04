package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.utils.AppExecutorsHelper
import com.example.william.my.module.network.utils.HttpURLUtils
import org.json.JSONObject

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
            "HttpURL postForm",
            "HttpURL postJson",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                AppExecutorsHelper.networkIO().execute {
                    postForm(Constants.Value_Username, Constants.Value_Password)
                }
            }

            1 -> {
                AppExecutorsHelper.networkIO().execute {
                    postJson(Constants.Value_Username, Constants.Value_Password)
                }
            }
        }
    }

    private fun postForm(username: String, password: String) {
        val params = mutableMapOf<String, String>()
        params[Constants.Key_Username] = username
        params[Constants.Key_Password] = password

        HttpURLUtils.postForm(Constants.Url_Login, params,
            listener = {
                showResponse(it)
            },
            errorListener = {
                showFailure(it?.message)
            })
    }

    private fun postJson(username: String, password: String) {
        val jsonObject = JSONObject()
            .put(Constants.Key_Username, username)
            .put(Constants.Key_Password, password)

        HttpURLUtils.postJson(Constants.Url_Login, jsonObject,
            listener = {
                showResponse(it)
            },
            errorListener = {
                showFailure(it?.message)
            })
    }
}