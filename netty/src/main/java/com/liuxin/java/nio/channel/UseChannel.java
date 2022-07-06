package com.liuxin.java.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class UseChannel {
    public static void main(String[] args) throws IOException {
        String path = "/Users/liuxin_gk/Documents/java_demos/netty/src/main/java/com/liuxin/java/nio/channel/";
        String filename = "channel.md";
        FileInputStream inputStream = new FileInputStream(path + filename);
        FileChannel channel = inputStream.getChannel();
        byte[] liuxins = new String("liuxin").getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(liuxins.length);
        buffer.get(liuxins, 0, liuxins.length).flip();
//        channel.write(buffer);//input not write file
    }
}
