package com.liuxin.java.nio.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CloseChannel {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("");
        FileChannel channel = inputStream.getChannel();
        channel.isOpen();
    }
}

/*
*
*  inputStream.getChannel().close();
*
*  关闭底层IO服务的过程中线程会占时阻塞(即使 no blocking 模式)
*
*   thread1--------------------->close success
*   thread2--------------------| 之前call close()都会阻塞
*   当一个channel 已经关闭再次调用不会有任何操作立即返回
*     public final void close() throws IOException {
        synchronized (closeLock) {
            if (!open)
                return;
            open = false;
            implCloseChannel();
        }
    }
*
*  channel.isOpen(); 测试开放状态
*
*
* */