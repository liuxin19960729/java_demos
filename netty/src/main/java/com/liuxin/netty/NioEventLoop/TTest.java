package com.liuxin.netty.NioEventLoop;

public class TTest {
    public static void main(String[] args) {
        ZZ<CCC<CCC>> objectZZ = new ZZ<>();
        a(objectZZ);
    }

    static void a(ZZ<? extends CCC<? super GG>> a) {

    }

}


class CCC<T> {

}

class GG<T> extends CCC<T> {

}

class ZZ<T> {

}
