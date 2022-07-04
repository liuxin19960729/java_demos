package com.liuxin.java.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

public class ByteBufferArea {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(100);
//        allocate.asIntBuffer()

    }
}

/*
* memory address left--->right 增加
*例
* int 类型  0x03 7f b4 c7  --------> big endian (高位低地址上)
* 内存存储   c7 b4 7f 03  low--->high address (small endian )
*
* IP协议:规定网络协议采用大端
*
*
* class ByteOrder
*    public static final ByteOrder BIG_ENDIAN
*        = new ByteOrder("BIG_ENDIAN");
*     public static final ByteOrder LITTLE_ENDIAN
*       = new ByteOrder("LITTLE_ENDIAN");
*     public static ByteOrder nativeOrder() {//将返回ByteOrde big endian or small endian中的一个 系统固有硬件字节顺序
        return Bits.byteOrder();
    }
*
*   String s = ByteOrder.nativeOrder().toString();JVM 运行的硬件平台固有的字节顺序
*        ByteBuffer allocate = ByteBuffer.allocate(1000);
        System.out.println(allocate.order().toString()); 查询字节顺序 default BIG_ENDIAN
*
*    CharBuffer allocate = CharBuffer.allocate(100);
        System.out.println(allocate.order().toString()); LITTLE_ENDIAN
*  注意:除了ByteBuffer,其他通过分配和包装的类都于ByteOrder.nativeOrder()相同
*
* 无论系统硬件的字节顺序是什么,ByteBuffer总是BIG_ENDIAN,
* java 的默认字节(byte)BIG_ENDIAN,若有些平台byte的默认字节顺序是小端这会造成很大的性能影响,解决方法:将byte当做其他类型存储
*
*
* 设置ByteBuffer 的字节顺序
* byteBuffer.order(ByteOrder.BIG_ENDIAN)
*
* */
