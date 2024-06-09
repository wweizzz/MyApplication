package com.example.william.my.module.demo.activity4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import java.lang.ref.WeakReference

/**
 * BroadcastReceiver
 */
@Route(path = RouterPath.Demo.Broadcast)
class BroadcastActivity : BasicResponseActivity() {

    private var mMessageReceiver: MessageReceiver? = null

    public override fun onResponseClick(view: View) {
        super.onResponseClick(view)
        sendBroadcast()
    }

    private fun sendBroadcast() {
        val bundle = Bundle()
        bundle.putString("message", MessageReceiver.ACTION_UPDATE)
        val intent = Intent()
        intent.putExtras(bundle)
        intent.action =
            MessageReceiver.ACTION_UPDATE
        sendBroadcast(intent)

        //LocalBroadcastManager.getInstance(BroadcastActivity.this).sendBroadcast(intent); 
    }

    override fun onStart() {
        super.onStart()
        registerReceiver()
    }

    private fun registerReceiver() {
        mMessageReceiver = MessageReceiver(this)

        val intentFilter = IntentFilter()
        intentFilter.addAction(MessageReceiver.ACTION_UPDATE)
        registerReceiver(mMessageReceiver, intentFilter)

        //LocalBroadcastManager.getInstance(BroadcastActivity.this).registerReceiver(mMessageReceiver, intentFilter);
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver()
    }

    private fun unregisterReceiver() {
        unregisterReceiver(mMessageReceiver)

        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    /**
     * 消息监听器
     */
    class MessageReceiver(activity: BroadcastActivity?) : BroadcastReceiver() {

        private val weakReference: WeakReference<BroadcastActivity?> = WeakReference(activity)

        override fun onReceive(context: Context, intent: Intent) {
            weakReference.get()?.let {
                it.mBinding.basicsResponse.text = intent.getStringExtra("message")
            }
        }

        companion object {
            //声明一个操作常量字符串
            const val ACTION_UPDATE = "com.example.broadcast"
        }
    }
}