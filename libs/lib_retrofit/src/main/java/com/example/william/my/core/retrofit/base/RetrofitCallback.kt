package com.example.william.my.core.retrofit.base

import com.example.william.my.core.retrofit.exception.ApiException

interface RetrofitCallback<T> {
    /**
     * onLoading
     */
    fun onLoading()

    /**
     * onResponse
     *
     * @param response
     */
    fun onResponse(response: T?)

    /**
     * onFailure
     *
     * @param e
     */
    fun onFailure(e: ApiException)
}