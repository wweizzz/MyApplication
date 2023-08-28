package com.example.william.my.module.network.netty.client

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler

class NettyClientHandler : SimpleChannelInboundHandler<String?>() {

    /**
     * 观察接收到的数据
     */
    override fun channelRead0(ctx: ChannelHandlerContext, msg: String?) {
        //ctx.channel().writeAndFlush("[Client]: " + msg + " \n");
    }
}