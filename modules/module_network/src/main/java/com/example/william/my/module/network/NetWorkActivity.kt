package com.example.william.my.module.network

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.ARouterPath

@Route(path = ARouterPath.Network.Main)
class NetWorkActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(
            RouterItem("CoilActivity", ARouterPath.Network.Coil)
        )
        routerItems.add(
            RouterItem("HttpURLActivity", ARouterPath.Network.HttpURL)
        )
        routerItems.add(
            RouterItem("VolleyActivity", ARouterPath.Network.Volley)
        )
        routerItems.add(
            RouterItem("VolleyHelperActivity", ARouterPath.Network.VolleyHelper)
        )
        routerItems.add(
            RouterItem("OkHttpActivity", ARouterPath.Network.OkHttp)
        )
        routerItems.add(
            RouterItem("OkHttpHelperActivity", ARouterPath.Network.OkHttpHelper)
        )
        routerItems.add(
            RouterItem("OkHttpDownloadActivity", ARouterPath.Network.OkHttpDownload)
        )
        routerItems.add(
            RouterItem("RetrofitActivity", ARouterPath.Network.Retrofit)
        )
        routerItems.add(
            RouterItem("RetrofitHelperActivity", ARouterPath.Network.RetrofitHelper)
        )
        routerItems.add(
            RouterItem("RetrofitDownloadActivity", ARouterPath.Network.RetrofitDownload)
        )
        routerItems.add(
            RouterItem("RetrofitRxJavaActivity", ARouterPath.Network.RetrofitRxJava)
        )
        routerItems.add(
            RouterItem("RetrofitRxJavaHelperActivity", ARouterPath.Network.RetrofitRxJavaHelper)
        )
        routerItems.add(
            RouterItem("RetrofitRxJavaDownloadActivity", ARouterPath.Network.RetrofitRxJavaDownload)
        )
        routerItems.add(
            RouterItem("RxRetrofitActivity", ARouterPath.Network.RxRetrofit)
        )
        routerItems.add(
            RouterItem("RxDownloadActivity", ARouterPath.Network.RxDownload)
        )
        routerItems.add(
            RouterItem("WebSocketActivity", ARouterPath.Network.WebSocket)
        )
        routerItems.add(
            RouterItem("WebSocketUtilsActivity", ARouterPath.Network.WebSocketUtils)
        )
        routerItems.add(
            RouterItem("NanoActivity", ARouterPath.Network.Nano)
        )
        routerItems.add(
            RouterItem("NettyActivity", ARouterPath.Network.Netty)
        )
        routerItems.add(
            RouterItem("SocketActivity", ARouterPath.Network.Socket)
        )
        return routerItems
    }
}