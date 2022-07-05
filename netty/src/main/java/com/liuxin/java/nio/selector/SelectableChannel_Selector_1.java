package com.liuxin.java.nio.selector;

import java.io.IOException;

import java.nio.channels.ServerSocketChannel;

public class SelectableChannelTest1 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        open.configureBlocking(false);
        Object lock = open.blockingLock();
        /*
        * 在
        * */
        synchronized (lock) {
            boolean preIsBlock = open.isBlocking();

            open.configureBlocking(false);


        }
    }
}
