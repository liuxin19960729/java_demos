package com.liuxin.java.nio.channel;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class UseChannel2 {
    public static void main(String[] args) throws IOException {
        ReadableByteChannel readableByteChannel = Channels.newChannel(System.in);
        WritableByteChannel writableByteChannel = Channels.newChannel(System.out);

        copy2(readableByteChannel, writableByteChannel);
        readableByteChannel.close();
        writableByteChannel.close();
    }

    private static void copy(ReadableByteChannel red, WritableByteChannel wri) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (red.read(buffer) != -1) {
            wri.write(buffer);
            buffer.compact();
        }

        buffer.flip();
        while (buffer.hasRemaining()) {
            wri.write(buffer);
        }
    }

    private static void copy2(ReadableByteChannel red, WritableByteChannel wri) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        while (red.read(buffer) != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                wri.write(buffer);
            }
            buffer.clear();
        }
    }
}


/*
 *channel  noblocing 不会让channel休眠
 *要么 完成完成 要么 什么操作都不做
 *
 * 只哟stream 同道才能使用非阻塞
 * */