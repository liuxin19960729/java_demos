package com.liuxin.java.nio.channel;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileChannelCus {
    public static void main(String[] args) throws IOException {
        String rootPath = FileChannelCus.class.getClassLoader().getResource(".").getPath();
//        String packagePath = FileChannelCus.class.getPackage().getName().replaceAll("\\.", File.separator);
        String filename = "file";
        String completePath = rootPath  + File.separator + filename;
        RandomAccessFile accessFile = new RandomAccessFile(completePath, "r");
        FileChannel channel = accessFile.getChannel();



    }
}