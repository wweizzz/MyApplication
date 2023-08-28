package com.example.william.my.application.app

import com.example.william.my.basic.basic_library.MyLibraryEventBusIndex
import com.example.william.my.basic.basic_module.app.ModuleApp
import com.example.william.my.basic.basic_repository.app.RepositoryApp
import com.example.william.my.library.app.BaseApp
import com.example.william.my.library.eventbus.EventBusHelper
import com.example.william.my.library.utils.CrashUtils
import com.example.william.my.library.utils.FileSDCardUtil
import com.example.william.my.library.utils.Utils
import com.example.william.my.module.arch.app.ArchApp
import com.example.william.my.module.demo.app.DemoApp
import com.example.william.my.module.flutter.app.FlutterApp
import com.example.william.my.module.libraries.app.LibrariesApp
import com.example.william.my.module.network.app.NetworkApp
import com.example.william.my.module.opensource.app.OpensourceApp
import com.example.william.my.module.sample.app.SampleApp
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
        registerAppInit(ModuleApp::class.java)
        registerAppInit(RepositoryApp::class.java)
        registerAppInit(ArchApp::class.java)
        registerAppInit(DemoApp::class.java)
        registerAppInit(FlutterApp::class.java)
        registerAppInit(LibrariesApp::class.java)
        registerAppInit(NetworkApp::class.java)
        registerAppInit(OpensourceApp::class.java)
        registerAppInit(SampleApp::class.java)
    }

    private fun initCrash() {
        CrashUtils.init(FileSDCardUtil.getCacheDirPath(), object : CrashUtils.OnCrashListener {
            override fun onCrash(crashInfo: CrashUtils.CrashInfo) {
                Utils.e("CrashUtils:" + crashInfo.throwable.message)
            }
        })
    }

    private fun initEventBus() {
        EventBusHelper
            .addIndex(MyLibraryEventBusIndex())
            .addIndex(MyLibrariesEventBusIndex())
            .init()
    }
}