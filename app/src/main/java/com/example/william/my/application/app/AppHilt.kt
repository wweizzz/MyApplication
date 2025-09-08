package com.example.william.my.application.app

import android.app.Application
import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.hilt.qualifier.AppInit
import com.example.william.my.lib.hilt.qualifier.ArchInit
import com.example.william.my.lib.hilt.qualifier.BaseInit
import com.example.william.my.lib.hilt.qualifier.DatabaseInit
import com.example.william.my.lib.hilt.qualifier.FlutterInit
import com.example.william.my.lib.hilt.qualifier.LibrariesInit
import com.example.william.my.lib.hilt.qualifier.OpenInit
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppHilt : Application() {

    @BaseInit
    @Inject
    lateinit var baseInit: IAppInit

    @AppInit
    @Inject
    lateinit var appInit: IAppInit

    @LibrariesInit
    @Inject
    lateinit var librariesInit: IAppInit

    @DatabaseInit
    @Inject
    lateinit var databaseInit: IAppInit

    @OpenInit
    @Inject
    lateinit var openInit: IAppInit

    @ArchInit
    @Inject
    lateinit var archInit: IAppInit

    @FlutterInit
    @Inject
    lateinit var flutterInit: IAppInit


    override fun onCreate() {
        super.onCreate()

        baseInit.init(this)
        appInit.init(this)

        librariesInit.init(this) // FlowEventBus
        databaseInit.init(this) // Greendao, ObjectBox

        openInit.init(this) //
        archInit.init(this) // Mavericks

        flutterInit.init(this) // FlutterEngine
    }
}