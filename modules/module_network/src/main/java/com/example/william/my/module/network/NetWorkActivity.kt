package com.example.william.my.module.network

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.activity.RouterRecyclerActivity
import com.example.william.my.basic.basic_module.router.item.RouterItem
import com.example.william.my.basic.basic_module.router.path.RouterPath

@Route(path = RouterPath.Network.Main)
class NetWorkActivity : RouterRecyclerActivity() {

    override fun buildRouter(): ArrayList<RouterItem> {
        val routerItems: ArrayList<RouterItem> = arrayListOf()
        routerItems.add(
            RouterItem("CoilActivity", RouterPath.Network.Coil)
        )
        routerItems.add(
            RouterItem("KtorActivity", RouterPath.Network.Ktor)
        )
        routerItems.add(
            RouterItem("KtorUtilsActivity", RouterPath.Network.KtorUtils)
        )

        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("HttpURLActivity", RouterPath.Network.HttpURL)
        )

        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("VolleyActivity", RouterPath.Network.Volley)
        )
        routerItems.add(
            RouterItem("VolleyHelperActivity", RouterPath.Network.VolleyHelper)
        )

        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("OkHttpActivity", RouterPath.Network.OkHttp)
        )
        routerItems.add(
            RouterItem("OkHttpHelperActivity", RouterPath.Network.OkHttpHelper)
        )

        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("RetrofitActivity", RouterPath.Network.Retrofit)
        )
        routerItems.add(
            RouterItem("RetrofitHelperActivity", RouterPath.Network.RetrofitHelper)
        )

        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("RetrofitRxJavaActivity", RouterPath.Network.RetrofitRxJava)
        )
        routerItems.add(
            RouterItem("RetrofitRxJavaHelperActivity", RouterPath.Network.RetrofitRxJavaHelper)
        )
        
        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("RxRetrofitActivity", RouterPath.Network.RxRetrofit)
        )

        //routerItems.add(
        //    RouterItem("", "")
        //)
        //routerItems.add(
        //    RouterItem("OkHttpDownloadActivity", RouterPath.Network.OkHttpDownload)
        //)
        //routerItems.add(
        //    RouterItem("RetrofitDownloadActivity", RouterPath.Network.RetrofitDownload)
        //)
        //routerItems.add(
        //    RouterItem("RxDownloadActivity", RouterPath.Network.RxDownload)
        //)

        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("WebSocketActivity", RouterPath.Network.WebSocket)
        )
        routerItems.add(
            RouterItem("WebSocketUtilsActivity", RouterPath.Network.WebSocketUtils)
        )

        routerItems.add(
            RouterItem("", "")
        )
        routerItems.add(
            RouterItem("NanoActivity", RouterPath.Network.Nano)
        )
        routerItems.add(
            RouterItem("NettyActivity", RouterPath.Network.Netty)
        )
        routerItems.add(
            RouterItem("SocketActivity", RouterPath.Network.Socket)
        )
        return routerItems
    }
}