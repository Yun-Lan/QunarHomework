package com.qunar.homework.work5.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: lymtics
 * @description:socket客户端
 */
public class SocketClient {
    public static void main(String[] args) throws Exception {
        client();

    }

    private static void client() throws Exception {
        //1.创建客户端
        SocketChannel socketChannel = SocketChannel.open();
        //连接服务端
        socketChannel.connect(new InetSocketAddress("localhost",8080));

        Scanner scanner = new Scanner(System.in);
        String internetAddress = scanner.nextLine();
        System.out.println(internetAddress);
        socketChannel.write(StandardCharsets.UTF_8.encode(internetAddress));
        //3.读取服务端返回数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("服务端返回数据=======：" + StandardCharsets.UTF_8.decode(byteBuffer).toString());
        //断开连接
        socketChannel.close();
    }
}
