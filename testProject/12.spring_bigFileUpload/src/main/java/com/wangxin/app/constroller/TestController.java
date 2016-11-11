package com.wangxin.app.constroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administra on 2016/11/9.
 */
@Controller
public class TestController {

    @RequestMapping("test")
    public  void  test(HttpServletResponse  response){
        System.out.println("11111111");
    }

}
