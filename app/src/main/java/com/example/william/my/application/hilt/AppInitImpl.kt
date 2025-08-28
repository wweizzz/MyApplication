package com.example.william.my.application.hilt

import android.app.Application
import com.example.william.my.basic.basic_lib.MyLibEventBusIndex
import com.example.william.my.lib.eventbus.EventBusHelper
import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.utils.CrashUtils
import com.example.william.my.lib.utils.FileSDCardUtil
import com.example.william.my.lib.utils.Utils
import com.example.william.my.modules.module_libraries.MyLibrariesEventBusIndex
import javax.inject.Inject

class AppInitImpl @Inject constructor() : IAppInit {

    private lateinit var mApp: Application

    override fun init(app: Application) {
        this.mApp = app

        initCrash(app)

        initEventBus()
    }

    override fun getApp(): Application {
        return mApp
    }

    private fun initCrash(app: Application) {
        CrashUtils.init(
            app, FileSDCardUtil.getCacheDirPath(app.applicationContext),
            object : CrashUtils.OnCrashListener {
                override fun onCrash(crashInfo: CrashUtils.CrashInfo) {
                    Utils.e("CrashUtils", crashInfo.throwable.message.toString())
                }
            })
    }

    private fun initEventBus() {
        EventBusHelper.addIndex(MyLibEventBusIndex()).addIndex(MyLibrariesEventBusIndex()).init()
    }
}