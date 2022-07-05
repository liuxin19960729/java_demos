package com.liuxin.java.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class CreateSelector {
    private static Object lock = new Object();
    private static Selector selector = null;

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();//
        selector = Selector.open();// 静态实例化Selector对象 SPI 发送请求
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                synchronized (lock){
                    System.out.println("close执行");
                    selector.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_CONNECT);
        boolean isConnect = channel.connect(new InetSocketAddress("103.235.46.40", 80));
        while (true) {
            System.out.println("start");
            int select = selector.select();// ret value 有几个key被添加到seletedKeys集合

            System.out.println("select num:" + select);
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                System.out.println(key.interestOps());
                synchronized (lock){
                    if (selector.isOpen() && key.isValid() && key.isConnectable()) {
                        isConnect = channel.finishConnect();
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_CONNECT);
                        if (isConnect) {
                            iterator.remove();//remove lastRet
                        }
                    }

                }
            }
        }
    }
}
