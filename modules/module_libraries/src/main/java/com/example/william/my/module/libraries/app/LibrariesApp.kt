package com.example.william.my.module.libraries.app

import com.example.william.my.core.eventbus.flow.FlowEventBus
import com.example.william.my.lib.app.BaseAppInit

class LibrariesApp : BaseAppInit() {

    override fun init() {
        super.init()

        FlowEventBus.init(app)
    }
}