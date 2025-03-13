package com.example.william.my.module.sample.repo

import com.example.william.my.basic.basic_data.api.NetworkApi
import com.example.william.my.basic.basic_data.bean.LoginData
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.example.william.my.lib.utils.Utils
import com.example.william.my.module.sample.utils.ThreadUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Android 上的 Kotlin 数据流
 * https://developer.android.google.cn/kotlin/flow
 * <p>
 * suspend -> Flow
 */
class FlowRepository(private val defaultDispatcher: CoroutineDispatcher) {

    private val api = RetrofitHelper.buildApi(NetworkApi::class.java)

    /**
     * 1. 创建数据流
     */
    private fun createFlow(username: String, password: String): Flow<RetrofitResponse<LoginData?>> {
        return flow {
            //打印线程
            ThreadUtils.isMainThread("FlowRepository login")

            // Emits the result of the request to the flow
            emit(api.loginSuspend(username, password))
        }
            // Executes on the IO dispatcher
            .flowOn(defaultDispatcher)
    }

    /**
     * 2. 修改数据流
     * 这些操作是惰性的，不会触发流。它们只是转换流在该时间点发出的当前值。
     * These operations are lazy and don't trigger the flow.
     * They just transform the current value emitted by the flow at that point in time.
     */
    fun login(username: String, password: String): Flow<RetrofitResponse<LoginData?>> {
        return createFlow(username, password)
            // 在默认调度程序上执行
            // Executes on the default dispatcher
            .map { news ->
                news
            }
            // 在默认调度程序上执行
            // Executes on the default dispatcher
            .onEach {

            }
            // flowOn 影响上游的 flow
            // flowOn affects the upstream flow ↑
            .flowOn(defaultDispatcher)
            // 下游的 flow 不受影响
            // the downstream flow ↓ is not affected
            .catch { exception -> // Executes in the consumer's context
                Utils.e(TAG, "exception : " + exception.message.toString())
            }
    }

    companion object {
        private val TAG = FlowRepository::class.java.simpleName
    }

}