package com.liuxin.thread.concept;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.Lock;

public class MarkWorkd2 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runn());
        Thread thread1 = new Thread(new Runn());
        Thread thread2 = new Thread(new Runn());
        Thread thread3 = new Thread(new Runn());
        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();

    }
}

class Runn implements Runnable {
    private Object lock = new Object();

    @Override
    public void run() {
        synchronized (lock) {
            try {
               lock.wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String s = ClassLayout.parseInstance(lock).toPrintable();
            System.out.println(s);
        }
    }
}