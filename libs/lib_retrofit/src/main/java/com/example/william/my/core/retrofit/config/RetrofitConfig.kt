package com.example.william.my.core.retrofit.config

import com.example.william.my.core.okhttp.helper.OkHttpHelper
import com.example.william.my.core.retrofit.converter.RetrofitConverterFactory
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object RetrofitConfig {

    private var mBaseUrl = "http://host/"

    private var mOkHttpClient: OkHttpClient = OkHttpHelper.client()

    private var mConverterFactory: Converter.Factory = RetrofitConverterFactory.create()

    private var mCallAdapterFactory: CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    private const val mCode: String =
        "errorCode" // SerializedName, An annotation argument must be a compile-time constant
    private const val mMessage: String =
        "errorMsg" // SerializedName, An annotation argument must be a compile-time constant

    fun getBaseUrl(): String {
        return mBaseUrl
    }

    fun getOkHttpClient(): OkHttpClient {
        return mOkHttpClient
    }

    fun getConverterFactory(): Converter.Factory {
        return mConverterFactory
    }

    fun getCallAdapterFactory(): CallAdapter.Factory {
        return mCallAdapterFactory
    }

    fun getCode(): String {
        return mCode
    }

    fun getMessage(): String {
        return mMessage
    }

    open class Builder {

        fun setBaseUrl(url: String): Builder {
            mBaseUrl = url
            return this
        }

        fun setOkHttpClient(client: OkHttpClient): Builder {
            mOkHttpClient = client
            return this
        }

        fun setConverterFactory(factory: Converter.Factory): Builder {
            return this
        }

        fun setCallAdapterFactory(factory: CallAdapter.Factory): Builder {
            mCallAdapterFactory = factory
            return this
        }
    }
}