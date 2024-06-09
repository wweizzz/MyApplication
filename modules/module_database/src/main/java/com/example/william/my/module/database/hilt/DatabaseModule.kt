package com.example.william.my.module.database.hilt

import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.hilt.qualifier.DatabaseInit
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @DatabaseInit
    @Binds
    abstract fun init(appInit: DatabaseInitImpl): IAppInit
}