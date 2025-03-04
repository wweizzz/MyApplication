package com.example.william.my.core.ktor.settings

import java.io.FileInputStream
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SslSettings {

    private fun getKeyStore(): KeyStore {
        val keyStoreFile = FileInputStream("keystore.jks")
        val keyStorePassword = "foobar".toCharArray()
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(keyStoreFile, keyStorePassword)
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
}