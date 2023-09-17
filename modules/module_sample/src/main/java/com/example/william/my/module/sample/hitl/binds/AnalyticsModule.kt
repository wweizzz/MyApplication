package com.example.william.my.module.sample.hitl.binds

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

/**
 * 使用 @Binds 注入接口实例
 */
interface AnalyticsService {
    fun analyticsMethods()
}

class AnalyticsServiceImpl @Inject constructor() : AnalyticsService {
    override fun analyticsMethods() {

    }
}

@Module // Hilt 模块
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: AnalyticsServiceImpl
    ): AnalyticsService
}