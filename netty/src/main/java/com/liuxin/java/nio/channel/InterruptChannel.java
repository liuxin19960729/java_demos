package com.liuxin.java.nio.channel;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class InterruotChannel {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
//        Thread.interrupted();
        System.out.println(Thread.currentThread().isInterrupted());
        
    }
}
