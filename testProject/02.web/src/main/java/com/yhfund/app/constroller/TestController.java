package com.yhfund.app.constroller;


import com.yhfund.app.service.BService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/test")
public class TestController {
    private Logger logger = Logger.getLogger(getClass());

//    @Autowired
//    private  BService bService;

    @RequestMapping(value = "/test1")
    public void list(HttpServletRequest request, HttpServletResponse resp) {
        System.out.println("controller");
        System.out.println("1111111111");
//        bService.fooB();
///        bService.fooB();
    }

//
//    @RequestMapping(value = "/json")
//    public void reaJson(@RequestBody String value, HttpServletRequest request, HttpServletResponse resp) {
//        System.out.println(value);
//
//        String json ="{\"sss\":\"www.baidu.comc.cn\"}";
//        System.out.println();
//    }
//


}
