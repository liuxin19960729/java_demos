package com.liuxin.java.nio.channel;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.WritableByteChannel;
import java.util.Objects;

public class ChannelToChannel {
    public static void main(String[] args) {
        String rootPath = FileChannelCus2.class.getClassLoader().getResource(".").getPath();
        String filename = "file";
        String completePath = rootPath + filename;
        RandomAccessFile accessFile = null;
        FileChannel channel = null;
        FileLock lock = null;
        WritableByteChannel writableByteChannel = Channels.newChannel(System.out);
        try {
            accessFile = new RandomAccessFile(completePath, "rw");
            channel = accessFile.getChannel();
            channel.transferTo(0, channel.size(), writableByteChannel);//传输到

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(lock)) lock.release();
                if (Objects.nonNull(accessFile)) accessFile.close();
                if (Objects.nonNull(channel)) channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
