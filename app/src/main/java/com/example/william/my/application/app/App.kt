package com.example.william.my.application.app

import com.example.william.my.basic.basic_module.app.ModuleApp
import com.example.william.my.lib.app.BaseApp
import com.example.william.my.lib.eventbus.EventBusHelper
import com.example.william.my.lib.utils.CrashUtils
import com.example.william.my.lib.utils.FileSDCardUtil
import com.example.william.my.lib.utils.Utils

/**
 * gradlew :app:dependencies 查询app依赖
 */
//@HiltAndroidApp
class App : BaseApp() {
    override fun onCreate() {
        super.onCreate()

        initCrash()

        initEventBus()
    }

    override fun initApp() {
        registerAppInit(ModuleApp::class.java)

//        registerAppInit(FlutterApp::class.java)

//        registerAppInit(DemoApp::class.java)
//        registerAppInit(LibrariesApp::class.java)

//        registerAppInit(OpensourceApp::class.java)

//        registerAppInit(NetworkApp::class.java)
//        registerAppInit(SampleApp::class.java)
//        registerAppInit(ArchApp::class.java)

//        registerAppInit(DatabaseApp::class.java)
    }

    private fun initCrash() {
        CrashUtils.init(FileSDCardUtil.getCacheDirPath(), object : CrashUtils.OnCrashListener {
            override fun onCrash(crashInfo: CrashUtils.CrashInfo) {
                Utils.e("CrashUtils", crashInfo.throwable.message.toString())
            }
        })
    }

    private fun initEventBus() {
        EventBusHelper
            //.addIndex(MyLibraryEventBusIndex())
            //.addIndex(MyLibrariesEventBusIndex())
            .init()
    }
}