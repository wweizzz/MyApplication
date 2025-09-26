package com.example.william.my.module.network.socket

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.william.my.basic.basic_module.utils.Utils

class SocketService : Service() {

    private var socketServer: SocketServer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        try {
            socketServer = SocketServer(SocketServer.DEFAULT_SERVER_PORT)
            socketServer?.isReuseAddr = true
            socketServer?.start()
            println("Start ServerSocket Success...")
        } catch (e: Exception) {
            println("Start ServerSocket Failed...")
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socketServer?.stop()
            println("Stop ServerSocket Success...")
        } catch (e: Exception) {
            println("Stop ServerSocket Failed...")
            e.printStackTrace()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun println(msg: String) {
        Utils.logcat(TAG, msg)
    }

    companion object {

        private val TAG = SocketService::class.java.simpleName

        @JvmStatic
        fun startService(context: Context) {
            val intent = Intent(context, SocketService::class.java)
            context.startService(intent)
        }

        @JvmStatic
        fun stopService(context: Context) {
            val intent = Intent(context, SocketService::class.java)
            context.stopService(intent)
        }
    }
}