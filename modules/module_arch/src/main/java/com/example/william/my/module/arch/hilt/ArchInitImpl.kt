package com.example.william.my.module.arch.hilt

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.example.william.my.lib.hilt.interfaces.IAppInit
import javax.inject.Inject

class ArchInitImpl @Inject constructor() : IAppInit {

    private lateinit var mApp: Application

    override fun init(app: Application) {
        this.mApp = app

        initMavericks(app)
    }

    override fun getApp(): Application {
        return mApp
    }

    private fun initMavericks(app: Application) {
        Mavericks.initialize(app)
    }
}