package com.liuxin.reflection;

public class IsAssignFrom extends ZZ implements IZZ {
    public static void main(String[] args) {
        IsAssignFrom isAssignFrom = new IsAssignFrom();
        Class<? extends IsAssignFrom> aClass = isAssignFrom.getClass();
        System.out.println(aClass.isAssignableFrom(ZZ.class));

    }
}

interface IZZ {

}

abstract class ZZ {

}