package com.example.william.my.module.network.netty.client

import com.example.william.my.basic.basic_module.utils.Utils
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel

object NettyClient {

    private val TAG = NettyClient::class.java.simpleName

    private var channel: Channel? = null

    /**
     * 连接服务端
     */
    fun connect(host: String?, port: Int) {
        val workerGroup: EventLoopGroup = NioEventLoopGroup()
        try {
            val bootstrap = Bootstrap()
            bootstrap.group(workerGroup)
            bootstrap.channel(NioSocketChannel::class.java)
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
            bootstrap.handler(NettyClientInitializer())

            // Start the client.
            val f = bootstrap.connect(host, port).sync()

            // Wait until the connection is closed.
            channel = f.channel()
            channel?.closeFuture()?.sync()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            workerGroup.shutdownGracefully()
        }
    }

    fun disconnect() {
        try {
            channel?.disconnect()
            channel?.close()
            channel = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendMessage(msg: String): Boolean {
        channel?.let { safeChannel ->
            if (safeChannel.isActive && safeChannel.isWritable) {
                safeChannel.writeAndFlush(msg + "\n")
                return true
            } else if (!safeChannel.isActive) {
                println("聊天通道未连接")
            } else if (!safeChannel.isWritable) {
                println("聊天通道连接，但不可写")
            }
        } ?: {
            println("聊天通道为空")
        }
        return false
    }

    fun getAddress(): String {
        return channel?.remoteAddress()?.toString() ?: ""
    }

    private fun println(msg: String) {
        Utils.logcat(TAG, msg)
    }
}