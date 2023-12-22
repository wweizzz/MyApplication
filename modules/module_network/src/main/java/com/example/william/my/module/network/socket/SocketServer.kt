package com.example.william.my.module.network.socket

import com.example.william.my.lib.utils.Utils
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class SocketServer(port: Int) : WebSocketServer(InetSocketAddress(port)) {

    override fun onStart() {
        Utils.d(TAG, "onStart")
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        Utils.d(TAG, "onOpen")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        Utils.d(TAG, "OnMessage:$message")
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        Utils.d(TAG, "onStart")
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        Utils.e(TAG, "Socket Exception:$ex")
    }

    companion object {

        private val TAG = SocketServer::class.java.simpleName

        const val DEFAULT_SERVER_PORT = 5566
    }
}