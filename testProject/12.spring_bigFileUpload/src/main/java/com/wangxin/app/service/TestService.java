package com.wangxin.app.service;

/**
 * Created by Administra on 2016/11/8.
 */
public class TestService {


    public static void main(String[] args) {

        Thread thread = new Thread("t1")
        {
            @Override
            public void run()
            {
                System.out.println("hello");
                // TODO Auto-generated method stub
                System.out.println(Thread.currentThread().getName());
            }
        };
        thread.run();
        thread.start();
    }
}
