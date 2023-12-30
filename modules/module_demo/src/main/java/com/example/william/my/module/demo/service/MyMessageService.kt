package com.example.william.my.module.demo.service

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import com.example.william.my.module.demo.activity4.MessengerActivity
import java.lang.ref.WeakReference

class MyMessageService : Service() {

    //clientMessenger表示的是客户端的Messenger，可以通过来自于客户端的Message的replyTo属性获得，
    //其内部指向了客户端的ClientHandler实例，可以用clientMessenger向客户端发送消息
    private var mClientMessenger: Messenger? = null

    //serviceMessenger是Service自身的Messenger，其内部指向了ServiceHandler的实例
    //客户端可以通过IBinder构建Service端的Messenger，从而向Service发送消息，
    //并由ServiceHandler接收并处理来自于客户端的消息
    private var mServiceMessenger: Messenger? = null

    /**
     * 接收并处理从客户端收到的消息
     */
    private class ServiceHandler(service: MyMessageService) : Handler(Looper.getMainLooper()) {

        private val weakReference: WeakReference<MyMessageService?> = WeakReference(service)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val mService = weakReference.get() ?: return
            if (msg.what == MessengerActivity.MSG_CODE_SEND_TO_SERVICE) {
                //通过Message的replyTo获取到客户端自身的Messenger，
                //Service可以通过它向客户端发送消息
                mService.mClientMessenger = msg.replyTo
                val value = msg.data.getString(MessengerActivity.MSG_SEND_KEY)
                sendMessage(
                    mService.mClientMessenger,
                    MessengerActivity.MSG_CODE_SEND_TO_ACTIVITY,
                    MessengerActivity.MSG_SEND_KEY,
                    value
                )
            }
        }

        private fun sendMessage(
            clientMessenger: Messenger?,
            id: Int,
            key: String,
            params: String?
        ) {
            clientMessenger?.let { client ->

                val message = Message.obtain()
                message.what = id

                //此处跨进程Message通信不能将msg.obj设置为non-Parcelable的对象，应该使用Bundle
                //message.obj = params;
                val bundle = Bundle()
                bundle.putString(key, params)
                message.data = bundle

                //向客户端发送消息
                client.send(message)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        //获取Service自身Messenger所对应的IBinder，并将其发送共享给所有客户端
        return mServiceMessenger!!.binder
    }

    override fun onCreate() {
        super.onCreate()

        initMessenger()
    }

    private fun initMessenger() {
        val serviceHandler = ServiceHandler(this)
        mServiceMessenger = Messenger(serviceHandler)
    }
}