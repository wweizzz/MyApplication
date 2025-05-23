package com.example.william.my.module.network.activity3

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.okhttp.helper.OkHttpHelper
import com.example.william.my.lib.utils.AppExecutorsHelper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException

/**
 * https://square.github.io/okhttp
 * https://github.com/square/okhttp
 */
@Route(path = RouterPath.Network.OkHttpHelper)
class OkHttpHelperActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "OkHttpHelper Posting a FormBody",
            "OkHttpHelper Posting a MultipartBody",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                AppExecutorsHelper.networkIO().execute {
                    postingForm(Constants.Value_Username, Constants.Value_Password)
                }
            }

            1 -> {
                AppExecutorsHelper.networkIO().execute {
                    postingMultipart(Constants.Value_Username, Constants.Value_Password)
                }
            }
        }
    }

    /**
     * FormBody
     */
    private fun postingForm(username: String, password: String) {
        // 创建 OkHttpClient 对象
        val client: OkHttpClient = OkHttpHelper.client()

        // 创建 RequestBody 对象
        val requestBody: RequestBody = OkHttpHelper.requestBodyBuilder()
            .addForm(Constants.Key_Username, username)
            .addForm(Constants.Key_Password, password)
            .buildForm()

        // 创建 Request 对象
        val request = OkHttpHelper.requestBuilder()
            .url(Constants.Url_Login)
            .post(requestBody)
            .build()

        // 创建 Call 对象，并发送请求并获取服务器返回的数据
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                showFailure(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    //for ((name, value) in response.headers) {
                    //    println("$name: $value")
                    //}

                    response.body?.let {
                        showResponse(it.string())
                    }
                }
            }
        })
    }

    /**
     * MultipartBody
     */
    private fun postingMultipart(username: String, password: String) {
        // 创建 OkHttpClient 对象
        val client: OkHttpClient = OkHttpHelper.client()

        // 创建 RequestBody 对象
        val requestBody: RequestBody = OkHttpHelper.requestBodyBuilder()
            .addMultipart(Constants.Key_Username, username)
            .addMultipart(Constants.Key_Password, password)
            .buildMultipart()

        // 创建 Request 对象
        val request: Request = OkHttpHelper.requestBuilder()
            .url(Constants.Url_Login)
            .post(requestBody)
            .build()

        // 创建 Call 对象，并发送请求并获取服务器返回的数据
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                showFailure(e.message)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    //for ((name, value) in response.headers) {
                    //    println("$name: $value")
                    //}

                    response.body?.let {
                        showResponse(it.string())
                    }
                }
            }
        })
    }
}