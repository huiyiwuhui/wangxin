package com.wangxin.app.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administra on 2016/9/19.
 */
@Service
public class HelloServiceClient{

//    @Reference(interfaceClass=com.wangxin.app.service.HelloService.class)
    @Autowired
    private HelloService helloService;

    public void sayHello() {
        helloService.sayHello();
    }

    public void sayHello(String name) {
        helloService.sayHello(name);
    }

    public List<String> getList(){
        return helloService.getList();
    }
}
