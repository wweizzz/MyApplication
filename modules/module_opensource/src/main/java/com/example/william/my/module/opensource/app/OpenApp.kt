package com.example.william.my.module.opensource.app

import com.example.william.my.lib.app.BaseAppInit
import com.example.william.my.module.opensource.loadsir.DefaultCallback
import com.example.william.my.module.opensource.loadsir.ErrorCallback
import com.google.firebase.FirebaseApp
import com.kingja.loadsir.core.LoadSir

class OpenApp : BaseAppInit() {

    override fun init() {
        super.init()

        initFirebaseApp()
        initLoadSir()
    }

    private fun initFirebaseApp() {
        FirebaseApp.initializeApp(app)
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(DefaultCallback())
            .setDefaultCallback(DefaultCallback::class.java) //设置默认状态页
            .commit()
    }
}