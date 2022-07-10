package com.liuxin.thread.concept;


import org.openjdk.jol.info.ClassLayout;

public class VolatileCus {
    public static void main(String[] args) {
        VolatileCus volatileCus = new VolatileCus();
        String s1 = ClassLayout.parseInstance(volatileCus).toPrintable();
        int[] arr = new int[2];
        System.out.println(s1);
        synchronized (arr) {
            String s = ClassLayout.parseInstance(arr).toPrintable();
            System.out.println(s);
        }

    }
}
