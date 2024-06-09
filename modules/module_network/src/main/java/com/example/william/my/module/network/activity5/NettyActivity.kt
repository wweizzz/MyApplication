package com.example.william.my.module.network.activity5

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.network.netty.NettyService
import com.example.william.my.module.network.netty.client.NettyClient
import com.example.william.my.module.network.netty.server.NettyServer
import java.net.InetAddress
import java.util.concurrent.Executors

/**
 * https://github.com/netty/netty/
 */
@Route(path = RouterPath.Network.Netty)
class NettyActivity : BasicResponseActivity() {

    override fun initView() {
        super.initView()

        connect()
    }

    private fun connect() {
        Executors.newSingleThreadExecutor().execute {
            //NettyClient.connect(NettyServer.DEFAULT_SERVER_HOST, NettyServer.DEFAULT_SERVER_PORT)
            NettyClient.connect(
                InetAddress.getLocalHost().hostAddress,
                NettyServer.DEFAULT_SERVER_PORT
            )
        }
    }

    override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        NettyClient.sendMessage(NettyClient.getAddress())
    }

    override fun onStart() {
        super.onStart()
        NettyService.startService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NettyClient.disconnect()
        NettyService.stopService(this)
    }
}