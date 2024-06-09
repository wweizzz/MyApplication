package com.example.william.my.module.libraries.hilt

import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.hilt.qualifier.LibrariesInit
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LibrariesModule {

    @LibrariesInit
    @Binds
    abstract fun init(appInit: LibrariesInitImpl): IAppInit
}