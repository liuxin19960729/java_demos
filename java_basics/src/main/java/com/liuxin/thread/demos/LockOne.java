package com.liuxin.thread.demos;

public class LockOne implements ILock {
    private boolean[] flag = new boolean[2];

    public void lock() {
        int i = ThreadID.getID();
        int j = 1 - i;
        flag[i] = true;
        while (flag[j]) {//可能存在死锁情况
        }
    }

    public void unlock() {
        int i = ThreadID.getID();
        flag[i] = false;
    }
}
