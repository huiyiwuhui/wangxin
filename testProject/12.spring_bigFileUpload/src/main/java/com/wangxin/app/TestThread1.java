package com.wangxin.app;

/**
 * Created by Administra on 2016/11/8.
 */
public class TestThread1 extends Thread {

    public void  run(){
        DeadlockRisk a = new DeadlockRisk();
        System.out.println(a.read());
    }

}
