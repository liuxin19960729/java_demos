package com.liuxin.java.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class CreateSelector {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();//
        Selector selector = Selector.open();// 静态实例化Selector对象 SPI 发送请求
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);
        boolean isConnect = channel.connect(new InetSocketAddress("103.235.46.40", 80));
        while (true) {
            int select = selector.select();
            System.out.println("select num:" + select);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                System.out.println(key.interestOps());
                if (key.isValid() && key.isConnectable()) {
                    isConnect = channel.finishConnect();
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_CONNECT);
                    if (isConnect) {
                        iterator.remove();//remove lastRet
                    }
                }
            }
        }
    }
}
