package com.liuxin.java.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class SocketChannelCus2 {
    public static void main(String[] args) throws IOException {
        InetSocketAddress remoteAddress = new InetSocketAddress("103.235.46.40", 80);
        SocketChannel ssc = SocketChannel.open();
        ssc.configureBlocking(false);
        ssc.connect(remoteAddress);
        while (ssc.finishConnect()) {

        }
        ssc.close();
    }
}
/*
*非阻塞(等connection pending 状态只能调用下列三个方法检查是否连接成功)
* ssc.finishConnect() 连接是否完成 从出现错误 抛出异常
* ssc.isConnected() 是否连接  不会抛出 异常
*ssc.isConnectionPending() 是否正在准备连接 不会抛出 异常
*
* ssc.finishConnect()
 * connecte faild 异步 trow exception 告诉什么元婴
*  * @throws  NoConnectionPendingException
 *          If this channel is not connected and a connection operation
 *          has not been initiated
 *
 * @throws  ClosedChannelException
 *          If this channel is closed
 *
 * @throws  AsynchronousCloseException
 *          If another thread closes this channel
 *          while the connect operation is in progress
 *
 * @throws  ClosedByInterruptException
* */
