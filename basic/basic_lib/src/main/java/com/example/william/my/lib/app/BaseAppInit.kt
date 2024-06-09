package com.example.william.my.lib.app

import android.app.Application
import android.content.res.Configuration

abstract class BaseAppInit {

    lateinit var app: Application

    fun setApplication(app: Application) {
        this.app = app
    }

    open fun init() {}
    open fun initAsync() {}
    open fun onLowMemory() {}
    open fun onConfigurationChanged(newConfig: Configuration) {}
}