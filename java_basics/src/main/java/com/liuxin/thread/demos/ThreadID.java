package com.liuxin.thread.demos;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadID extends Thread {
    private final int id;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public ThreadID() {
        id = atomicInteger.getAndIncrement();
    }

    public static int getID() {
        ThreadID thread = (ThreadID) Thread.currentThread();
        return thread.id;
    }
}
