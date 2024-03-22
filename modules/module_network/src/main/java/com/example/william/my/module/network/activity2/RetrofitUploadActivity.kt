package com.example.william.my.module.network.activity2

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
@Route(path = RouterPath.Network.RetrofitUpload)
class RetrofitUploadActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        upload()
    }

    private fun upload() {

    }
}