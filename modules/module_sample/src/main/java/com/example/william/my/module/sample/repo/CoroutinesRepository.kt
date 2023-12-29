package com.example.william.my.module.sample.repo

import com.example.william.my.basic.basic_module.bean.UserBean
import com.example.william.my.basic.basic_repository.api.NetworkApi2
import com.example.william.my.basic.basic_repository.data.NetworkResult
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import com.example.william.my.core.retrofit.response.RetrofitResponse
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

    private val api = RetrofitHelper.buildApi(NetworkApi2::class.java)

    suspend fun login(
        username: String,
        password: String
    ): NetworkResult<RetrofitResponse<UserBean>> {

        return withContext(defaultDispatcher) {
            //打印线程
            ThreadUtils.isMainThread("CoroutinesRepository loginByRetrofit")

            // 阻塞网络请求
            // Blocking network request code
            NetworkResult.Success(api.loginSuspend(username, password))
        }
    }
}