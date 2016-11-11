package com.wangxin.app;

/**
 * Created by Administra on 2016/11/8.
 */
public class TestThread2 implements Runnable {
    @Override
    public void run() {
        DeadlockRisk a = new DeadlockRisk();
        a.write(1,2);
        System.out.println(a.read());
    }
}
