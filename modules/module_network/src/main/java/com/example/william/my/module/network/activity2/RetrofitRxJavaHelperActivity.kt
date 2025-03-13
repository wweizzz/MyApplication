package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_data.api.NetworkApi
import com.example.william.my.basic.basic_data.bean.UserData
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.retrofit.callback.RetrofitResponseCallback
import com.example.william.my.core.retrofit.exception.ApiException
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit

/**
 * https://square.github.io/retrofit
 * https://github.com/square/retrofit
 */
@Route(path = RouterPath.Network.RetrofitRxJavaHelper)
class RetrofitRxJavaHelperActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "RetrofitRxJavaHelper login",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                loginSingle(Constants.Value_Username, Constants.Value_Password)
            }
        }
    }

    private fun loginSingle(username: String, password: String) {
        // 创建 Retrofit 实例
        val retrofit: Retrofit = RetrofitHelper.retrofit()

        // 创建网络请求接口实例
        val api: NetworkApi = RetrofitHelper.buildApi(NetworkApi::class.java, retrofit)

        // 调用网络接口中的方法获取 Observable 对象
        val single: Single<RetrofitResponse<UserData?>> = api.loginSingle(username, password)

        // 创建 异常管理与线程切换的 Observable 对象
        val retrofitSingle: Single<RetrofitResponse<UserData?>> = RetrofitHelper.buildSingle(single)

        // 进行网络请求
        retrofitSingle.subscribe(object : RetrofitResponseCallback<UserData?>() {
            override fun onLoading() {
                super.onLoading()
            }

            override fun onResponse(response: UserData?) {
                super.onResponse(response)
                showResponse(response?.string())
            }

            override fun onFailure(e: ApiException) {
                super.onFailure(e)
                showFailure(e.message)
            }
        })
    }
}