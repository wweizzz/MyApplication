package com.example.william.my.module.opensource.hilt

import android.app.Application
import com.example.william.my.lib.hilt.interfaces.IAppInit
import javax.inject.Inject

class OpenInitImpl @Inject constructor() : IAppInit {

    private lateinit var mApp: Application

    override fun init(app: Application) {
        this.mApp = app

        initUpgrade()
    }

    override fun getApp(): Application {
        return mApp
    }

    private fun initUpgrade() {

    }
}