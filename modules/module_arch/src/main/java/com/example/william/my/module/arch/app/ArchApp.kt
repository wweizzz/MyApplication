package com.example.william.my.module.arch.app

import com.airbnb.mvrx.Mavericks
import com.example.william.my.lib.app.BaseAppInit

class ArchApp : BaseAppInit() {

    override fun init() {
        super.init()

        Mavericks.initialize(app)
    }
}