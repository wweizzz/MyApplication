package com.example.william.my.lib.hilt.interfaces

import android.app.Application
import android.content.res.Configuration

interface IAppInit {

    fun init(app: Application)

    fun initAsync(app: Application) {}

    fun onLowMemory() {}

    fun onConfigurationChanged(newConfig: Configuration) {}

    fun getApp(): Application
}