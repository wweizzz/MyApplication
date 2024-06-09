package com.example.william.my.module.network.activity3

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.router.service.FileIOUtilsService
import com.example.william.my.core.okhttp.helper.OkHttpHelper
import com.example.william.my.core.okhttp.interceptor.InterceptorProgress
import com.example.william.my.core.okhttp.listener.ResponseProgressListener
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import java.io.File

/**
 * https://square.github.io/okhttp
 * https://github.com/square/okhttp
 */
@Route(path = RouterPath.Network.OkHttpDownload)
class OkhttpDownloadActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        download()
    }

    private fun download() {
        // 创建 OkHttpClient 对象
        val client: OkHttpClient = OkHttpHelper.createBuilder()
            .addInterceptor(InterceptorProgress(object : ResponseProgressListener {
                override fun onProgress(url: String, currentSize: Long, totalSize: Long) {
                    val progress = (currentSize * 1f / totalSize * 100).toInt()
                    showResponse("下载进度：$progress%")
                }
            }))
            .build()

        // 创建 Request 对象
        val request: Request = OkHttpHelper.requestBuilder()
            .url(Constants.Url_Download)
            .get()
            .build()

        // 创建 Call 对象，并发送请求并获取服务器返回的数据
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    //for ((name, value) in response.headers) {
                    //    println("$name: $value")
                    //}

                    response.body?.let {
                        val fileIOUtils =
                            ARouter.getInstance().build(RouterPath.Service.FileIOUtilsService)
                                .navigation() as FileIOUtilsService
                        val file =
                            File(getExternalFilesDir("okhttp")?.absolutePath + File.separator + "okhttp.apk")
                        if (fileIOUtils.writeFileFromIS(file, it.byteStream())) {
                            showResponse("下载完成")
                        }
                    }
                }
            }
        })
    }
}