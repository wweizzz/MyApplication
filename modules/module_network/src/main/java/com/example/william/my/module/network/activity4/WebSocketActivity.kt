package com.example.william.my.module.network.activity4

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.activity.BasicResponseActivity
import com.example.william.my.basic.basic_module.base.Constants
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

@Route(path = RouterPath.Network.WebSocket)
class WebSocketActivity : BasicResponseActivity() {

    private val mOkHttpClient: OkHttpClient = OkHttpClient()

    public override fun onResponseClick(view: View) {
        super.onResponseClick(view)

        webSocket()
    }

    private fun webSocket() {
        val request: Request = Request.Builder()
            .url(Constants.Url_WebSocket)
            .build()
        mOkHttpClient.newWebSocket(request, object : WebSocketListener() {
            /**
             * 和远程建立连接时回调
             */
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                showResponse("onOpen：" + response.code)
            }

            /**
             * 接收到消息时回调
             */
            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                webSocket.send("heart")
                showResponse("onMessageString：$text")
            }

            /**
             * 接收到消息时回调
             */
            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                showResponse("onMessageByteString：$bytes")
            }

            /**
             * 准备关闭时回调
             */
            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                showResponse("onClosing:" + "code:" + code + "reason:" + reason)
            }

            /**
             * 连接关闭时回调
             */
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                showResponse("onClosed:" + "code:" + code + "reason:" + reason)
            }

            /**
             * 失败时被回调
             */
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                val builder = StringBuilder("onFailure:")
                if (t.message != null) {
                    builder.append("Throwable:").append(t.message)
                }
                if (response != null) {
                    builder.append("onFailure：").append(response.code)
                    builder.append("onFailure：").append(Gson().toJson(response.body))
                }
                showResponse(builder.toString())
            }
        })
    }
}