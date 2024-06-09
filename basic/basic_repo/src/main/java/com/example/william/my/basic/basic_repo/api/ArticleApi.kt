package com.example.william.my.basic.basic_repo.api

import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_repo.bean.ArticleData
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApi {

    @GET(Constants.Url_Article_List)
    fun getArticleSingle(
        @Path("page") page: Int,
    ): Single<RetrofitResponse<ArticleData>>

    // 提供挂起功能的网络请求接口
    // Interface that provides a way to make network requests with suspend functions
    @GET(Constants.Url_Article_List)
    suspend fun getArticleSuspend(
        @Path("page") page: Int,
    ): RetrofitResponse<ArticleData>
}