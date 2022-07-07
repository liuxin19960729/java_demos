package com.liuxin.java.reactor.commom;

import java.nio.channels.SocketChannel;

public class NioTaskTask {
    private int OPS;
    private SocketChannel socketChannel;

    public NioTaskTask(SocketChannel socketChannel, int OPS) {
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
