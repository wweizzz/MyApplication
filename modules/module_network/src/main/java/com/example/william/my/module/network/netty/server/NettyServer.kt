package com.example.william.my.module.network.netty.server

import com.example.william.my.basic.basic_module.utils.Utils
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import java.net.InetAddress

class NettyServer {

    private var channel: Channel? = null

    //用于处理accept事件
    private val bossGroup: EventLoopGroup = NioEventLoopGroup()

    //用于处理通道的读写事件
    private val workerGroup: EventLoopGroup = NioEventLoopGroup()


    fun start(port: Int) {
        try {
            val serverBootstrap = ServerBootstrap()
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(NettyServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)

            // Bind and start to accept incoming connections.
            val future = serverBootstrap.bind(port).sync()

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            channel = future.channel()
            channel?.closeFuture()?.sync()
        } catch (e: Exception) {
            e.printStackTrace()
            Utils.logcat(TAG, "Netty Exception:$e")
        } finally {
            workerGroup.shutdownGracefully()
            bossGroup.shutdownGracefully()
        }
    }

    fun stop() {
        workerGroup.shutdownGracefully()
        bossGroup.shutdownGracefully()
        channel?.closeFuture()
        channel?.close()
    }

    companion object {

        private val TAG = NettyServer::class.java.simpleName

        const val DEFAULT_SERVER_HOST = "192.168.0.110"

        const val DEFAULT_SERVER_PORT = 5568

        @JvmStatic
        fun main(args: Array<String>) {
            //if (args.length > 0) {
            //    port = Integer.parseInt(args[0]);
            //}
            println("Local HostAddress: " + InetAddress.getLocalHost().hostAddress + ":" + DEFAULT_SERVER_PORT)
            NettyServer().start(DEFAULT_SERVER_PORT)
        }
    }
}