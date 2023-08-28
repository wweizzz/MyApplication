package com.example.william.my.core.okhttp.base

object Header {
    /**
     * 添加以这个为名的Header可以让这个Request使用另一个BaseUrl
     */
    const val RETROFIT_URL_REDIRECT = "Retrofit-Url-Redirect"

    /**
     * 添加以这个为名的Header可以让这个Request支持缓存（有网联网获取，无网读取缓存）
     * 如//@Headers({Header.CACHE_ALIVE_SECOND + ":" + 10})
     */
    const val RETROFIT_CACHE_ALIVE_SECOND = "Retrofit-Cache-Alive-Second"
}