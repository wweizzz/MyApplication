package com.example.william.my.core.retrofit.function

import com.example.william.my.core.retrofit.exception.ServerResultException
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.google.gson.JsonElement
import io.reactivex.rxjava3.functions.Function

/**
 * 服务器返回结果，泛型转换(JsonElement -> 泛型)
 */
class RxRetrofitFunction<T> :
    Function<RetrofitResponse<JsonElement>, RetrofitResponse<T>> {

    @Suppress("UNCHECKED_CAST")
    override fun apply(response: RetrofitResponse<JsonElement>): RetrofitResponse<T> {
        //抛出服务器返回自定义异常
        if (!response.isSuccess) {
            throw ServerResultException(response.code, response.message)
        }
        return response as RetrofitResponse<T>
    }
}