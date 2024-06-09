package com.example.william.my.module.arch.hilt

import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.hilt.qualifier.ArchInit
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArchModule {

    @ArchInit
    @Binds
    abstract fun init(appInit: ArchInitImpl): IAppInit
}