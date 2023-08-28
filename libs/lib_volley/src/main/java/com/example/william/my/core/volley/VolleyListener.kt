package com.example.william.my.core.volley

import com.android.volley.Response

abstract class VolleyListener<T> : Response.Listener<T>, Response.ErrorListener {
    /**
     * 创建请求的事件监听
     */
    val mListener =
        Response.Listener<T> { t ->
            onResponse(t)
        }

    /**
     * 创建请求失败的事件监听
     */
    val mErrorListener =
        Response.ErrorListener { error ->
            onErrorResponse(error)
        }
}