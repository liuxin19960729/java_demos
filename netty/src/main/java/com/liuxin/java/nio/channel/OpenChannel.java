package com.liuxin.java.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class OpenChannel {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        System.out.printf("socketChannel isOpen:%b%n", socketChannel.isOpen());
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        System.out.println(String.format("serverSocketChannel isOpen:%b", serverSocketChannel.isOpen()));
        DatagramChannel datagramChannel = DatagramChannel.open();
        System.out.println(String.format("datagramChannel isOpen:%b", datagramChannel.isOpen()));
        String path = "/Users/liuxin_gk/Documents/java_demos/netty/src/main/java/com/liuxin/java/nio/channel/";
        String filename = "channels.md";
        RandomAccessFile randomAccessFile = new RandomAccessFile(path+filename, "r");
        FileChannel fileChannel = randomAccessFile.getChannel();

        System.out.println(String.format("fileChannel isOpen:%b", fileChannel.isOpen()));
    }
}
