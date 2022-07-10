package com.liuxin.thread.demos;

public class Counter {
    public static void main(String[] args) {
        Counter counter = new Counter();
        counter.getAndIncrement();
    }

    private final ILock lock=new LockOne();
    private long value;

    public long getAndIncrement() {
        try {
            //设置临界区互斥
            lock.lock();
            value += 1;
            return value;
        } finally {
            lock.unlock();
        }
    }
}

