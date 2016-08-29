package com.yhfund.app.service;

import org.springframework.stereotype.Service;

// 使用cglib
@Service
public class CService {
  

    public String fooC() {
        System.out.println("cServiceImpl.fooc()");
        return "wangxin";
    }  
  
}