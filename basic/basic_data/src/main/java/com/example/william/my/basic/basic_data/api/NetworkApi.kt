package com.example.william.my.basic.basic_data.api

import com.example.william.my.basic.basic_data.bean.Login
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.core.retrofit.response.RetrofitResponse
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    ): Single<RetrofitResponse<Login?>>

    /**
     * RetrofitDownloadActivity
     */
    @GET
    fun downloadFile(@Url url: String): Call<ResponseBody>

    /**
     * RetrofitUploadActivity
     */
    @POST
    fun uploadFile(@Url url: String, @Body body: MultipartBody): Call<ResponseBody>

    /**
     * RetrofitUploadActivity
     */
    @Multipart
    @POST
    fun uploadFile(@Url url: String, @Part part: MultipartBody.Part): Call<ResponseBody>

    /**
     * RetrofitUploadActivity
     */
    @Multipart
    @POST
    fun uploadFiles(@Url url: String, @Part parts: List<MultipartBody.Part>): Call<ResponseBody>

    // =============================================================================================

    /**
     * CoroutinesRepository
     * FlowRepository
     */
    @POST(Constants.Url_Login)
    suspend fun loginSuspend(
        @Query(Constants.Key_Username) username: String,
        @Query(Constants.Key_Password) password: String
    ): RetrofitResponse<Login?>
}