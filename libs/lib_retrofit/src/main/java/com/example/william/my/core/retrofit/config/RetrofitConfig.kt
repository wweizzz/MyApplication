package com.example.william.my.core.retrofit.config

import com.example.william.my.core.okhttp.helper.OkHttpHelper
import com.example.william.my.core.retrofit.converter.RetrofitConverterFactory
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object RetrofitConfig {

    var baseUrl: String = "http://host/"

    var errorCode = "errorCode"

    var errorMsg = "errorMsg"

    var client = OkHttpHelper.client()

    var converter: Converter.Factory = RetrofitConverterFactory.create()

    var callAdapter: CallAdapter.Factory = RxJava3CallAdapterFactory.create()
}