package com.example.william.my.module.network.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.api.NetworkApi
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * https://square.github.io/retrofit
 * https://github.com/square/retrofit
 */
@Route(path = ARouterPath.Network.Retrofit)
class RetrofitActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        asynchronousPost()
    }

    private fun asynchronousPost() {
        // （2）创建 Retrofit 实例
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.Url_Base) // baseUlr必须以 /（斜线）结束，不然会抛出一个IllegalArgumentException
            .build()

        // （3）创建网络请求接口实例
        val api: NetworkApi = retrofit.create(NetworkApi::class.java)

        // （4）调用网络接口中的方法获取 Call 对象
        val call: Call<ResponseBody> =
            api.loginCall(Constants.Value_Username, Constants.Value_Password)

        // （5）进行网络请求
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val netSuccess = "onResponse: " + response.body()?.string()
                showResponse(netSuccess)
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                val netError = "onFailure: " + t.message
                showResponse(netError)
            }
        })
    }
}