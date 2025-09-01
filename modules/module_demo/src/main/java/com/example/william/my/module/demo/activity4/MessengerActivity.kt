package com.example.william.my.module.demo.activity4

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.demo.service.MyMessageService
import java.lang.ref.WeakReference

@Route(path = RouterPath.Demo.Messenger)
class MessengerActivity : BasicResponseActivity() {

    //serviceMessenger表示的是Service端的Messenger，其内部指向了MyService的ServiceHandler实例
    //可以用serviceMessenger向MyService发送消息
    private var mServiceMessenger: Messenger? = null

    //clientMessenger是客户端自身的Messenger，内部指向了ClientHandler的实例
    //MyService可以通过Message的replyTo得到clientMessenger，从而MyService可以向客户端发送消息，
    //并由ClientHandler接收并处理来自于Service的消息
    private var mClientMessenger: Messenger? = null

    private var mServiceConnection: ServiceConnection? = null

    private class ClientHandler(activity: MessengerActivity) : Handler(Looper.getMainLooper()) {

        private val weakReference: WeakReference<MessengerActivity> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val mActivity = weakReference.get() ?: return
            if (msg.what == MSG_CODE_SEND_TO_ACTIVITY) {
                val value = msg.data.getString(MSG_SEND_KEY)
                mActivity.showResponse(value)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initMessenger()
    }

    private fun initMessenger() {
        val clientHandler = ClientHandler(this)
        mClientMessenger = Messenger(clientHandler)
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        sendMessage()
    }

    private fun sendMessage() {
        sendMessage(
            mServiceMessenger,
            MSG_CODE_SEND_TO_SERVICE,
            MSG_SEND_KEY,
            MSG_SEND_MESSAGE,
            mClientMessenger
        )
    }

    private fun sendMessage(
        serviceMessenger: Messenger?,
        id: Int,
        key: String,
        params: String,
        clientMessenger: Messenger?
    ) {
        serviceMessenger?.let { service ->

            val message = Message.obtain()
            message.what = id

            //此处跨进程Message通信不能将msg.obj设置为non-Parcelable的对象，应该使用Bundle
            //message.obj = params;
            val bundle = Bundle()
            bundle.putString(key, params)
            message.data = bundle

            //需要将Message的replyTo设置为客户端的clientMessenger，
            //以便Service可以通过它向客户端发送消息
            message.replyTo = clientMessenger

            service.send(message)
        }
    }

    override fun onStart() {
        super.onStart()

        bindService()
    }

    private fun bindService() {
        mServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
                //我们可以通过从Service的onBind方法中返回的IBinder初始化一个指向Service端的Messenger
                mServiceMessenger = Messenger(iBinder)
                sendMessage(
                    mServiceMessenger,
                    MSG_CODE_SEND_TO_SERVICE,
                    MSG_SEND_KEY,
                    "你好，MyService，我是client",
                    mClientMessenger
                )
            }

            override fun onServiceDisconnected(componentName: ComponentName) {
                //客户端与Service失去连接
                mServiceMessenger = null
            }
        }

        mServiceConnection?.let { conn ->
            bindService(
                Intent(this@MessengerActivity, MyMessageService::class.java),
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

    companion object {
        const val MSG_CODE_SEND_TO_SERVICE = 1
        const val MSG_CODE_SEND_TO_ACTIVITY = 2
        const val MSG_SEND_KEY = "MSG_SEND_KEY"
        const val MSG_SEND_MESSAGE = "MSG_SEND_MESSAGE"
    }
}