package com.yhfund.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 使用cglib
@Service
public class BService {

    @Autowired
    private  CService cService;
  
    public void barB(String _msg, int _type) {  
        System.out.println("BServiceImpl.barB(msg:" + _msg + " type:" + _type + ")");  
        if (_type == 1)  
            throw new IllegalArgumentException("测试异常");  
    }  
  
    public void fooB() {  
        System.out.println("BServiceImpl.fooB()");
        cService.fooC();
    }  
  
}