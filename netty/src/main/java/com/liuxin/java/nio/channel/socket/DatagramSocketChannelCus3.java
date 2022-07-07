package com.liuxin.java.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class DatagramSocketChannelCus2 {
    private static int increment = 0;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress("127.0.0.1", 8082));
//        server.connect(new InetSocketAddress("127.0.0.1",8081));
        byte[] bytes = ("hello" + increment++).getBytes();
        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length).put(bytes);
        buffer.flip();
        server.register(selector, SelectionKey.OP_WRITE, buffer);
        server.send(buffer, new InetSocketAddress("127.0.0.1", 8081));
        buffer = null;
        while (true) {
            int select = selector.select(1000);
            if (select == 0) continue;
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isWritable()) {
                    ByteBuffer attachment = (ByteBuffer) key.attachment();
                    if (!attachment.hasRemaining()) {
                        byte[] b = ("hello" + increment++).getBytes();
                        buffer = ByteBuffer.allocate(b.length);
                        buffer.put(b).flip();
                        key.attach(buffer);
                        DatagramChannel channel = (DatagramChannel) key.channel();
                        channel.send(buffer, new InetSocketAddress("127.0.0.1", 8081));
                    }
                }

                iterator.remove();
            }
        }
    }


}
