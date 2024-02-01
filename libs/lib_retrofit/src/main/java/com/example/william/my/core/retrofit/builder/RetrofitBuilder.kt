package com.example.william.my.core.retrofit.builder

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.william.my.core.okhttp.body.RequestProgressBody
import com.example.william.my.core.okhttp.listener.RequestProgressListener
import com.example.william.my.core.okhttp.media.MediaType
import com.example.william.my.core.retrofit.RxRetrofit
import com.example.william.my.core.retrofit.method.Method
import com.example.william.my.core.retrofit.response.RetrofitResponse
import com.trello.lifecycle4.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle4.LifecycleProvider
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File

class RetrofitBuilder<T> {

    private lateinit var api: String
    private var method: Method = Method.GET
    private var header: MutableMap<String, Any> = mutableMapOf()
    private var parameter: MutableMap<String, Any> = mutableMapOf()

    private var lifecycle: LifecycleProvider<Lifecycle.Event>? = null

    fun getApi(): String {
        return api
    }

    fun getMethod(): Method {
        return method
    }

    fun getHeader(): MutableMap<String, Any> {
        return header
    }

    fun getParam(): MutableMap<String, Any> {
        return parameter
    }

    fun getLifecycle(): LifecycleProvider<Lifecycle.Event>? {
        return lifecycle
    }

    fun hasBody(): Boolean {
        return hasBody
    }

    fun isJson(): Boolean {
        return isJson
    }

    fun getBodyString(): String? {
        return bodyString
    }

    fun getBodyForm(): MultipartBody.Builder? {
        return bodyFormData
    }

    fun api(api: String): RetrofitBuilder<T> {
        this.api = api
        return this
    }

    fun get(): RetrofitBuilder<T> {
        method = Method.GET
        return this
    }

    fun post(): RetrofitBuilder<T> {
        method = Method.POST
        return this
    }

    fun delete(): RetrofitBuilder<T> {
        method = Method.DELETE
        return this
    }

    fun put(): RetrofitBuilder<T> {
        method = Method.PUT
        return this
    }

    fun addHeader(key: String, value: Any): RetrofitBuilder<T> {
        header[key] = value
        return this
    }

    fun addParam(key: String, value: Any): RetrofitBuilder<T> {
        parameter[key] = value
        return this
    }

    private var hasBody = false

    private var isJson = true
    private var bodyString: String? = null
    private var bodyFormData: MultipartBody.Builder? = null

    fun addString(bodyString: String?): RetrofitBuilder<T> {
        this.hasBody = true
        this.isJson = false
        this.bodyString = bodyString
        return this
    }

    fun addJsonString(bodyString: String?): RetrofitBuilder<T> {
        this.hasBody = true
        this.isJson = true
        this.bodyString = bodyString
        return this
    }

    fun addJsonObject(jsonObject: JSONObject): RetrofitBuilder<T> {
        this.hasBody = true
        this.isJson = true
        this.bodyString = jsonObject.toString()
        return this
    }

    fun addFormData(key: String, value: String): RetrofitBuilder<T> {
        this.hasBody = true
        if (this.bodyFormData == null) {
            this.bodyFormData = MultipartBody.Builder().setType(MultipartBody.FORM)
        }
        this.bodyFormData?.addFormDataPart(key, value)
        return this
    }

    fun addFile(key: String, file: File): RetrofitBuilder<T> {
        this.hasBody = true
        if (this.bodyFormData == null) {
            this.bodyFormData = MultipartBody.Builder().setType(MultipartBody.FORM)
        }
        this.bodyFormData?.addFormDataPart(
            key, file.name, file.asRequestBody(MediaType.MEDIA_TYPE_MULTIPART)
        )
        return this
    }

    fun addFile(key: String, file: File, listener: RequestProgressListener?): RetrofitBuilder<T> {
        this.hasBody = true
        if (this.bodyFormData == null) {
            this.bodyFormData = MultipartBody.Builder().setType(MultipartBody.FORM)
        }
        this.bodyFormData?.addFormDataPart(
            key,
            file.name,
            RequestProgressBody(file.asRequestBody(MediaType.MEDIA_TYPE_MULTIPART), listener)
        )
        return this
    }

    fun setProvider(owner: LifecycleOwner): RetrofitBuilder<T> {
        lifecycle = AndroidLifecycle.createLifecycleProvider(owner)
        return this
    }

    fun buildSingle(): Single<RetrofitResponse<T>> {
        return RxRetrofit(this).createResponse()
    }
}