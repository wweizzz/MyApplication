package com.example.william.my.core.websocket

import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

class WebSocketInfo {

    lateinit var webSocket: WebSocket

    var response: Response? = null

    var string: String? = null

    var bytes: ByteString? = null

    var isOnOpen = false

    var isOnReconnect = false

    var isOnClosed = false


    constructor(onReconnect: Boolean) {
        isOnReconnect = onReconnect
    }

    constructor(onReconnect: Boolean, onClosed: Boolean) {
        isOnReconnect = onReconnect
        isOnClosed = onClosed
    }

    constructor(mWebSocket: WebSocket, mResponse: Response?, onOpen: Boolean) {
        webSocket = mWebSocket
        response = mResponse
        isOnOpen = onOpen
    }

    constructor(mWebSocket: WebSocket, mString: String?) {
        webSocket = mWebSocket
        string = mString
    }

    constructor(mWebSocket: WebSocket, mBytes: ByteString?) {
        webSocket = mWebSocket
        bytes = mBytes
    }
}