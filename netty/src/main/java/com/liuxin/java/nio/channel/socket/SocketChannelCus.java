package com.liuxin.java.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class SocketChannelCus {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();//Socket 点对点封装 默认阻塞模式
        channel.configureBlocking(false);
        boolean connect = channel.connect(new InetSocketAddress("103.235.46.40", 80));
        System.out.println(connect);
        System.out.println(channel.isConnectionPending());//判断是否是在链接等待

        System.out.println(channel.finishConnect());

    }
}
/*
* Socket 建立连接需要时间
*    三次对话 ,,,,网路传输时间
*
* 非阻塞模式下
* channel.finishConnect()
*   任何时候都能够安全的调用
*  1.  throw   NoConnectionPendingException  未执行 channel.connect()方法
*  2.  正在建立连接的过程立即返回false
*  3. 第二次调用 connect 阻塞到连接成功
*     在次调用 finishConnect() 返回 true;
*
* 处于中间等待你只能调用 finishConnect()
*
* */