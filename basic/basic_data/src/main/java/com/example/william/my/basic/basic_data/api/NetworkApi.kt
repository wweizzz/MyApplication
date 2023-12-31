package com.example.william.my.basic.basic_data.api

import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_data.bean.UserBean
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface NetworkApi {

    /**
     * RetrofitActivity
     * RetrofitHelperActivity
     */
    @POST(Constants.Url_Login)
    fun loginCall(
        @Query(Constants.Key_Username) username: String,
        @Query(Constants.Key_Password) password: String
    ): Call<ResponseBody>

    /**
     * RetrofitRxJavaActivity
     * RetrofitRxJavaHelperActivity
     */
    @POST(Constants.Url_Login)
    fun loginSingle(
        @Query(Constants.Key_Username) username: String,
        @Query(Constants.Key_Password) password: String
    ): Single<RetrofitResponse<UserBean>>

    /**
     * RetrofitDownloadActivity
     */
    @GET
    fun downloadFile(@Url url: String): Call<ResponseBody>

    // =============================================================================================

    @POST(Constants.Url_Login)
    suspend fun loginSuspend(
        @Query(Constants.Key_Username) username: String,
        @Query(Constants.Key_Password) password: String
    ): RetrofitResponse<UserBean>

    //@GET(Constants.Url_Article_List)
    //fun getArticleSingle(
    //    @Path("page") page: Int,
    //): Single<RetrofitResponse<ArticleListBean>>

    // 提供挂起功能的网络请求接口
    // Interface that provides a way to make network requests with suspend functions
    //@GET(Constants.Url_Article_List)
    //suspend fun getArticleSuspend(
    //    @Path("page") page: Int,
    //): RetrofitResponse<ArticleListBean>
}