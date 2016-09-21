package com.wangxin.app.service;

import org.springframework.stereotype.Service;

/**
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
//    public String sayHello(String name){
//       return name+"    hello";
//    }
}
