package com.example.william.my.module.network.app

import com.example.william.my.core.okhttp.config.OkHttpConfig
import com.example.william.my.lib.app.BaseAppInit

class NetworkApp : BaseAppInit() {

    override fun init() {
        super.init()
        OkHttpConfig.app = app
    }
}