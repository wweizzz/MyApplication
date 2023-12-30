package com.example.william.my.module.demo.app

import com.example.william.my.lib.app.BaseAppInit
import com.example.william.my.module.demo.proxy.HookTrack

class DemoApp : BaseAppInit() {

    override fun init() {
        super.init()

        HookTrack.init(app)
    }
}