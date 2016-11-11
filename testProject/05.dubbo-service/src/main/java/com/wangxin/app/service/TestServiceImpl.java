package com.wangxin.app.service;

import com.alibaba.dubbo.config.annotation.Service;

/**
 *
 * 注解型
 * Created by Administra on 2016/10/27.
 */
@Service(version = "1.0.0")
public class TestServiceImpl implements TestService {

    public void test1(){
        System.out.println("---------test1");
    }
}
