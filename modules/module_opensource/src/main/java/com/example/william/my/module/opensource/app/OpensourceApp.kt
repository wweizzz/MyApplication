package com.example.william.my.module.opensource.app

import com.example.william.my.lib.app.BaseAppInit

class OpensourceApp : BaseAppInit() {

    override fun init() {
        super.init()

        System.loadLibrary("msaoaidsec") // TODO （3）SDK初始化操作
    }
}