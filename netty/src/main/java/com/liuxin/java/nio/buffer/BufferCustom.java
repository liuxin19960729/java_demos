package com.liuxin.java.nio.buffer;

import java.nio.Buffer;
import java.util.Spliterator;

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
*
*
*
*
*
**/
