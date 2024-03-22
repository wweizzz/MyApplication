package com.example.william.my.core.okhttp.compat

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object CompatHttpsSSL {

    fun ignoreSSLForOkHttp(builder: OkHttpClient.Builder) {
        builder.hostnameVerifier(ignoreHostnameVerifier)
        builder.sslSocketFactory(ignoreSSLSocketFactory, trustManager)
    }

    fun ignoreSSLForHttpsURLConnection() {
        HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier)
        HttpsURLConnection.setDefaultSSLSocketFactory(ignoreSSLSocketFactory)
    }

    /**
     * 获取忽略证书的HostnameVerifier
     * 与[.getIgnoreSSLSocketFactory]同时配置使用
     */
    private val ignoreHostnameVerifier: HostnameVerifier
        get() = HostnameVerifier { _, _ ->
            true
        }

    /**
     * 获取忽略证书的SSLSocketFactory
     * 与[.getIgnoreHostnameVerifier]同时配置使用
     */
    private val ignoreSSLSocketFactory: SSLSocketFactory
        get() = try {
            val sslContext = SSLContext.getInstance("TLS")
            val trustManager = trustManager
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    @get:SuppressLint("CustomX509TrustManager")
    private val trustManager: X509TrustManager
        get() = object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
}