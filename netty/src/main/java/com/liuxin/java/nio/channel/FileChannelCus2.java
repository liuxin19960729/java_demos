package com.liuxin.java.nio.channel;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileChannelCus2 {
    public static void main(String[] args) throws IOException {
        String rootPath = FileChannelCus2.class.getClassLoader().getResource(".").getPath();
//        String packagePath = FileChannelCus.class.getPackage().getName().replaceAll("\\.", File.separator);
        String filename = "file";
        String completePath = rootPath + filename;
        System.out.println(completePath);
        RandomAccessFile accessFile = new RandomAccessFile(completePath, "rw");
        FileChannel channel = accessFile.getChannel();
//        Thread thread = new Thread(() -> {
//            try {
////                channel.close();
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
////            catch (IOException e) {
////                e.printStackTrace();
////            }
//        });
//        thread.start();
        FileLock lock = channel.lock();
        System.out.println(lock.isShared());
        System.out.println("vaild: " + lock.isValid());
        System.out.println(lock.position());
        System.out.println(lock.size());
        System.out.println(lock.overlaps(100, 200));

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }
}