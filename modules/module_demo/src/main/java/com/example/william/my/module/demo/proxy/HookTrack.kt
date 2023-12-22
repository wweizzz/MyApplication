package com.example.william.my.module.demo.proxy

import android.app.Application
import com.example.william.my.lib.utils.Utils

object HookTrack {

    private val TAG = this.javaClass.simpleName

    private var activityLifeCycleRegister = false

    fun init(application: Application?) {
        if (application == null) {
            Utils.e(TAG, "Please init with the param \"Application\"/")
            throw RuntimeException()
        }
        if (!activityLifeCycleRegister) {
            application.registerActivityLifecycleCallbacks(HookActivityLifecycleCallbacks())
            activityLifeCycleRegister = true
        }
    }
}