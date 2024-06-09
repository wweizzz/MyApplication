package com.example.william.my.module.demo.activity4

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Demo.HandlerThread)
class HandlerThreadActivity : BasicResponseActivity() {

    private var mHandler: Handler? = null
    private var mHandlerThread: HandlerThread? = null

    override fun initView() {
        super.initView()

        initHandlerThread()
    }

    private fun initHandlerThread() {
        //创建HandlerThread线程
        mHandlerThread = HandlerThread("mHandlerThread")
        //开启HandlerThread线程
        mHandlerThread?.start()

        //在HandlerThread线程中创建一个handler对象
        mHandler = object : Handler(mHandlerThread!!.looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    1 -> showResponse("收到主线程消息")
                    2 -> showResponse("收到子线程消息")
                    else -> {}
                }
            }
        }
        //在主线程给Handler发送消息
        mHandler?.sendEmptyMessage(1)
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        sendMessage()
    }

    private fun sendMessage() {
        Thread { //在子线程给Handler发送数据
            mHandler?.sendEmptyMessage(2)
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        quitHandlerThread()
    }

    private fun quitHandlerThread() {
        //终止HandlerThread运行
        mHandlerThread?.quit()
    }
}