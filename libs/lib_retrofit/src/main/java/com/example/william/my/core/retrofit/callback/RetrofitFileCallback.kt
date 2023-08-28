package com.example.william.my.core.retrofit.callback

import com.example.william.my.core.retrofit.base.RetrofitCallback
import com.example.william.my.core.retrofit.exception.ApiException
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import java.io.File

/**
 * 处理基本逻辑
 */
open class RetrofitFileCallback : DisposableSingleObserver<File>(),
    RetrofitCallback<File> {

    override fun onSuccess(file: File) {
        onResponse(file)
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

    override fun onResponse(response: File) {

    }

    override fun onFailure(e: ApiException) {

    }
}