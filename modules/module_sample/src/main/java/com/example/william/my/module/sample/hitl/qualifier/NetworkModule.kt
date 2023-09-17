package com.example.william.my.module.sample.hitl.qualifier

import com.example.william.my.library.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier

/**
 * 为同一类型提供多个绑定
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @AuthInterceptorOkHttpClient
    @Provides
    fun provideAuthInterceptorOkHttpClient(): Unit {
        Utils.e("Hilt", "provideAuthInterceptorOkHttpClient")
    }

    @OtherInterceptorOkHttpClient
    @Provides
    fun provideOtherInterceptorOkHttpClient() {
        Utils.e("Hilt", "provideOtherInterceptorOkHttpClient")
    }
}

// As a dependency of a constructor-injected class.
class ExampleServiceImpl @Inject constructor(
    @AuthInterceptorOkHttpClient private val okHttpClient1: Unit,
    @OtherInterceptorOkHttpClient private val okHttpClient2: Unit
)
