package com.wangxin.app;

/**
 * Created by Administra on 2016/11/8.
 */
public class Test1 {

    public static void main(String[] args) {
        TestThread1 testThread1 = new TestThread1();
        testThread1.start();
        TestThread2 testThread2 = new TestThread2();
        Thread thread1 = new Thread(testThread2);
        thread1.start();
    }
}
