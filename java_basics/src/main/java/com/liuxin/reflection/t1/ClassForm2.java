package com.liuxin.reflection;

public class ClassForm2 {
    public static void main(String[] args) throws ClassNotFoundException {
        //Class<A> aClass = A.class;// 不初始化
        //Class.form 显示指定是否初始化
        Class<?> aClass = Class.forName
                ("com.liuxin.reflection.A",false,ClassLoader.getSystemClassLoader());

    }
}

class A {
    static {
        System.out.println("init");
    }
}