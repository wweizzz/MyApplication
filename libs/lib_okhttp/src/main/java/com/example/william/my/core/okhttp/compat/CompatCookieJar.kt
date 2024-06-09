package com.example.william.my.core.okhttp.compat

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient

object CompatCookieJar {

    fun cookieJar(builder: OkHttpClient.Builder) {
        builder.cookieJar(buildCookieJar())
    }

    //fun cookieJar2(builder: OkHttpClient.Builder) {
    //    OkHttpConfig.app?.let { app ->
    //        builder.addInterceptor(InterceptorCookie(app))
    //    } ?: {
    //        HttpLog.error("context == null. cookie 缓存未启用.")
    //    }
    //}

    private fun buildCookieJar(): CookieJar {

        return object : CookieJar {

            private val cookieStore: MutableMap<String, List<Cookie>> = mutableMapOf()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url.host] = cookies
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url.host]
                return cookies ?: ArrayList()
            }
        }
    }
}