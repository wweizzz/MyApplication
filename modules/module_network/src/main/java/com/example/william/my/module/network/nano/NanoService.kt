package com.example.william.my.module.network.nano

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.william.my.lib.utils.Utils
import java.io.IOException

class NanoService : Service() {

    private var nanoServer: NanoServer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        try {
            nanoServer = NanoServer(application)
            nanoServer?.start(3000)
            Utils.d(TAG, "Start HttpService Success...")
        } catch (e: IOException) {
            Utils.e(TAG, "Start HttpService Failed...")
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            nanoServer?.stop()
            Utils.d(TAG, "Stop HttpService Success...")
        } catch (e: Exception) {
            Utils.e(TAG, "Stop HttpService Failed...")
            e.printStackTrace()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    companion object {

        private val TAG = NanoService::class.java.simpleName

        fun startService(context: Context) {
            val intent = Intent(context, NanoService::class.java)
            context.startService(intent)
        }

        fun stopService(context: Context) {
            val intent = Intent(context, NanoService::class.java)
            context.stopService(intent)
        }
    }
}