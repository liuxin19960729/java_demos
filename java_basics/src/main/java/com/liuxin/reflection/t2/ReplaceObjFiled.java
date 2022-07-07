package com.liuxin.reflection.t2;

import java.lang.reflect.Field;

/**
 * 替换private 里面的字段
 */
public class ReplaceObjFiled {
    public static void main(String[] args) {
        Rpfild rpfild = new Rpfild();
        System.out.println(rpfild.getZz());
        Class<Rpfild> rpfildClass = Rpfild.class;
        try {
            Field zz = rpfildClass.getDeclaredField("zz");
            zz.setAccessible(true);
            zz.set(rpfild, "sex:男");
            System.out.println(rpfild.getZz());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class Rpfild {
    private String zz = "name";

    public String getZz() {
        return zz;
    }
}
