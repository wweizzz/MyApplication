package com.example.william.my.core.websocket

import android.os.SystemClock
import com.google.gson.Gson
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketOnSubscribe(
    private val url: String,
    private val request: Request,
    private val okHttpClient: OkHttpClient
) : ObservableOnSubscribe<WebSocketInfo> {
    private var webSocket: WebSocket? = null

    override fun subscribe(emitter: ObservableEmitter<WebSocketInfo>) {
        if (webSocket != null) {
            //降低重连频率
            if ("main" != Thread.currentThread().name) {
                SystemClock.sleep(3000)
                emitter.onNext(WebSocketInfo(true))
            }
        }
        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                WebSocketUtils.setWebSocket(url, webSocket)
                if (!emitter.isDisposed) {
                    emitter.onNext(WebSocketInfo(webSocket, null, true))
                }
                WebSocketLogger.debug("onOpen：$url")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                if (!emitter.isDisposed) {
                    emitter.onNext(WebSocketInfo(webSocket, text))
                }
                WebSocketLogger.debug("onMessageString：$text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                if (!emitter.isDisposed) {
                    emitter.onNext(WebSocketInfo(webSocket, bytes))
                }
                WebSocketLogger.debug("onMessageByteString：$bytes")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                WebSocketLogger.debug("onClosing:" + "code:" + code + "reason:" + reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                emitter.onNext(WebSocketInfo(false, onClosed = true))
                WebSocketLogger.debug("onClosed:" + "code:" + code + "reason:" + reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                if (!emitter.isDisposed) {
                    emitter.onError(t)
                }
                WebSocketLogger.debug("Throwable:$t")
                if (response != null) {
                    WebSocketLogger.debug("onFailure：" + response.code)
                    WebSocketLogger.debug("onFailure：" + Gson().toJson(response.body))
                }
            }
        })
    }
}