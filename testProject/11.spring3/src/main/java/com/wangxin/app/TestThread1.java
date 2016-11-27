package com.wangxin.app;

import com.wangxin.app.util.http.HttpClientUtil;
import com.wangxin.app.util.http.HttpClientUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.util.Random;

/**
 * Created by Administra on 2016/11/8.
 */
public class TestThread1 extends Thread {

    public void  run(){
        StopWatch watch = new StopWatch();
        // 计时器开始
        watch.start();
        String str = HttpClientUtil.httpGet("http://172.16.60.91:8013/yhapi/restful/account/queryaccostate", null);
        watch.stop();
        System.out.println( watch.getTime()+Thread.currentThread().getName());
    }


    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            TestThread1 t1 = new TestThread1();
            t1.start();
        }
//        System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
