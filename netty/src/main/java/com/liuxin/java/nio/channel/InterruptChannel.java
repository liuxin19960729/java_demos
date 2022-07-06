package com.liuxin.java.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;


public class InterruptChannel {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
//        Thread.interrupted();
        System.out.println(Thread.currentThread().isInterrupted());
        ReadableByteChannel readableByteChannel = Channels.newChannel(System.in);
        WritableByteChannel writableByteChannel = Channels.newChannel(System.out);
        ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
        buffer.clear();
        readableByteChannel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            writableByteChannel.write(buffer);
        }
        buffer.clear();
        readableByteChannel.close();
        writableByteChannel.close();
    }
}

/*

interface InterruptibleChannel

关闭 和中断的行为引入

阻塞 -->另一个线程 blockThread.interrupt() --throw CloseByInterruptException

线程1--------(向线程2.interrupt())
线程2----------------------------------->read()| write()... throw CloseByInterruptException

在执行之前 check current.isInterrupt() and Thread.interrupt() 清除当前Interrupt status




 *note 当一个线程 interrupt status 未 true
 *
 *对channel 进行操作 throw  ClosedByInterruptException
 *
 * 解决方法  Thread.interrupted(); 清除当前线程 interrupt 状态
 *1.如果一个通道上线程被阻塞
2.另一个线程 blockThread.interrupt()
3.通道关闭 通道产生ClosedByInterruptException
 *
 * */

