package com.example.william.my.application.app

import com.example.william.my.basic.basic_lib.MyLibEventBusIndex
import com.example.william.my.lib.app.BaseApp
import com.example.william.my.lib.eventbus.EventBusHelper
import com.example.william.my.lib.utils.CrashUtils
import com.example.william.my.lib.utils.FileSDCardUtil
import com.example.william.my.lib.utils.Utils
import com.example.william.my.module.arch.app.ArchApp
import com.example.william.my.module.database.app.DatabaseApp
import com.example.william.my.module.flutter.app.FlutterApp
import com.example.william.my.module.libraries.app.LibrariesApp
import com.example.william.my.module.opensource.app.OpenApp
import com.example.william.my.modules.module_libraries.MyLibrariesEventBusIndex

/**
 * gradlew :app:dependencies 查询app依赖
 */
class App : BaseApp() {

    override fun onCreate() {
        super.onCreate()

        initCrash()

        initEventBus()
    }

    override fun initApp() {
        registerAppInit(LibrariesApp::class.java) // FlowEventBus
        registerAppInit(DatabaseApp::class.java) // Greendao, ObjectBox

        registerAppInit(OpenApp::class.java) // Shiply
        registerAppInit(ArchApp::class.java) // Mavericks

        registerAppInit(FlutterApp::class.java) // FlutterEngine
    }

    private fun initCrash() {
        CrashUtils.init(
            this, FileSDCardUtil.getCacheDirPath(this.applicationContext),
            object : CrashUtils.OnCrashListener {
                override fun onCrash(crashInfo: CrashUtils.CrashInfo) {
                    Utils.e("CrashUtils", crashInfo.throwable.message.toString())
                }
            })
    }

    private fun initEventBus() {
        EventBusHelper
            .addIndex(MyLibEventBusIndex())
            .addIndex(MyLibrariesEventBusIndex())
            .init()
    }
}