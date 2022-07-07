package com.liuxin.java.nio.charset;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class CharsetCus1 {
    public static void main(String[] args) {
        Charset charset = Charset.defaultCharset();
//        charset.canEncode() 是否允许编码

        System.out.println(charset.name());//规范名称
        System.out.println(charset.displayName());//def 规范名称 、可以设置一个本地名称
//        System.out.println(charset.aliases());
        /*
        * 返回支持的所有字符集的实力
        * */
//        Collection<Charset> values = Charset.availableCharsets().values();
//        System.out.println(values);
//        Charset.availableCharsets() 虚拟机所有支持的字符集
//        Charset.isSupported() 当前字符集在jvm支持


//        ByteBuffer encode = charset.encode("");
//        ByteBuffer encode1 = charset.encode();

    }
}
