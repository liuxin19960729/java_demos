package com.liuxin.test;

public class MoreParaTest2 {
    public static void main(String[] args) {
        aa(3, 4, 5, 6);
    }

    static void aa(Object... a) {
            bb(a);
    }

    static void bb(Object... a) {
        for (Object o : a) {
            System.out.println(o);
        }
    }
}
