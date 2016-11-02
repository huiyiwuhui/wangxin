package com.wangxin.app.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * XML配置型
 * Created by Administra on 2016/9/19.
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService{
    public void sayHello(){
        System.out.println("hello");
    }
    public void sayHello(String name){
        System.out.println(name+"    hello");
    }

    @Override
    public List<String> getList() {
        List<String> list = new ArrayList<String>();
        list.add("wang");
        list.add("xin");
        list.add("你好");
        return list;
    }
}
