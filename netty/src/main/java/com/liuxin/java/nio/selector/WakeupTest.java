package com.liuxin.java.nio.selector;

import java.io.IOException;
import java.nio.channels.Selector;

public class WakeupTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        Selector selector = Selector.open();
        selector.close();
        Thread other = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("other start");
                selector.wakeup();
                System.out.println("other end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        other.start();
        int i = 0;
        while (true) {
            if (i==0){
                Thread.sleep(5000);
            }
            System.out.println("while: start" + i);
            selector.select();
            System.out.println("while: end" + i);
            i++;
        }
    }
}
