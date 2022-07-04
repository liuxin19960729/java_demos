package com.liuxin.java.nio.buffer;



public class BufferCustom {
    public static void main(String[] args) {

    }

}
/*
*Buffer的基本属性
*   mark 做标记  init undefine
*   private int  mark = -1;
*   private int position = 0;元素读取位置索引
*   private int limit; 若读取元素 则读取到limit 位置不允许往下读
*   private int capacity; 容量
*
*  o<=mark<=position<=limit<=capacity
* index   0 1  2 3 4 5 6 7
* array   [1,2,3,4,5,6,7,8]
*  capacity=8
*  position=下一次读取的位置
*  limit=读取到什么位置停止
*  mark=call mark() mark=position
*
* buffer 方法
*
* isReadOnly() true 只允许可读
* if(!buf.isReadOnly()){可对缓冲器进行类容的写取 }
*
* 若对只读Buffer进行修改throwReadOnlyBufferException
*
*
*    public final Buffer flip() {
        limit = position;
        position = 0;
        mark = -1;
        return this;
    }
*   //倒回
*    public final Buffer rewind() {
        position = 0;
        mark = -1;
        return this;
    }
    *
    public final boolean hasRemaining() {
        return position < limit;
    }
* public final int remaining() {
        return limit - position;
    }

    清除所有数据
    public final Buffer clear() {
        position = 0;
        limit = capacity;
        mark = -1;
        return this;
    }
    *
    *
    *
    *
    * mark() 作用
    * 在reset() 里面使用
    * //将position重置到上一次mark的位置
    * public final Buffer reset() {
        int m = mark;
        if (m < 0)
            throw new InvalidMarkException();
        position = m;
        return this;
    }
    *
**/
