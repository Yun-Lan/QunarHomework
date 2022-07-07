package com.qunar.homework.work5;

import com.qunar.pojo.ResultCount;

import javax.crypto.Mac;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: lymtics
 * @description:服务端
 */
public class Server {
    public static void main(String[] args) throws Exception{
        server();
    }

    private static void server() throws Exception{
        //创建Selector
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //将Channel注册到selector上（绑定关系）
        //当事件发生后可以根据SelectionKey知道哪个事件和哪个Channel的事件
        SelectionKey selectionKey = serverSocketChannel.register(selector,0,null);
        //selectionKey 关注ACCEPT事件（也可以在上面注册时用第二个参数设置）
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        while (true){
            selector.select();
            System.out.println("开始处理事件=================");
            // 处理事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey sk = iterator.next();
                //获取到SelectionKey对象之后，将集合内的引用删掉（Selecotr不会自动删除）
                iterator.remove();
                if(sk.isAcceptable()){ //有连接请求事件
                    System.out.println("连接请求");
                    //通过SelectionKey 获取对应的channel
                    ServerSocketChannel channel = (ServerSocketChannel) sk.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    //读取数据内容,bytebuffer大小注意消息边界（一个字符串被分割读取多次出来结果不准确）
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //将socketChannel绑定Selector
                    SelectionKey socketKey = socketChannel.register(selector,0,byteBuffer);
                    //关注可读事件
                    socketKey.interestOps(SelectionKey.OP_READ);
                }else if(sk.isReadable()){//可读事件
                    try {
                        System.out.println("读取请求");
                        //取到Channel
                        SocketChannel socketChannel = (SocketChannel) sk.channel();
                        //获取到绑定的ByteBuffer
                        ByteBuffer byteBuffer = (ByteBuffer) sk.attachment();
                        int read = socketChannel.read(byteBuffer);
                        //如果是正常断开 read = -1
                        if(read == -1){
                            //取消事件
                            sk.cancel();
                            continue;
                        }
                        byteBuffer.flip();
                        String str = StandardCharsets.UTF_8.decode(byteBuffer).toString();
                        System.out.println("服务端读取到数据：" + str);
                        //写数据回客户端
                        ResultCount resultCount = writeBack(str);
                        ByteBuffer writeBuffer = StandardCharsets.UTF_8.encode(resultCount.toString());
                        socketChannel.write(writeBuffer);
                        //如果数据一次没写完关注可写事件进行再次写入（大数据一次写不完的情况）
                        if(writeBuffer.hasRemaining()){
                            //关注可写事件，添加事件，用interestOps()方法获取到之前的事件加上可写事件
                            sk.interestOps(sk.interestOps() + SelectionKey.OP_WRITE);
                            sk.attach(writeBuffer);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        //客户端异常断开连接 ，取消事件
                        sk.cancel();
                    } finally {
//                        selector.close();
                    }
                }
            }
        }
    }

    //统计字符并且写回
    public static ResultCount writeBack(String url) {
        try {
            String httpContent = new HttpUtil().doGet(url);
            int allCharCount = httpContent.length();    //总字符数
            int chineseCount = 0;   //中文字符数
            int letterCount = 0;    //英文字符数
            int charCount = 0;      //标点符号字符数
            Pattern wordPattern = Pattern.compile("[a-zA-Z]+$");    //字母匹配
            Pattern numPattern = Pattern.compile("[0-9]*");         //数字匹配
            Pattern ChinesePattern = Pattern.compile("[\\u4e00-\\u9fa5]{0,}$");     //中文匹配
            Matcher wordMatcher = null;
            Matcher numMatcher = null;
            Matcher ChineseMatcher = null;
            for (int i = 0; i < httpContent.toCharArray().length; i++) {
                String str = String.valueOf(httpContent.charAt(i));
                wordMatcher = wordPattern.matcher(str);
                numMatcher = numPattern.matcher(str);
                ChineseMatcher = ChinesePattern.matcher(str);
                if (wordMatcher.matches()) {
                    letterCount++;
                }else if (ChineseMatcher.matches()){
                    chineseCount++;
                }else if (str.equals(" ")){
                    continue;
                }else if (!numMatcher.matches()){
                    charCount++;
                }
            }
            return new ResultCount(allCharCount, chineseCount, letterCount, charCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}




