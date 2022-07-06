package com.liuxin.java.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Objects;

public class FileMappingChannel {
    private FileChannel channel;

    public static void main(String[] args) {
        String rootPath = FileChannelCus2.class.getClassLoader().getResource(".").getPath();
        String filename = "file";
        String completePath = rootPath + filename;
        RandomAccessFile accessFile = null;
        FileChannel channel = null;
        FileLock lock = null;
        try {
            accessFile = new RandomAccessFile(completePath, "rw");
            channel = accessFile.getChannel();
            lock = channel.lock();
            //size !<0    !>Interger.MAX >2.1G 不允许
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
            System.out.println(buffer.isDirect());
            StringBuilder builder = new StringBuilder(buffer.remaining());
            if (buffer.isLoaded()) {
                while (buffer.hasRemaining()) {
                    builder.append((char) buffer.get());
                }
                System.out.println(builder.toString());
                buffer.force();
                buffer.clear();
                builder.delete(0,builder.length());
            }



//            Thread.sleep(15000);
            while (buffer.hasRemaining()) {
                builder.append((char) buffer.get());
            }
            System.out.println(builder.toString());
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
