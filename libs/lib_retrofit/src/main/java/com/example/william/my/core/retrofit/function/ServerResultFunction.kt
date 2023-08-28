package com.example.william.my.core.retrofit.function

import com.example.william.my.core.retrofit.exception.ServerResultException
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.functions.Function

/**
 * 服务器返回结果
 */
class ServerResultFunction<T> : Function<RetrofitResponse<T>, RetrofitResponse<T>> {

    override fun apply(response: RetrofitResponse<T>): RetrofitResponse<T> {
        //抛出服务器返回自定义异常
        if (!response.isSuccess) {
            throw ServerResultException(response.code, response.message)
        }
        return response
    }
}