package com.liuxin.java.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class CreateSelector {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();//
        Selector selector = Selector.open();// 静态实例化Selector对象 SPI 发送请求
        channel.register(selector, SelectionKey.OP_CONNECT);
        boolean isConnect = false;





        while (true) {
            selector.select(10000);//等待10
            Set<SelectionKey> keys = selector.keys();
            for (SelectionKey key : keys) {
                isConnect = channel.connect(new InetSocketAddress("103.235.46.40", 80));
            }

        }
    }
}
