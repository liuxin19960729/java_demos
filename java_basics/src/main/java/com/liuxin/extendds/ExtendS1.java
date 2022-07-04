package com.liuxin.extendds;

public class ExtendS1 extends AAA{
    public static void main(String[] args) {
        ExtendS1 aaa = new ExtendS1();
        aaa.b();
    }

    @Override
    void a() {
        System.out.println("ExtendS1.a()");
    }
}


class AAA{
    void a(){
        System.out.println("AAA.a()");
    }

    void b(){
         a();
    }
}
