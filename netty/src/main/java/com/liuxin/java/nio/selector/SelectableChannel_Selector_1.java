package com.liuxin.java.nio.selector;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SelectableChannel_Selector_1 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();




        Object lock = open.blockingLock();
        open.bind(new InetSocketAddress(8080));
        /*
         * 在同步代码块里面的代码其他线程不能够执行
         * */
        SocketChannel accept = null;
        synchronized (lock) {
            boolean preIsBlock = open.isBlocking();

            open.configureBlocking(true);

            accept = open.accept();

            open.configureBlocking(preIsBlock);
        }

//        System.out.println(accept);
//        Selector
    }
}
