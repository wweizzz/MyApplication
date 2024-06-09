package com.example.william.my.core.retrofit.api;

import com.example.william.my.core.retrofit.response.RetrofitResponse;
import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Api接口
 * retrofit不支持二次泛型 JsonElement
 * Method return type must not include a type variable or wildcard:Observable<T>
 * You cannot Type information needs to be fully known at runtime in order for deserialization to work.
 */
public interface Api {

    /**
     * GET 请求
     *
     * @param url       api接口url
     * @param header    请求头map
     * @param parameter 请求参数map
     * @return
     */
    @GET
    Single<RetrofitResponse<JsonElement>> get(@Url String url, @HeaderMap Map<String, Object> header, @QueryMap Map<String, Object> parameter);

    /**
     * POST 请求
     *
     * @param url       api接口url
     * @param header    请求头map
     * @param parameter 请求参数map
     * @return
     */
    @FormUrlEncoded
    @POST
    Single<RetrofitResponse<JsonElement>> post(@Url String url, @HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> parameter);

    /**
     * POST 请求
     *
     * @param url         api接口url
     * @param header      请求头map
     * @param requestBody 用于String/JSON格式数据
     * @return
     */
    @POST
    Single<RetrofitResponse<JsonElement>> post(@Url String url, @HeaderMap Map<String, Object> header, @Body RequestBody requestBody);

    /**
     * DELETE 请求
     *
     * @param url       api接口url
     * @param header    请求头map
     * @param parameter 请求参数map
     * @return
     */
    @DELETE
    Single<RetrofitResponse<JsonElement>> delete(@Url String url, @HeaderMap Map<String, Object> header, @QueryMap Map<String, Object> parameter);

    /**
     * PUT 请求
     *
     * @param url       api接口url
     * @param header    请求头map
     * @param parameter 请求参数map
     * @return
     */
    @FormUrlEncoded
    @PUT
    Single<RetrofitResponse<JsonElement>> put(@Url String url, @HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> parameter);

    /**
     * 文件上传
     * 文件上传注解 multipart/form-data
     *
     * @param url    api接口url
     * @param header 请求头map
     * @param part   文件参数
     * @return
     */
    @Multipart
    @POST
    Single<RetrofitResponse<JsonElement>> uploadFile(@Url String url, @HeaderMap Map<String, Object> header, @Part MultipartBody.Part part);

    /**
     * 多文件上传
     * 文件上传注解 multipart/form-data
     *
     * @param url    api接口url
     * @param header 请求头map
     * @param map    文件参数
     * @return
     */
    @Multipart
    @POST
    Single<RetrofitResponse<JsonElement>> uploadFiles(@Url String url, @HeaderMap Map<String, Object> header, @PartMap Map<String, RequestBody> map);


    /**
     * 断点续传下载
     * Streaming 防止内容写入内存, 大文件通过此注解避免OOM
     *
     * @param range 断点下载范围 bytes= start - end
     * @param url   下载地址
     * @return
     */
    @Streaming
    @GET
    Single<ResponseBody> downloadFile(@Header("RANGE") String range, @Url String url);
}
