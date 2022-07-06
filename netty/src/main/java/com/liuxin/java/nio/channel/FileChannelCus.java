package com.liuxin.java.nio.channel;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Objects;

public class FileChannelCus {
    public static void main(String[] args) throws IOException {
        String rootPath = FileChannelCus.class.getClassLoader().getResource(".").getPath();
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
        FileLock lock = channel.tryLock();
        if (Objects.nonNull(lock)) {
            System.out.println(lock.isShared());
            System.out.println("vaild: " + lock.isValid());
            System.out.println(lock.position());
            System.out.println(lock.size());
            System.out.println(lock.overlaps(100, 200));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.release();
                channel.close();
                accessFile.close();
                System.out.println("释放");
            }
        } else {
            System.out.println("not lock");
        }


    }
}