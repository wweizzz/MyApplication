package com.example.william.my.core.websocket

import io.reactivex.rxjava3.observers.DisposableObserver
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

abstract class WebSocketObserver : DisposableObserver<WebSocketInfo>() {

    private var hasOpened = false

    override fun onNext(webSocketInfo: WebSocketInfo) {
        if (webSocketInfo.isOnOpen) {
            hasOpened = true
            onOpen(webSocketInfo.webSocket, webSocketInfo.response)
        } else if (webSocketInfo.string != null) {
            onMessage(webSocketInfo.webSocket, webSocketInfo.string)
        } else if (webSocketInfo.bytes != null) {
            onMessage(webSocketInfo.webSocket, webSocketInfo.bytes)
        } else if (webSocketInfo.isOnReconnect) {
            onReconnect()
        } else if (webSocketInfo.isOnClosed) {
            onClosed()
        }
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    override fun onComplete() {
        if (hasOpened) {
            if (!isDisposed) {
                dispose()
            }
        }
    }

    protected open fun onOpen(webSocket: WebSocket?, response: Response?) {}
    protected open fun onMessage(webSocket: WebSocket?, text: String?) {}
    protected open fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {}
    protected open fun onReconnect() {}
    protected open fun onClosed() {}
}