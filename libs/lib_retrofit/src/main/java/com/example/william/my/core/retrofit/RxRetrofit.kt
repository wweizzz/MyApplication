package com.example.william.my.core.retrofit

import com.example.william.my.core.okhttp.media.MediaType
import com.example.william.my.core.retrofit.api.Api
import com.example.william.my.core.retrofit.builder.RetrofitBuilder
import com.example.william.my.core.retrofit.function.HttpResultFunction
import com.example.william.my.core.retrofit.function.RxRetrofitFunction
import com.example.william.my.core.retrofit.helper.RetrofitHelper
import com.example.william.my.core.retrofit.method.Method
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.google.gson.JsonElement
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * Http请求类
 */
class RxRetrofit<T>(private val builder: RetrofitBuilder<T>) {

    private fun buildApi(): Api {
        return RetrofitHelper
            .retrofit()
            .create(Api::class.java)
    }

    fun createResponse(): Single<RetrofitResponse<T>> {
        lateinit var response: Single<RetrofitResponse<T>>
        lateinit var responseSingle: Single<RetrofitResponse<JsonElement>>
        when (builder.getMethod()) {
            Method.GET -> {
                responseSingle =
                    buildApi().get(builder.getApi(), builder.getHeader(), builder.getParam())
            }

            Method.POST -> {
                responseSingle =
                    if (!builder.hasBody()) {
                        buildApi().post(builder.getApi(), builder.getHeader(), builder.getParam())
                    } else {
                        val body: RequestBody =
                            builder.getBodyString()
                                ?.toRequestBody(
                                    if (builder.isJson()) MediaType.MEDIA_TYPE_JSON
                                    else MediaType.MEDIA_TYPE_FORM
                                )
                                ?: builder.getBodyForm()!!.build()
                        buildApi().post(builder.getApi(), builder.getHeader(), body)
                    }
            }

            Method.PUT -> {
                responseSingle =
                    buildApi().put(builder.getApi(), builder.getHeader(), builder.getParam())
            }

            Method.DELETE -> {
                responseSingle =
                    buildApi().delete(builder.getApi(), builder.getHeader(), builder.getParam())
            }
        }
        response = responseSingle
            .map(RxRetrofitFunction<T>())
            .onErrorResumeNext(HttpResultFunction())

        builder.getLifecycle()?.let { lifecycle ->
            response = response.compose(lifecycle.bindToLifecycle())
        }

        response = response.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return response
    }

    companion object {
        fun <T> builder(): RetrofitBuilder<T> {
            return RetrofitBuilder()
        }
    }
}