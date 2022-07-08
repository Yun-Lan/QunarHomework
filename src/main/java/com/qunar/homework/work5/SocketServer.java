package com.qunar.homework.work5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author: lymtics
 * @description:服务端
 */
public class SocketServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup) //设置两个线程组
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到连接个数
                    .channel(NioServerSocketChannel.class)//使用 NioSocketChannel 作为服务器的通道实现
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象(匿名对象)
                        //给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new SocketServerHandler());
                        }
                    });// 给我们的 workerGroup 的 EventLoop 对应的管道设置处理器
            //绑定一个端口并且同步, 生成了一个 ChannelFuture 对象,启动服务器(并绑定端口)
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}

