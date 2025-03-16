package com.example.william.my.module.network.activity4

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_data.api.NetworkApi
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

/**
 * https://square.github.io/retrofit
 * https://github.com/square/retrofit
 */
@Route(path = RouterPath.Network.RetrofitHelper)
class RetrofitHelperActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "RetrofitHelper loginCall",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                loginCall(Constants.Value_Username, Constants.Value_Password)
            }
        }
    }


    private fun loginCall(username: String, password: String) {
        // （2）创建 Retrofit 实例
        val retrofit: Retrofit = RetrofitHelper.retrofit()

        // （3）创建网络请求接口实例
        val api: NetworkApi = RetrofitHelper.buildApi(NetworkApi::class.java, retrofit)

        // （4）调用网络接口中的方法获取 Call 对象
        val call: Call<ResponseBody> = api.loginCall(username, password)

        // （5）进行网络请求
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                showResponse(response.body()?.string())
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                showFailure(t.message)
            }
        })
    }
}