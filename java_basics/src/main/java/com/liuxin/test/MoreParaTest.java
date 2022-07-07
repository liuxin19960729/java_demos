package com.liuxin.test;

public class MoreParaTest {
    public static void main(String[] args) {
        aa(1,2,3);
    }

    static void aa(int... a) {
        bb(a);
    }

    static void bb(int... b) {
        for (int i : b) {
            System.out.println(i);
        }
    }
}
