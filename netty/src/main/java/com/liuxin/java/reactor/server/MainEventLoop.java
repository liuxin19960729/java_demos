package com.liuxin.java.reactor.server;

import com.liuxin.java.reactor.commom.NioTaskTask;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class MainEventLoop implements Runnable {
    private Selector selector = null;
    private final int THREAD_NUM = 1 << 2;
    private SubEventLoop[] threads = new SubEventLoop[THREAD_NUM];
    private ExecutorService businessExecutor = null;
    private int id = 0;
    private final AtomicInteger reqCounter = new AtomicInteger(0);

    public MainEventLoop(ServerSocketChannel ssc) throws IOException {
        businessExecutor = Executors.newFixedThreadPool(THREAD_NUM, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("bussiness-main-" + (++id));
                return thread;
            }
        });
        selector = Selector.open();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new SubEventLoop(businessExecutor, "sub-eventLoop-" + i);
            threads[i].start();
        }
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("main eventLoop construct");
    }

    @Override
    public void run() {
        System.out.println("maineventLoop run");
        while (!Thread.interrupted()) {
            try {
                int select = selector.select();
                if (select == 0) continue;
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = channel.accept();
                        socketChannel.configureBlocking(false);
                        reqCounter.incrementAndGet();
                        dispater(socketChannel);
                    }
                    iterator.remove();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispater(SocketChannel socketChannel) {
        next().addTask(new NioTaskTask(socketChannel, SelectionKey.OP_READ));
    }

    private SubEventLoop next() {
        return threads[(reqCounter.intValue() - 1) & (THREAD_NUM - 1)];
    }
}
