package com.example.william.my.module.network.activity3

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_data.api.NetworkApi
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.router.service.FileIOUtilsService
import com.example.william.my.core.okhttp.helper.OkHttpHelper
import com.example.william.my.core.okhttp.interceptor.InterceptorProgress
import com.example.william.my.core.okhttp.listener.ResponseProgressListener
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File

/**
 * https://square.github.io/retrofit
 * https://github.com/square/retrofit
 */
@Route(path = RouterPath.Network.RetrofitDownload)
class RetrofitDownloadActivity : BasicResponseActivity() {

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

        // （2）创建 Retrofit 实例
        val retrofit: Retrofit = RetrofitHelper.client(client).retrofit()

        // （3）创建网络请求接口实例
        val service: NetworkApi = RetrofitHelper.buildApi(NetworkApi::class.java, retrofit)

        // （4）调用网络接口中的方法获取 Call 对象
        val call: Call<ResponseBody> = service.downloadFile(Constants.Url_Download)

        // （5）进行网络请求
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                response.body()?.let {
                    val fileIOUtils =
                        ARouter.getInstance().build(RouterPath.Service.FileIOUtilsService)
                            .navigation() as FileIOUtilsService
                    val file =
                        File(getExternalFilesDir("retrofit")?.absolutePath + File.separator + "retrofit.apk")
                    if (fileIOUtils.writeFileFromIS(file, it.byteStream())) {
                        showResponse("下载完成")
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                val netError = "onFailure: " + t.message
                showResponse(netError)
            }
        })
    }
}