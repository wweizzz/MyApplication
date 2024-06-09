package com.example.william.my.core.volley

import android.content.Context

class VolleyHelper<T>(private val builder: VolleyBuilder<T>) {

    fun enqueue(content: Context, responseListener: VolleyListener<T>) {
        val request = GsonRequest(
            builder.getMethod(),
            builder.getUrl(),
            builder.getClazz(),
            builder.getHeader(),
            builder.getParam(),
            responseListener.mListener,
            responseListener.mErrorListener
        )
        request.tag = builder.getUrl()
        VolleySingleton.getInstance(content).addToRequestQueue(request)
    }

    fun cancel(content: Context, tag: String) {
        VolleySingleton.getInstance(content).cancel(tag)
    }

    companion object {
        fun <T> builder(): VolleyBuilder<T> {
            return VolleyBuilder()
        }
    }
}