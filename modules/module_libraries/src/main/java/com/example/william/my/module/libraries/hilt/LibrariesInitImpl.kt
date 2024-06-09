package com.example.william.my.module.libraries.hilt

import android.app.Application
import com.example.william.my.core.eventbus.flow.FlowEventBus
import com.example.william.my.lib.hilt.interfaces.IAppInit
import javax.inject.Inject

class LibrariesInitImpl @Inject constructor() : IAppInit {

    private lateinit var mApp: Application

    override fun init(app: Application) {
        this.mApp = app

        initFlowEventBus(app)
    }

    override fun getApp(): Application {
        return mApp
    }

    private fun initFlowEventBus(app: Application) {
        FlowEventBus.init(app)
    }
}