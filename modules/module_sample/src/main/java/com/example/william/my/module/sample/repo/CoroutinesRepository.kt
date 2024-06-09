package com.example.william.my.module.sample.repo

import com.example.william.my.basic.basic_data.api.NetworkApi
import com.example.william.my.basic.basic_data.bean.Login
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.module.sample.data.NetworkResult
import com.example.william.my.module.sample.utils.ThreadUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Android 上的 Kotlin 协程
 * https://developer.android.google.cn/kotlin/coroutines
 * <p>
 * suspend -> Result
 */
class CoroutinesRepository(private val defaultDispatcher: CoroutineDispatcher) {

    private val api = RetrofitHelper.buildApi(NetworkApi::class.java)

    suspend fun login(
        username: String,
        password: String
    ): NetworkResult<RetrofitResponse<Login?>> {

        return withContext(defaultDispatcher) {
            //打印线程
            ThreadUtils.isMainThread("CoroutinesRepository login")

            // 阻塞网络请求
            // Blocking network request code
            NetworkResult.Success(api.loginSuspend(username, password))
        }
    }

    companion object {
        private val TAG = CoroutinesRepository::class.java.simpleName
    }
}