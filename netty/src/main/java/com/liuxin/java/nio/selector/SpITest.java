package com.liuxin.java.nio.selector;

import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;

public class SpITest {
    public static void main(String[] args) {
        String provider = SPI_P.provider();
        System.out.println(provider);
    }
}


class SPI_P {
    private static String provider = null;
    private static Object lock = new Object();

     static String provider() {
        synchronized (lock) {
            if (Objects.nonNull(provider)) return provider;

            return AccessController.doPrivileged(new PrivilegedAction<String>(){
                @Override
                public String run() {
                    return "liuxin";
                }
            });
        }
    }
}
