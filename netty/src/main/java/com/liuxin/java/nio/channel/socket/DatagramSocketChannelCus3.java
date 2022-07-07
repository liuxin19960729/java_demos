package com.liuxin.java.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class DatagramSocketChannelCus3 {
    private static int increment = 0;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress("127.0.0.1", 8083));
        ByteBuffer buffer = null;
        server.register(selector, SelectionKey.OP_WRITE, buffer);
        while (true) {
            int select = selector.select(1000);
            if (select == 0) continue;
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isWritable()) {
                    buffer = (ByteBuffer) key.attachment();
                    DatagramChannel channel = (DatagramChannel) key.channel();
                    if (Objects.isNull(buffer)) {
                        byte[] b = ("hello" + increment++).getBytes();
                        buffer = ByteBuffer.allocate(b.length);
                        buffer.put(b).flip();
                    }
                    int sendNum = channel.send(buffer, new InetSocketAddress("127.0.0.1", 8081));
                    key.attach(sendNum == 0 ? buffer : null);
                }

                iterator.remove();
            }
        }
    }


}
