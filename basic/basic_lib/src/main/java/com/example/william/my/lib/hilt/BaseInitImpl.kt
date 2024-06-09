package com.example.william.my.lib.hilt

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.lib.BuildConfig
import com.example.william.my.lib.hilt.interfaces.IAppInit
import com.example.william.my.lib.utils.ActivityManagerUtil
import javax.inject.Inject

class BaseInitImpl @Inject constructor() : IAppInit {

    private lateinit var mApp: Application

    override fun init(app: Application) {
        this.mApp = app

        initARouter(app)

        initManagerUtil(app)
    }

    override fun getApp(): Application {
        return mApp
    }

    private fun initARouter(app: Application) {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(app) // 尽可能早，推荐在Application中初始化
    }

    private fun initManagerUtil(app: Application) {
        ActivityManagerUtil.register(app)
    }
}