package com.example.william.my.core.ktor.settings

import android.annotation.SuppressLint
import java.io.FileInputStream
import java.security.KeyStore
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SslSettings {

    fun getHostnameVerifier(hostName: String = ""): HostnameVerifier {
        return HostnameVerifier { s, sslSession ->
            if (hostName == s) {
                true
            } else {
                HttpsURLConnection.getDefaultHostnameVerifier().verify(s, sslSession)
            }
        }
    }

    private fun getKeyStore(): KeyStore {
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(FileInputStream("keystore.jks"), "password".toCharArray())
        return keyStore
    }

    private fun getTrustManagerFactory(): TrustManagerFactory? {
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(getKeyStore())
        return trustManagerFactory
    }

    fun getSSLSocketFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManagerFactory()?.trustManagers, null)
        return sslContext.socketFactory
    }

    fun getTrustManager(): X509TrustManager {
        return getTrustManagerFactory()?.trustManagers?.first { it is X509TrustManager } as X509TrustManager
    }

    /**
     * 获取忽略证书的HostnameVerifier
     * 与[.getIgnoreSSLSocketFactory]同时配置使用
     */
    val ignoreHostnameVerifier: HostnameVerifier
        get() = HostnameVerifier { _, _ ->
            true
        }

    /**
     * 获取忽略证书的SSLSocketFactory
     * 与[.getIgnoreHostnameVerifier]同时配置使用
     */
    val ignoreSSLSocketFactory: SSLSocketFactory
        get() = try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf<TrustManager>(ignoreTrustManager), null)
            sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    @get:SuppressLint("CustomX509TrustManager")
    val ignoreTrustManager: X509TrustManager
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