package com.example.william.my.module.network.activity7

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.network.nano.NanoServer
import com.example.william.my.module.network.socket.SocketService
import com.example.william.my.module.network.utils.NetworkUtils

/**
 * http://www.websocket-test.com/
 * https://github.com/TooTallNate/Java-WebSocket
 */
@Route(path = RouterPath.Network.Socket)
class SocketActivity : BasicResponseActivity() {

    override fun initView() {
        super.initView()

        showIPAddress()
    }

    private fun showIPAddress() {
        showResponse("http://" + NetworkUtils.getIPAddress(true) + ":" + NanoServer.DEFAULT_SERVER_PORT)
    }

    override fun onStart() {
        super.onStart()
        SocketService.startService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketService.stopService(this)
    }
}