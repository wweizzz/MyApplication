package com.example.william.my.module.demo.activity2

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.R
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Demo.Notification)
class NotificationActivity : BasicResponseActivity() {

    private var mNotificationManager: NotificationManager? = null

    override fun initView() {
        super.initView()

        initNotification()
    }

    private fun initNotification() {
        mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //groupId要唯一
            val groupId = "groupId"
            val groupName = "groupName"
            val group = NotificationChannelGroup(groupId, groupName)

            //创建group
            mNotificationManager?.createNotificationChannelGroup(group)

            //channelId要唯一
            val channelId = "channelId"
            val channelName = "channelName"
            val channelDescription = "channelDescription"
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.setShowBadge(true) //显示角标
            channel.description = channelDescription

            //将渠道添加进组（先创建组才能添加）
            channel.group = groupId

            //创建channel
            mNotificationManager?.createNotificationChannel(channel)
        }
    }


    public override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        notifyNotification()
    }

    private fun notifyNotification() {
        val notification = NotificationCompat.Builder(
            this@NotificationActivity,
            "channelId"
        )
            //.setPriority(NotificationManager.IMPORTANCE_HIGH)//设置优先级//@RequiresApi(api = Build.VERSION_CODES.N)
            //.setWhen(System.currentTimeMillis())//设置通知时间，默认为系统发出通知的时间，通常不用设置
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle("通知标题") //通知标题
            .setContentText("通知内容") //通知内容
            .setAutoCancel(true)
            .build()
        mNotificationManager?.notify(1, notification) //发送通知,id=1
    }

    override fun onDestroy() {
        super.onDestroy()

        deleteNotification()
    }

    private fun deleteNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager?.deleteNotificationChannel("channelId")
        }
    }
}