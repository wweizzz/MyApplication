package com.example.william.my.module.network.activity6

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.core.websocket.WebSocketObserver
import com.example.william.my.core.websocket.WebSocketUtils
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

@Route(path = RouterPath.Network.WebSocketUtils)
class WebSocketUtilsActivity : BasicResponseActivity() {

    public override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        webSocketUtils()
    }

    private fun webSocketUtils() {
        WebSocketUtils
            .createWebSocket(Constants.Url_WebSocket)
            .subscribe(object : WebSocketObserver() {
                override fun onOpen(webSocket: WebSocket?, response: Response?) {
                    super.onOpen(webSocket, response)
                    response?.let {
                        showResponse("onOpen：" + response.code)
                    }
                }

                override fun onMessage(webSocket: WebSocket?, text: String?) {
                    super.onMessage(webSocket, text)
                    webSocket?.send("heart")
                    text?.let {
                        showResponse("onMessageString：$text")
                    }
                }

                override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
                    super.onMessage(webSocket, bytes)
                    bytes?.let {
                        showResponse("onMessageByteString：$bytes")
                    }
                }

                override fun onReconnect() {
                    super.onReconnect()
                    showResponse("onReconnect：")
                }

                override fun onClosed() {
                    super.onClosed()
                    showResponse("onClosed：")
                }
            })
    }
}