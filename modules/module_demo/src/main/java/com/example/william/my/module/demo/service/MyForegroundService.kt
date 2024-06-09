package com.example.william.my.module.demo.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.william.my.basic.basic_module.R
import com.example.william.my.lib.utils.Utils

/**
 * 前台服务
 */
class MyForegroundService : Service() {

    private val mNotificationManager: NotificationManager? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    /**
     * 首次创建服务时调用，如果服务已在运行，则不会调用此方法。该方法只被调用一次。
     * 在调用 onStartCommand() 或 onBind() 之前
     */
    override fun onCreate() {
        super.onCreate()

        // 非Activity场景启动Activity
        // Intent intent = new Intent(this, NotificationActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //不加 FLAG_ACTIVITY_NEW_TASK 将抛出异常
        // startActivity(intent);

        val notification = NotificationCompat.Builder(this, "channelId")
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("通知标题") //通知标题
            .setContentText("通知内容") //通知内容
            .build()
        startForeground(1000, notification) //将服务置于启动状态
    }

    /**
     * 每次通过startService()方法启动Service时都会被调用
     */
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Utils.show("前台服务已启动")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true) //停止前台服务，true：移除通知
    }
}