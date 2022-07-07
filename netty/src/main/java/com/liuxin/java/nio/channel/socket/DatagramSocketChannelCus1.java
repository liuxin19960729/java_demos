package com.liuxin.java.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class DatagramSocketChannelCus {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress("127.0.0.1", 8081));

        byte[] bytes = "hello".getBytes();
        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length).put(bytes);
        buffer.flip();
        server.register(selector, SelectionKey.OP_READ, buffer);
        server.send(buffer, new InetSocketAddress("127.0.0.1", 8082));


        while (true) {
            int select = selector.select(1000);
            if (select == 0) continue;
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isReadable()) {
                    ByteBuffer buffer1 = ByteBuffer.allocateDirect(1024);
                    DatagramChannel channel = (DatagramChannel) key.channel();
                    channel.read(buffer1);
                    buffer1.flip();
                    StringBuilder builder = new StringBuilder();
                    while (buffer1.hasRemaining()) {
                        builder.append((char) buffer.get());
                    }
                    System.out.println(buffer.toString());
                }

                iterator.remove();
            }
        }
    }


}
