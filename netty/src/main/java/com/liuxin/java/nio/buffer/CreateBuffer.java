package com.liuxin.java.nio.buffer;

import java.nio.CharBuffer;

public class CreateBuffer {
    public static void main(String[] args) {
        CharBuffer allocate = CharBuffer.allocate(100);




    }
}

/*
 *buffer 创建一种char类型  对  int short byte long float double 都适用
 *CharBuffer allocate = CharBuffer.allocate(100); 私有 array
 *
 *CharBuffer.wrap(char[] arr) 使用所提供的来存入缓存区
 *CharBuffer wrap(char[] array, int offset, int length)
 *
 * limit=offset+length
 * pos=offset
 * note: clean or flip or rewind(倒带 pos=0,mark =-1 像磁带进行倒带 标记和 位置恢复到以前)
 *
 *
 *allocate() or wrap() 创建的通道 缓冲区都是间接的
 *间接换缓冲区使用备份数组
 *
 * hasArray() 1.具有可写权限&& 数组 ！=null
 *  public final boolean hasArray() {
        return (hb != null) && !isReadOnly;
    }
 *
 * 返回存储数组的引用
 * char[] array = allocate.array();
 *
 * int i = allocate.arrayOffset();返回 offset字段 wrap() 是传入 的偏移量  allocate offset=0
 *
 *
 * */