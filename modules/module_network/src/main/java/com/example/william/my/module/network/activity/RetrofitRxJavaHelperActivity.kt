package com.example.william.my.module.network.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.api.NetworkApi
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.bean.UserBean
import com.example.william.my.basic.basic_module.router.path.ARouterPath
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
@Route(path = ARouterPath.Network.RetrofitRxJavaHelper)
class RetrofitRxJavaHelperActivity : BasicResponseActivity() {

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        buildSingle()
    }

    private fun buildSingle() {
        // 创建 Retrofit 实例
        val retrofit: Retrofit = RetrofitHelper.retrofit()

        // 创建网络请求接口实例
        val api: NetworkApi = RetrofitHelper.buildApi(NetworkApi::class.java, retrofit)

        // 调用网络接口中的方法获取 Observable 对象
        val single: Single<RetrofitResponse<UserBean>> =
            api.loginSingle(Constants.Value_Username, Constants.Value_Password)

        // 创建 异常管理与线程切换的 Observable 对象
        val retrofitSingle: Single<RetrofitResponse<UserBean>> =
            RetrofitHelper.buildSingle(single)

        // 进行网络请求
        retrofitSingle.subscribe(object : RetrofitResponseCallback<UserBean>() {
            override fun onLoading() {
                super.onLoading()
                val netSuccess = "onLoading: "
                showResponse(netSuccess)
            }

            override fun onResponse(response: UserBean) {
                super.onResponse(response)
                val netSuccess = "onResponse: " + response.string()
                showResponse(netSuccess)
            }

            override fun onFailure(e: ApiException) {
                super.onFailure(e)
                val netError = "onFailure: " + e.message
                showResponse(netError)
            }
        })
    }
}