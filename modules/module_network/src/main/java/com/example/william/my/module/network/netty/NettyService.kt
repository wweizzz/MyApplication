package com.example.william.my.module.network.netty

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.example.william.my.lib.utils.Utils
import com.example.william.my.module.network.netty.server.NettyServer
import java.net.InetAddress
import java.util.concurrent.Executors

class NettyService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Executors.newSingleThreadExecutor().execute {
            //if (args.length > 0) {
            //    port = Integer.parseInt(args[0]);
            //}
            Utils.d(
                TAG,
                "Local HostAddress: " + InetAddress.getLocalHost().hostAddress + ":" + NettyServer.DEFAULT_SERVER_PORT
            )
            NettyServer().start(NettyServer.DEFAULT_SERVER_PORT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NettyServer().stop()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    companion object {

        private val TAG = NettyService::class.java.simpleName

        @JvmStatic
        fun startService(context: Context) {
            val intent = Intent(context, NettyService::class.java)
            context.startService(intent)
        }

        @JvmStatic
        fun stopService(context: Context) {
            val intent = Intent(context, NettyService::class.java)
            context.stopService(intent)
        }
    }
}