package com.example.william.my.module.network.socket

import com.example.william.my.basic.basic_module.utils.Utils
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class SocketServer(port: Int) : WebSocketServer(InetSocketAddress(port)) {

    override fun onStart() {
        println("onStart")
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        println("onOpen")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        println("OnMessage:$message")
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        println("onStart")
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        println("onError:$ex")
    }

    fun println(msg: String) {
        Utils.logcat(TAG, msg)
    }

    companion object {

        private val TAG = SocketServer::class.java.simpleName

        const val DEFAULT_SERVER_PORT = 5566
    }
}