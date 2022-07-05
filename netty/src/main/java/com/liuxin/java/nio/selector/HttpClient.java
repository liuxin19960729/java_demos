package com.liuxin.java.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;

public class HttpClient {
    private static final String HOST = "103.235.46.40";
    private static final int PORT = 80;

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        Selector selector = Selector.open();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);
        boolean isConnect = channel.connect(new InetSocketAddress(HOST, PORT));
        long connStart = System.currentTimeMillis();
        conti:
        while (true) {
            int select = selector.select();
            if (select == 0) continue;
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
//                next.interestOps()
                selector.isOpen();
                if (next.isConnectable()) {
                    SocketChannel channel_ = (SocketChannel) next.channel();
                    isConnect = channel_.finishConnect();
                    if (isConnect) {
                        req(next);
                    } else {
                        long connEnd = System.currentTimeMillis();
                        long delay = connEnd - connStart;
                        if (delay > 5000) {
                            throw new TimeOutException();
                        }
                        continue conti;
                    }
                }
                if (selector.isOpen() && next.isReadable()) {
                    resp(next);
                }
                if (selector.isOpen() && next.isWritable()) {
                    req(next);
                }

                if (!selector.isOpen()) break conti;
                iterator.remove();
            }

        }
    }

    private static void req(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        if (Objects.isNull(buffer)) {
            StringBuilder builder = new StringBuilder();
            builder.append("GET /  HTTP/1.1 \r\n");
            byte[] bytes = builder.toString().getBytes();
            buffer = ByteBuffer.allocateDirect(bytes.length).put(bytes);
            buffer.flip();
            key.attach(buffer);
        }
        int remaining = buffer.remaining();
        SocketChannel channel = (SocketChannel) key.channel();
        if (remaining == 0 || remaining == channel.write(buffer)) {
            key.interestOps(SelectionKey.OP_READ);
            key.attach(null);
        } else {//继续写
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private static void resp(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        if (Objects.isNull(buffer)) {
            buffer = ByteBuffer.allocateDirect(1024);
            key.attach(buffer);
        }
        SocketChannel channel = (SocketChannel) key.channel();
        int read = channel.read(buffer);
        buffer.position(read).flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        System.out.println(new String(bytes));
        key.selector().close();
    }
}

class TimeOutException extends RuntimeException {

}