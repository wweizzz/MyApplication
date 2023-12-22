package com.example.william.my.lib.app

import android.app.Application
import android.content.res.Configuration
import android.os.Process
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.lib.BuildConfig
import com.example.william.my.lib.utils.ActivityManagerUtil
import java.util.concurrent.Executors

/**
 * Application
 * Setting -> Build,Execution, Deployment -> Debugger -> HotSwap -> Enable hot-swap agent for Groovy
 */
abstract class BaseApp : Application() {


    private val appInitList: MutableList<BaseAppInit> = ArrayList()
    private val classInitList: MutableList<Class<out BaseAppInit>> = ArrayList()

    override fun onCreate() {
        super.onCreate()
        app = this

        initApp()

        initARouter()

        initModuleApp()

        modulesApplicationInit()

        modulesApplicationInitAsync()
    }

    private fun initARouter() {
        ActivityManagerUtil.register(app)
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(app) // 尽可能早，推荐在Application中初始化
    }

    private fun initModuleApp() {
        for (classInit in classInitList) {
            try {
                val appInit = classInit.newInstance()
                appInit.setApplication(this)
                appInitList.add(appInit)
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun modulesApplicationInit() {
        //Module类的APP初始化
        for (app in appInitList) {
            app.init()
        }
    }

    private fun modulesApplicationInitAsync() {
        Executors.newSingleThreadExecutor().execute {
            //设置线程的优先级，不与主线程抢资源
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND)
            //Module类的APP初始化异步
            for (app in appInitList) {
                app.initAsync()
            }
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        for (app in appInitList) {
            app.onLowMemory()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        for (app in appInitList) {
            app.onConfigurationChanged(newConfig)
        }
    }

    protected abstract fun initApp()

    protected fun registerAppInit(classInit: Class<out BaseAppInit>) {
        classInitList.add(classInit)
    }

    companion object {
        lateinit var app: BaseApp
    }
}