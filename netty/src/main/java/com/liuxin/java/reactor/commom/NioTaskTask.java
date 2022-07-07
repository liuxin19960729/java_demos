package com.liuxin.java.reactor.commom;

import java.nio.channels.SocketChannel;

public class SocketChannelOpsTask {
    private int OPS;
    private SocketChannel socketChannel;

    public SocketChannelOpsTask(int OPS, SocketChannel socketChannel) {
        this.OPS = OPS;
        this.socketChannel = socketChannel;
    }

    public int getOPS() {
        return OPS;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
