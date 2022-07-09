package com.qunar.homework.work5.netty;

import com.qunar.pojo.ResultCount;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import static com.qunar.homework.work5.HttpUtil.writeBack;

/**
 * @author: lymtics
 * @description:服务端处理器
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取客户端发送的消息并且处理
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已接收",CharsetUtil.UTF_8));
        ResultCount resultCount = writeBack(byteBuf.toString(CharsetUtil.UTF_8));
        ctx.writeAndFlush(Unpooled.copiedBuffer(resultCount.toString(), CharsetUtil.UTF_8));

    }

    //处理异常 一般需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


}
