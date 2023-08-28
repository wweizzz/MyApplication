package com.example.william.my.core.volley

import com.android.volley.Request.Method

class VolleyBuilder<T> {

    private lateinit var url: String
    private lateinit var clazz: Class<T>
    private var method: Int = Method.GET
    private var header: MutableMap<String, String> = mutableMapOf()
    private var parameter: MutableMap<String, String> = mutableMapOf()

    fun getUrl(): String {
        return url
    }

    fun getClazz(): Class<T> {
        return clazz
    }

    fun getMethod(): Int {
        return method
    }

    fun getHeader(): MutableMap<String, String> {
        return header
    }

    fun getParam(): MutableMap<String, String> {
        return parameter
    }

    fun url(api: String): VolleyBuilder<T> {
        this.url = api
        return this
    }

    fun clazz(clazz: Class<T>): VolleyBuilder<T> {
        this.clazz = clazz
        return this
    }

    fun get(): VolleyBuilder<T> {
        method = Method.GET
        return this
    }

    fun post(): VolleyBuilder<T> {
        method = Method.POST
        return this
    }

    fun delete(): VolleyBuilder<T> {
        method = Method.DELETE
        return this
    }

    fun put(): VolleyBuilder<T> {
        method = Method.PUT
        return this
    }

    fun addHeader(key: String, value: String): VolleyBuilder<T> {
        header[key] = value
        return this
    }

    fun addParam(key: String, value: String): VolleyBuilder<T> {
        parameter[key] = value
        return this
    }

    fun build(): VolleyHelper<T> {
        return VolleyHelper(this)
    }
}