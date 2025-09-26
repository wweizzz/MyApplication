package com.example.william.my.module.demo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.william.my.basic.basic_module.utils.Utils
import com.example.william.my.module.demo.IMyAidlInterface

class MyAIDLService : Service() {

    //private XBinder xBinder? = null
    private var aidlBinder: AIDLBinder? = null

    override fun onBind(intent: Intent): IBinder? {
        //return xBinder
        return aidlBinder
    }

    /**
     * 首次创建服务时调用，如果服务已在运行，则不会调用此方法。该方法只被调用一次。
     * 在调用 onStartCommand() 或 onBind() 之前
     */
    override fun onCreate() {
        super.onCreate()
        //xBinder = XBinder()
        aidlBinder = AIDLBinder()
    }

    class XBinder : Binder() {
        fun showToast(s: String?) {
            Utils.toast(s)
        }
    }

    class AIDLBinder : IMyAidlInterface.Stub() {
        override fun showToast(message: String?) {
            Utils.toast(message)
        }
    }
}