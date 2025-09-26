package com.example.william.my.module.network.netty.server

import com.example.william.my.basic.basic_module.utils.Utils
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor

class NettyServerHandler : SimpleChannelInboundHandler<String>() {

    /**
     * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
     * A closed Channel is automatically removed from the collection,
     */
    private val channels: ChannelGroup = DefaultChannelGroup(GlobalEventExecutor.INSTANCE)

    override fun handlerAdded(ctx: ChannelHandlerContext) {
        super.handlerAdded(ctx)
        val channel = ctx.channel()
        println(channel.remoteAddress().toString() + " 加入")
    }

    /**
     * 将会在连接被建立并且准备进行通信时被调用
     */
    override fun channelActive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        println(channel.remoteAddress().toString() + " 在线")
        channels.add(channel)
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        super.channelInactive(ctx)
        val channel = ctx.channel()
        println(channel.remoteAddress().toString() + " 离线")
    }

    override fun handlerRemoved(ctx: ChannelHandlerContext) {
        super.handlerRemoved(ctx)
        val channel = ctx.channel()
        println(channel.remoteAddress().toString() + " 离开")

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
        channels.remove(channel)
    }

    @Deprecated("Deprecated in Java")
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        //super.exceptionCaught(ctx, cause);
        val channel = ctx.channel()
        println(channel.remoteAddress().toString() + " 异常")

        // Close the connection when an exception is raised.
        cause.printStackTrace()
        channel.close()
    }

    /**
     * 观察接收到的数据
     */
    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        val inComing = ctx.channel()
        for (channel in channels) {
            if (channel !== inComing) {
                channel.writeAndFlush("[" + inComing.remoteAddress() + "]:  $msg\n")
            } else {
                channel.writeAndFlush("[localhost]:  $msg\n")
            }
        }
        println(inComing.remoteAddress().toString() + " Msg : $msg")
    }

    private fun println(msg: String) {
        Utils.logcat(TAG, msg)
    }

    companion object {
        private val TAG = NettyServerHandler::class.java.simpleName
    }
}