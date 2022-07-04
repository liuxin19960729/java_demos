package com.liuxin.java.nio.buffer;

import java.nio.CharBuffer;

public class CreateBuffer {
    public static void main(String[] args) {
        CharBuffer allocate = CharBuffer.allocate(100);
//        allocate.array()
//        CharBuffer.wrap()
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
 *
 *
 *
 *
 * */