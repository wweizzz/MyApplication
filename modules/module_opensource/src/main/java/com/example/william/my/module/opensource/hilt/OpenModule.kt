package com.example.william.my.module.opensource.hilt

import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.hilt.qualifier.OpenInit
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class OpenModule {

    @OpenInit
    @Binds
    abstract fun init(appInit: OpenInitImpl): IAppInit
}