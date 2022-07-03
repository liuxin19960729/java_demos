package com.liuxin.exception.runtime;

import java.io.IOException;

public class ThrowNotCatchRuntimeException {
    public static void main(String[] args) {
          catchE();
    }


    static void catchE() {
        try {
            text1(true);
        } catch (RuntimeException e) {
            System.out.println("runtime exception catch");
        } catch (Exception e) {

        }
    }

    static void noCatchE() {
        text1(true);
    }

    static void text1(boolean isException) {
        System.out.println("text1 enter");
        if (isException) {
            text2();
        }
        System.out.println("text1 exit");
    }

    static void text2() {
        throw new RuntimeException();
    }
}
