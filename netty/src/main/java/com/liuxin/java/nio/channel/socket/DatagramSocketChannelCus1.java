package com.liuxin.java.nio.channel.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class DatagramSocketChannelCus1 {
    private static int[] ports = {8082, 8083};
    private static int incre = 0;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress("127.0.0.1", 8081));
        server.register(selector, SelectionKey.OP_READ);

        while (true) {
            int select = selector.select(10000);
            if (select == 0) continue;
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isReadable()) {
                    ByteBuffer buffer1 = ByteBuffer.allocateDirect(1024);
                    DatagramChannel channel = (DatagramChannel) key.channel();
                    SocketAddress address = channel.receive(buffer1);
                    buffer1.flip();
                    StringBuilder builder = new StringBuilder();
                    while (buffer1.hasRemaining()) {
                        builder.append((char) buffer1.get());
                    }
                    System.out.printf("%s  text:%s%n", address.toString(), builder.toString());
                    channel.disconnect().connect(new InetSocketAddress("127.0.0.1", nextPort()));
                    System.out.println("重新连接");
                }

                iterator.remove();
            }

        }
    }

    static int nextPort() {
        incre = incre >= ports.length ? 0 : incre;
        return ports[incre++];
    }
}
