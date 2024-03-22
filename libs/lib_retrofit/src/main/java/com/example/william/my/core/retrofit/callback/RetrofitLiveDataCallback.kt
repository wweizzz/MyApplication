package com.example.william.my.core.retrofit.callback

import com.example.william.my.core.retrofit.base.RetrofitCallback
import com.example.william.my.core.retrofit.exception.ApiException
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.observers.DisposableSingleObserver

/**
 * 处理基本逻辑
 * 携带状态的 LiveData Callback
 */
abstract class RetrofitLiveDataCallback<T> :
    DisposableSingleObserver<RetrofitResponse<T>>(), RetrofitCallback<RetrofitResponse<T>> {

    override fun onSuccess(t: RetrofitResponse<T>) {
        onResponse(t)
    }

    override fun onError(e: Throwable) {
        if (e is ApiException) {
            onFailure(e)
        } else {
            onFailure(ApiException(e, ApiException.Error.UNKNOWN))
        }
    }

    override fun onLoading() {
        onPostValue(RetrofitResponse.loading())
    }

    override fun onResponse(response: RetrofitResponse<T>?) {
        try {
            onPostValue(response)
        } catch (e: Exception) {
            onPostValue(RetrofitResponse.error("数据异常"))
        }
    }

    override fun onFailure(e: ApiException) {
        onPostValue(RetrofitResponse.error(e.message))
    }

    open fun onPostValue(value: RetrofitResponse<T>?) {

    }
}