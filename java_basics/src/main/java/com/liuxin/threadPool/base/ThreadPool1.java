package com.liuxin.threadPool.base;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool1 {
    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2,
                0, TimeUnit.MINUTES, blockingQueue, new ThreadFactory() {
            private final AtomicInteger atomicInteger = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("excutor-index: " + atomicInteger.incrementAndGet());
                System.out.println(thread.getName());
                return thread;
            }
        }) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("before: " + t.getName());
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("end: ");
                super.afterExecute(r, t);
            }

            @Override
            protected void terminated() {
                System.out.println("terminated" + getTaskCount());

                super.terminated();
            }
        };


        threadPoolExecutor.execute(() -> {
            System.out.println("ss");
        });

        Future<String> submit = threadPoolExecutor.submit(() -> {

            return "ss";
        });


        System.out.println(Runtime.getRuntime().availableProcessors());

        threadPoolExecutor.shutdown();
    }
}
