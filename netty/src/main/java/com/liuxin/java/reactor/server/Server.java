package com.liuxin.java.reactor.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        new Thread(new ServerBoot()).start();
    }

    private static class ServerBoot implements Runnable {

        private ServerSocketChannel ssc = null;
        private Selector selector = null;
        private ExecutorService executorService = null;


        @Override
        public void run() {
            try {
                executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
                    private final AtomicInteger vvv = new AtomicInteger(0);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("main-tread-" + vvv.incrementAndGet());
                        return thread;
                    }
                });
                ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                ssc.bind(new InetSocketAddress(PORT));
                dispatch(ssc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private final void dispatch(ServerSocketChannel ssc) throws IOException {
            executorService.submit(new MainEventLoop(ssc));
        }
    }

}
