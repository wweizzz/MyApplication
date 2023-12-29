package com.example.william.my.basic.basic_repository.api

import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.bean.UserBean
import com.example.william.my.basic.basic_repository.bean.ArticleListData
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi2 {

    @POST(Constants.Url_Login)
    suspend fun loginSuspend(
        @Query(Constants.Key_Username) username: String,
        @Query(Constants.Key_Password) password: String
    ): RetrofitResponse<UserBean>

    @GET(Constants.Url_Article_List)
    fun getArticleSingle(
        @Path("page") page: Int,
    ): Single<RetrofitResponse<ArticleListData>>

    // 提供挂起功能的网络请求接口
    // Interface that provides a way to make network requests with suspend functions
    @GET(Constants.Url_Article_List)
    suspend fun getArticleSuspend(
        @Path("page") page: Int,
    ): RetrofitResponse<ArticleListData>
}