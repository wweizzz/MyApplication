package com.example.william.my.module.flutter.hilt

import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.hilt.qualifier.FlutterInit
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FlutterModule {

    @FlutterInit
    @Binds
    abstract fun init(appInit: FlutterInitImpl): IAppInit
}