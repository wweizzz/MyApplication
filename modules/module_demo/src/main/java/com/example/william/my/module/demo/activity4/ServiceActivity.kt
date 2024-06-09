package com.example.william.my.module.demo.activity4

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.demo.service.MyAIDLService
import com.example.william.my.module.demo.service.MyAIDLService.XBinder
import com.example.william.my.module.demo.service.MyForegroundService

/**
 * Service
 */
@Route(path = RouterPath.Demo.Service)
class ServiceActivity : BasicResponseActivity() {

    private var mServiceConnection: ServiceConnection? = null

    override fun onStart() {
        super.onStart()

        bindService()
    }

    private fun bindService() {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {

                //BindService
                try {
                    val xBinder = iBinder as XBinder
                    xBinder.showToast("服务已启动")
                } catch (e: ClassCastException) {
                    e.printStackTrace()
                }

                //AIDLService
                //IMyAidlInterface serviceInterface = IMyAidlInterface.Stub.asInterface(iBinder);
                //try {
                //    serviceInterface.showToast("服务已启动");
                //} catch (RemoteException e) {
                //    e.printStackTrace();
                //}
            }

            /**
             * 与服务的连接意外中断时调用，取消绑定时不会调用该方法
             */
            override fun onServiceDisconnected(componentName: ComponentName) {

            }
        }

        mServiceConnection?.let { conn ->
            bindService(
                Intent(this@ServiceActivity, MyAIDLService::class.java),
                conn,
                BIND_AUTO_CREATE
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        unbindService()
    }

    private fun unbindService() {
        mServiceConnection?.let { conn ->
            unbindService(conn)
        }
    }

    public override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        startService()
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(Intent(this, MyForegroundService::class.java))
        } else {
            startService(Intent(this, MyForegroundService::class.java))
        }
    }
}