package com.liuxin.java.reactor.server;

import com.liuxin.java.reactor.commom.NioTaskTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class SubEventLoop extends Thread {
    private final ExecutorService businessExecutor;
    private final Selector selector;
    private final Object taskLock = new Object();
    private final LinkedList<NioTaskTask> tasks = new LinkedList();

    public SubEventLoop(ExecutorService businessExecutor, String name) throws IOException {
        super(name);
        this.businessExecutor = businessExecutor;
        selector = Selector.open();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select(1000);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                SocketChannel socketChannel = (SocketChannel) key.channel();
                try {
                    if (key.isReadable()) {
                        System.out.println("接收数据");
                        int ops = key.interestOps() & ~SelectionKey.OP_READ;
                        key.interestOps(ops);
                        System.out.println();
                        disPatche(socketChannel);
                    } else if (key.isWritable()) {
                        System.out.println("传输数据");
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        socketChannel.write(buffer);
                        if (buffer.remaining() == 0) {
                            key.interestOps((key.interestOps() | SelectionKey.OP_READ) & (~SelectionKey.OP_WRITE));
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.out.println("断开连接");
                } finally {
                    iterator.remove();
                }
            }

            // 处理Task 事件
            if (!tasks.isEmpty()) {
                synchronized (taskLock) {
                    NioTaskTask task = tasks.removeFirst();
                    SocketChannel socketChannel = task.getSocketChannel();
                    try {
                        socketChannel.register(selector, task.getOPS());
                    } catch (ClosedChannelException e) {
                        e.printStackTrace();// close
                    }
                }
            }
        }
    }

    private void disPatche(SocketChannel sc) {
        businessExecutor.submit(new Handel(sc, this));
    }

    public void addTask(NioTaskTask task) {
        synchronized (taskLock) {
            tasks.add(task);
        }
    }
}
