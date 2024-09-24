package com.example.william.my.core.retrofit.callback

import com.example.william.my.core.retrofit.base.RetrofitCallback
import com.example.william.my.core.retrofit.exception.ApiException
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.observers.DisposableSingleObserver

/**
 * 处理基本逻辑
 */
abstract class RetrofitResponseCallback<T> :
    DisposableSingleObserver<RetrofitResponse<T>>(), RetrofitCallback<T> {

    override fun onSuccess(t: RetrofitResponse<T>) {
        onToast(t.message)
        onResponse(t.data)
    }

    override fun onError(e: Throwable) {
        if (e is ApiException) {
            onFailure(e)
        } else {
            onFailure(ApiException(e, ApiException.Error.UNKNOWN))
        }
    }

    override fun onLoading() {

    }

    override fun onToast(message: String?) {

    }

    override fun onResponse(response: T?) {

    }

    override fun onFailure(e: ApiException) {

    }
}