package com.example.william.my.module.network.activity2

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicRecyclerActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.ARouterPath
import com.example.william.my.basic.basic_repo.api.NetworkApi
import com.example.william.my.basic.basic_repo.bean.UserBean
import com.example.william.my.core.retrofit.converter.RetrofitConverterFactory
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

/**
 * https://square.github.io/retrofit
 * https://github.com/square/retrofit
 */
@Route(path = ARouterPath.Network.RetrofitRxJava)
class RetrofitRxJavaActivity : BasicRecyclerActivity() {

    override fun buildList(): ArrayList<String> {
        return arrayListOf(
            "RetrofitRxJava login",
        )
    }

    override fun onRecyclerClick(position: Int, string: String) {
        super.onRecyclerClick(position, string)
        when (position) {
            0 -> {
                loginSingle()
            }
        }
    }

    private fun loginSingle() {
        // （2）创建 Retrofit 实例
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.Url_Base)
            .addConverterFactory(RetrofitConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        // （3）创建网络请求接口实例
        val api: NetworkApi = retrofit.create(NetworkApi::class.java)

        // （4）调用网络接口中的方法获取 Observable 对象
        val single: Single<RetrofitResponse<UserBean>> =
            api.loginSingle(Constants.Value_Username, Constants.Value_Password)

        // （5）进行网络请求
        single
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<RetrofitResponse<UserBean>>() {
                override fun onSuccess(response: RetrofitResponse<UserBean>) {
                    val netSuccess = "onResponse: " + response.string()
                    showMessage(netSuccess)
                }

                override fun onError(e: Throwable) {
                    val netError = "onFailure: " + e.message
                    showMessage(netError)
                }
            })
    }
}