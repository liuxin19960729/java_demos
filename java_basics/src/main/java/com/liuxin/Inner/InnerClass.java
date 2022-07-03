package com.liuxin.Inner;

public class InnerClass {
    private int innera = 100;

    public static void main(String[] args) {

        /**
         *  InnerClazz innerClazz = new InnerClazz();
         *  直接这样在静态方法里面instance InnerClazz class
         *  不允许 staic 不能直接访问this(隐式)
         */

        /**
         * 正确方式
         */

        InnerClass innerClass = new InnerClass();
        //创建内部类
        //创建的时候会隐式的传入 innerClass 到类里面
        InnerClazz innerClazz = innerClass.new InnerClazz();

        innerClazz.cc();

    }

    private static void out() {
        System.out.println("out");
    }

    private class InnerClazz {
        int a = 100;
        final static int c = 2;//要想在内部类里面使用静态必须声明为常量字段

        void cc() {

            a = 200;
            System.out.println(a);
           /*
           * final com.liuxin.Inner.InnerClass this$0;类似于生成的这个字段存储外部类对象
           *
           *  编译器回把内部类的构造器编译成
           *  com.liuxin.Inner.InnerClass$InnerClazz(com.liuxin.Inner.InnerClass, com.liuxin.Inner.InnerClass$1);

            * */

            System.out.println(innera);//隐式调用外部对象 访问 字段
            System.out.println(InnerClass.this.innera);//显示调用外部对象 访问 字段

            /*
            *为什么内类允许访问外部对象但是静态字段
            *
        static int access$100(com.liuxin.Inner.InnerClass);
             Code:
               0: aload_0
               1: getfield      #1                  // Field innera:I
               4: ireturn

*
*   编译编译的结果 访问 外部内私有方法
* 编译构建一个静态方法  在
            * */

            out();
            InnerClass.out();
        }
        /*
         * 内部类不允许静态方法
         * */
//        final static int dd(){
//
//            return 100;
//        }
    }
}
