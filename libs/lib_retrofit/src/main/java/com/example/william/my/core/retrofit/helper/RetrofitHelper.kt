package com.example.william.my.core.retrofit.helper

import com.example.william.my.core.retrofit.config.RetrofitConfig
import com.example.william.my.core.retrofit.function.HttpResultFunction
import com.example.william.my.core.retrofit.function.ServerResultFunction
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

object RetrofitHelper {

    private var mRetrofit: Retrofit? = null

    fun retrofit(): Retrofit {
        return mRetrofit ?: createRetrofit()
    }

    fun baseUrl(url: String): RetrofitHelper {
        RetrofitConfig.Builder().setBaseUrl(url)
        return this@RetrofitHelper
    }

    fun client(client: OkHttpClient): RetrofitHelper {
        RetrofitConfig.Builder().setOkHttpClient(client)
        return this@RetrofitHelper
    }

    fun converter(factory: Converter.Factory): RetrofitHelper {
        RetrofitConfig.Builder().setConverterFactory(factory)
        return this@RetrofitHelper
    }

    fun callAdapter(factory: CallAdapter.Factory): RetrofitHelper {
        RetrofitConfig.Builder().setCallAdapterFactory(factory)
        return this@RetrofitHelper
    }

    private fun createRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitConfig.getBaseUrl())
            .client(RetrofitConfig.getOkHttpClient())
            .addConverterFactory(RetrofitConfig.getConverterFactory())
            .addCallAdapterFactory(RetrofitConfig.getCallAdapterFactory())
            .build()
        mRetrofit = retrofit
        return retrofit
    }

    fun <T> buildApi(api: Class<T>, retrofit: Retrofit = retrofit()): T {
        return retrofit.create(api)
    }

    fun <T : Any> buildSingle(single: Single<RetrofitResponse<T?>>): Single<RetrofitResponse<T?>> {
        return single
            .map(ServerResultFunction())
            .onErrorResumeNext(HttpResultFunction())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}