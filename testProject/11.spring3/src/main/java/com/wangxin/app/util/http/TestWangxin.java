package com.wangxin.app.util.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Administra on 2016/11/11.
 */
public class TestWangxin {

    public static void main(String[] args) {
        try {
            String url = "http://127.0.0.1:6001/yhapi/fundinfo/queryFundList.do";
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            HttpResponse res = client.execute(get);
            System.out.println(client);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
