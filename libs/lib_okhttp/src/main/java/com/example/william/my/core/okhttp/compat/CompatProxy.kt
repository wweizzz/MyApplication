package com.example.william.my.core.okhttp.compat

import okhttp3.OkHttpClient
import java.net.Proxy

object CompatProxy {

    /**
     * 设置连接使用的HTTP代理。
     * 该方法优先于proxySelector，默认代理为空，完全禁用代理使用NO_PROXY
     */
    fun noProxy(builder: OkHttpClient.Builder) {
        builder.proxy(Proxy.NO_PROXY)
    }
}