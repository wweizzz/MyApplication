package com.example.william.my.core.retrofit.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.william.my.core.retrofit.callback.RetrofitLiveDataCallback
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

    fun baseUrl(basUrl: String): RetrofitHelper {
        RetrofitConfig.baseUrl = basUrl
        return this@RetrofitHelper
    }

    fun client(client: OkHttpClient): RetrofitHelper {
        RetrofitConfig.client = client
        return this@RetrofitHelper
    }

    fun converter(converter: Converter.Factory): RetrofitHelper {
        RetrofitConfig.converter = converter
        return this@RetrofitHelper
    }

    fun callAdapter(callAdapter: CallAdapter.Factory): RetrofitHelper {
        RetrofitConfig.callAdapter = callAdapter
        return this@RetrofitHelper
    }

    private fun createRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(RetrofitConfig.baseUrl)
            .client(RetrofitConfig.client)
            .addConverterFactory(RetrofitConfig.converter)
            .addCallAdapterFactory(RetrofitConfig.callAdapter)
            .build()
        mRetrofit = retrofit
        return retrofit
    }

    fun <T> buildApi(api: Class<T>, retrofit: Retrofit = retrofit()): T {
        return retrofit.create(api)
    }

    fun getBaseUrl(url: String): String {
        var tempUrl = url
        var head = ""
        var index = tempUrl.indexOf("://")
        if (index != -1) {
            head = tempUrl.substring(0, index + 3)
            tempUrl = tempUrl.substring(index + 3)
        }
        index = tempUrl.indexOf("/")
        if (index != -1) {
            tempUrl = tempUrl.substring(0, index + 1)
        }
        return head + tempUrl
    }

    fun <T : Any> buildSingle(single: Single<RetrofitResponse<T>>): Single<RetrofitResponse<T>> {
        return single
            .map(ServerResultFunction())
            .onErrorResumeNext(HttpResultFunction())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T : Any> buildLiveData(single: Single<RetrofitResponse<T>>): LiveData<RetrofitResponse<T>> {
        val liveData: MutableLiveData<RetrofitResponse<T>> = MutableLiveData()
        buildSingle(single)
            .subscribe(object : RetrofitLiveDataCallback<T>() {
                override fun onPostValue(value: RetrofitResponse<T>) {
                    liveData.postValue(value)
                }
            })
        return liveData
    }

    fun <T : Any> buildLiveData(
        single: Single<RetrofitResponse<T>>, postValue: (RetrofitResponse<T>) -> Unit
    ) {
        buildSingle(single)
            .subscribe(object : RetrofitLiveDataCallback<T>() {
                override fun onPostValue(value: RetrofitResponse<T>) {
                    postValue(value)
                }
            })
    }
}