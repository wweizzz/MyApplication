package com.example.william.my.module.network.activity7

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.module.network.nano.NanoServer
import com.example.william.my.module.network.nano.NanoService
import com.example.william.my.module.network.utils.NetworkUtils

/**
 * https://github.com/NanoHttpd/nanohttpd
 */
@Route(path = RouterPath.Network.Nano)
class NanoActivity : BasicResponseActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        showIPAddress()
    }

    private fun showIPAddress() {
        showResponse("http://" + NetworkUtils.getIPAddress(true) + ":" + NanoServer.DEFAULT_SERVER_PORT)
    }

    override fun onStart() {
        super.onStart()
        NanoService.startService(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        NanoService.stopService(this)
    }
}