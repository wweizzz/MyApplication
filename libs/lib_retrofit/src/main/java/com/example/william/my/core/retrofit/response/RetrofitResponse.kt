package com.example.william.my.core.retrofit.response

import com.example.william.my.core.retrofit.base.BaseBean
import com.example.william.my.core.retrofit.status.State
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * RetrofitResponse
 */
class RetrofitResponse<T> : BaseBean {
    /**
     * 状态码
     */
    @SerializedName("errorCode")
    val code: Int

    /**
     * 描述信息
     */
    @SerializedName("errorMsg")
    var message: String = ""

    /**
     * 数据对象
     */
    @SerializedName("data")
    var data: T? = null
        private set

    constructor(code: Int) {
        this.code = code
    }

    private constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    private constructor(code: Int, data: T) {
        this.code = code
        this.data = data
    }

    /**
     * 是否成功(这里约定0)
     */
    val isSuccess: Boolean
        get() = code == State.SUCCESS

    fun string(): String? {
        return Gson().toJson(this)
    }

    fun T.string(): String? {
        return Gson().toJson(this)
    }

    companion object {
        fun <T> loading(): RetrofitResponse<T> {
            return RetrofitResponse(State.LOADING)
        }

        @JvmStatic
        fun <T> success(data: T): RetrofitResponse<T> {
            return RetrofitResponse(State.SUCCESS, data)
        }

        fun <T> error(message: String): RetrofitResponse<T> {
            return RetrofitResponse(State.ERROR, message)
        }
    }
}