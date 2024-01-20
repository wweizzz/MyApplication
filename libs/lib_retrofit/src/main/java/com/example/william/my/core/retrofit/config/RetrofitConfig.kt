package com.example.william.my.core.retrofit.config

import com.example.william.my.core.okhttp.helper.OkHttpHelper
import com.example.william.my.core.retrofit.converter.RetrofitConverterFactory
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object RetrofitConfig {

    private var mBaseUrl = "http://host/"

    private var mErrorCode: String = "code"

    private var mErrorMessage: String = "message"

    private var mOkHttpClient: OkHttpClient = OkHttpHelper.client()

    private var mConverterFactory: Converter.Factory = RetrofitConverterFactory.create()

    private var mCallAdapterFactory: CallAdapter.Factory = RxJava3CallAdapterFactory.create()

    fun getBaseUrl(): String {
        return mBaseUrl
    }

    fun getErrorCode(): String {
        return mErrorCode
    }

    fun getErrorMessage(): String {
        return mErrorMessage
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

    open class Builder {

        fun setBaseUrl(url: String): Builder {
            RetrofitConfig.mBaseUrl = url
            return this
        }

        fun setErrorCode(code: String): Builder {
            RetrofitConfig.mErrorCode = code
            return this
        }

        fun setErrorMessage(message: String): Builder {
            RetrofitConfig.mErrorMessage = message
            return this
        }

        fun setOkHttpClient(client: OkHttpClient): Builder {
            RetrofitConfig.mOkHttpClient = client
            return this
        }

        fun setConverterFactory(factory: Converter.Factory): Builder {
            RetrofitConfig.mConverterFactory = mConverterFactory
            return this
        }

        fun setCallAdapterFactory(factory: CallAdapter.Factory): Builder {
            RetrofitConfig.mCallAdapterFactory = factory
            return this
        }
    }
}