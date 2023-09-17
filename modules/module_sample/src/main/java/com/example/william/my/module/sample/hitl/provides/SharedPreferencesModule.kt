package com.example.william.my.module.sample.hitl.provides

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

/**
 * 使用 @Provides 注入实例
 */
@Module // Hilt 模块
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    /**
     * @ApplicationContext 为 Hilt 中的预定义限定符
     */
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("pref_name", Context.MODE_PRIVATE)
    }
}