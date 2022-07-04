package com.liuxin.java.nio.buffer;

import java.nio.CharBuffer;

public class DuplicateBuffer {
    public static void main(String[] args) {
        CharBuffer allocate = CharBuffer.allocate(100);
        CharBuffer charBuffer = allocate.slice();
    }
}

/*
* CharBuffer allocate = CharBuffer.allocate(100);
*  allocate.duplicate()
*
* imp: 创建一个数组公用(array share)的新缓冲区
*     public CharBuffer duplicate() {
        return new HeapCharBuffer(hb,
                                        this.markValue(),
                                        this.position(),
                                        this.limit(),
                                        this.capacity(),
                                        offset);
    }
*
*
* 只读缓冲区(共享数组)
* CharBuffer charBuffer = allocate.asReadOnlyBuffer();
*
*     public CharBuffer asReadOnlyBuffer() {

        return new HeapCharBufferR(hb,
                                     this.markValue(),
                                     this.position(),
                                     this.limit(),
                                     this.capacity(),
                                     offset);



    }
*
*   截取一段 未读取的数据 [position,limit) 数组共享
*   CharBuffer charBuffer = allocate.slice();
*
*
    public CharBuffer slice() {
        return new HeapCharBuffer(hb,
                                        -1,//mark
                                        0,//position
                                        this.remaining(),//limit
                                        this.remaining(),//limit
                                        this.position() + offset);
    }
*
* */
