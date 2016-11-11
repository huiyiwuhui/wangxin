//package com.wangxin.app;
//
//import org.apache.commons.lang.time.StopWatch;
//
///**
// * Created by Administra on 2016/11/8.
// */
//public class TestThread1 extends Thread {
//
//    public void  run(){
//        String url = "http://localhost:8084/upload/test";
//        StopWatch watch = new StopWatch();
//        // 计时器开始
//        watch.start();
//        String str = HttpUtil.httpGet(url);
//        watch.stop();
//        System.out.println( watch.getTime()+Thread.currentThread().getName());
//    }
//
//}
