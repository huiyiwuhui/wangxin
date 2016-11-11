package com.wangxin.app.constroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administra on 2016/11/8.
 */
@Controller
public class HeaderController {

    @RequestMapping("testHeader")
    public void test(HttpServletRequest request){

        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println("key:"+key+"     value:"+value);
        }

    }
}
