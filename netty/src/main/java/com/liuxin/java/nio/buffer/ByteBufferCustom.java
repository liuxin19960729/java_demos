package com.liuxin.java.nio.buffer;

public class ByteBufferCustom {
    public static void main(String[] args) {

    }
}

/*
*方法
*get() put()
*
*
* public abstract byte get(); 1.读取  2. position+1
* public abstract byte get(index i) 精确读取 postion 不修改
*
* public abstract ByteBuffer put(int index, byte b); 指定位置写入数据
* public abstract ByteBuffer put(byte b); 写入 position+1
* public ByteBuffer put(ByteBuffer src) 将src 里面的数据放在里面 note throws except
*    note:put((byte)'a') ascii 码的存入 必须强制转换(char)
*
*
* public final Buffer flip()  在ByteBuffer实现
* limit = last postion
* position=0
*
*
* public final Buffer rewind()  倒回
*   position=0;
*   mark =-1;
*
*
* limit 和 postion 之间关系
* public final boolean hasRemaining limit 和 position 之间是否还有元素
* public final int remaining() limit 和postion 之间还有几个元素
*
* 清除所有数据
* public final Buffer clear()
*   position=0;
*   limit=capacity
*   mark=-1
*
*
* compact() 丢弃position 前面的数据 现在的数据向前面移动
* public abstract ByteBuffer compact();
* [position,limit) 所有的数据放到[0,limit-position]的位置
* position 移动到index:(limit-position)
* limit=capacity
*  实现代码:
*      public ByteBuffer compact() {

        System.arraycopy(hb, ix(position()), hb, ix(0), remaining());//copy array
        position(remaining());position limit-position
        limit(capacity());limit=capacity
        discardMark();mark=-1
        return this;
    }
*
*
*  mark() 作用
 * 在reset() 里面使用 重置到上次调用mark的位置，若从未mark抛出异常 InvalidMarkException
*
*

* buf.equal(buf2) 比较两个缓冲区是否一样
* 理buf1 和 buf class 一样
* buf.remaining()==buf2.remaining()=true
* 和索引无关
* position->limit 之间元素必须相同
*
* 实现
*     public boolean equals(Object ob) {
        if (this == ob)// 同一对象
            return true;
        if (!(ob instanceof ByteBuffer)) ob Class 必须是 ByteBuffer 或 子类
            return false;
        ByteBuffer that = (ByteBuffer)ob;
        if (this.remaining() != that.remaining())//剩余个数必须相等
            return false;
        int p = this.position();
        for (int i = this.limit() - 1, j = that.limit() - 1; i >= p; i--, j--)//从后往前进行比较
            if (!equals(this.get(i), that.get(j)))
                return false;
        return true;
    }
    private static boolean equals(byte x, byte y) {



        return x == y;

    }
    *
    总结: ByteBuffer Equal() 比较的就是position -limit(可读数据)  是否相等
    *
    *
    *
*
*
* ByteBhffer implement<ByteBuffer>{
*     public int compareTo(ByteBuffer that) {
        int n = this.position() + Math.min(this.remaining(), that.remaining());
        for (int i = this.position(), j = that.position(); i < n; i++, j++) {
            int cmp = compare(this.get(i), that.get(j));
            if (cmp != 0)// 比较第一个不同的元素并且将相差值返回
                return cmp;
        }
        * //全部相等比较那元素个数多
        return this.remaining() - that.remaining();
    }

    private static int compare(byte x, byte y) {






        return Byte.compare(x, y);

    }
* }
*
*
* */