package com.wangxin.app.service;


import java.util.List;

/**
 * Created by Administra on 2016/9/19.
 */
public interface HelloService {
    void sayHello();
    void sayHello(String name);

    List<String> getList();
}
