package com.example.william.my.core.retrofit.callback

import com.example.william.my.core.retrofit.base.RetrofitCallback
import com.example.william.my.core.retrofit.exception.ApiException
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import okhttp3.ResponseBody
import java.io.InputStream

/**
 * 处理基本逻辑
 */
abstract class RetrofitFileCallback :
    DisposableSingleObserver<ResponseBody>(), RetrofitCallback<InputStream> {

    override fun onSuccess(t: ResponseBody) {
        onResponse(t.byteStream())
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

    override fun onResponse(response: InputStream?) {

    }

    override fun onFailure(e: ApiException) {

    }
}