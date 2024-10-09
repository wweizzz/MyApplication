package com.example.william.my.lib.hilt.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseInit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppInit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LibrariesInit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DatabaseInit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenInit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ArchInit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FlutterInit