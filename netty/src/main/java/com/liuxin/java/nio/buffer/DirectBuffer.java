package com.liuxin.java.nio.buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.LongBuffer;

public class DirectBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        boolean direct = buffer.isDirect();
        System.out.println(direct);
        LongBuffer longBuffer = buffer.asLongBuffer();

    }
}

/*
*非直接缓存: arry 在jvm是个数组,位置可能不在连续的区域,随着GC位置家进行移动
*
*
*直接缓冲区:堆(jdm)外内存 特点 buffer连续
*
* 像IO中使用非DirectBuffer
* 1 创建一个临时直接 buffer
* 2.非直接buffer拷贝到 零时直接
* 3.临时直接缓冲区执行地执行底层IO操作
* 4.零时直接缓冲区离开IO执行方法,并且最终成为回收的无用数据
*
*直接缓冲区缺点
*  创建对象比非直接花费要高
*
*
*直接缓存的创建
*ByteBuffer buffer = ByteBuffer.allocateDirect(10);
*
*  buffer 继承过来的  isDirect()  --每个都有一个这个方法
* boolean direct = buffer.isDirect();
* System.out.println(direct);
*
*
* ByteBuffer 转换为其他类型视图
*    asLongBuffer() asIntBuffer()....
*
*
* 数据元素视图
*    buffer.getChar()
*
*
* */