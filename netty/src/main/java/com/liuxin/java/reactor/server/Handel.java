package com.liuxin.java.reactor.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Handel implements Runnable {
    private SocketChannel socketChannel;
    private SubEventLoop parent;

    public Handel(SocketChannel socketChannel, SubEventLoop parent) {
        this.socketChannel = socketChannel;
        this.parent = parent;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        try {
            socketChannel.read(buffer);
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String string = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(string);
        } catch (IOException e) {
            //todo
            e.printStackTrace();
        } finally {
            buffer = null;//GC
        }
    }
}
