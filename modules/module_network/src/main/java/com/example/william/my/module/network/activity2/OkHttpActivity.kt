package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

/**
 * https://square.github.io/okhttp
 * https://github.com/square/okhttp
 */
@Route(path = RouterPath.Network.OkHttp)
class OkHttpActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "Posting a String",
            "Posting a parameter",
            "Posting a multipart request"
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                postingString(
                    Constants.Url_Login,
                    Constants.LoginString
                )
            }

            1 -> {
                postingParam(
                    Constants.Url_Login,
                    Constants.Value_Username,
                    Constants.Value_Password
                )

            }

            2 -> {
                postingMultipart(
                    Constants.Url_Login,
                    Constants.Value_Username,
                    Constants.Value_Password
                )
            }
        }
    }

    // 创建 OkHttpClient 对象
    private val client: OkHttpClient = OkHttpClient()

    private fun postingString(url: String, postString: String) {
        val postBody = postString
            .toRequestBody("application/x-www-form-urlencoded".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(postBody)
            .build()

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

    private fun postingParam(url: String, username: String, password: String) {
        val formBody = FormBody.Builder()
            .add(Constants.Key_Username, username)
            .add(Constants.Key_Password, password)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

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

    private fun postingMultipart(url: String, username: String, password: String) {
        val multipartBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(Constants.Key_Username, username)
            .addFormDataPart(Constants.Key_Password, password)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(
                multipartBody
            ).build()

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