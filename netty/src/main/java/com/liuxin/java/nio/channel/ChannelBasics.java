package com.liuxin.java.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class ChannelBasics {
    public static void main(String[] args) throws IOException, InterruptedException {
//        ServerSocketChannel server = ServerSocketChannel.open();
//        server.bind(new InetSocketAddress(8080));
//        server.configureBlocking(false);
//        while (true) {
//            System.out.println("start");
//            SocketChannel channel = server.accept();
//            if (Objects.isNull(channel)) {
//                Thread.sleep(3000);
//            }
//            System.out.println("end");
//        }

        SocketChannel open = SocketChannel.open();
        open.configureBlocking(true);
        System.out.println("isBlocking:" + open.isBlocking());
        boolean connect = open.connect(new InetSocketAddress("103.235.46.40", 80));

        Socket socket = open.socket();
        SocketChannel channel = socket.getChannel();
        System.out.println(channel == open);

        System.out.println(connect);
        System.out.println("=======");
    }
}

