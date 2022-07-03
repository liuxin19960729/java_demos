package com.liuxin.Inner;

public class StaticClass {
    public static void main(String[] args) {
        test1();
    }

    static void test1() {
        OutSideClass outSideClass = new OutSideClass();
        outSideClass.aa();
    }


}

/**
 * 外部类
 * 内部静态内在内部 instance
 *    所有权限符字段和方法都允许访问
 *
 * 外部类能够访问内部类所有权限静态方法和类
 *
 */

class OutSideClass {
    private static class InnerStaticClass {
        private int a = 100;
        private static int b = 200;

        InnerStaticClass() {
            System.out.println("InnerStaticClass");

        }
    }

    void aa() {
        InnerStaticClass innerStaticClass = new InnerStaticClass();
        System.out.println(innerStaticClass.a);
        System.out.println(InnerStaticClass.b);
    }
}
