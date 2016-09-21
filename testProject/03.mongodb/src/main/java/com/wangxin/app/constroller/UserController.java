package com.wangxin.app.constroller;

import com.wangxin.app.JsonMapper;
import com.wangxin.app.bean.User;
import com.wangxin.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 王鑫 on 2016/8/23.
 */
@Controller
@RequestMapping("/user")
public class UserController {



    @Autowired
    private UserService userService;



    @RequestMapping("/test")
    public void test( HttpServletRequest request, HttpServletResponse response){
        System.out.println("test----------------");
    }


    @RequestMapping("/save")
    public void saveUser(User user,HttpServletRequest request,HttpServletResponse response){
        userService.saveUser(user);
    }

    @RequestMapping("/find")
    public void findUser(User user,HttpServletRequest request,HttpServletResponse response){
       List<User> users = userService.findUserByName(user.getName());
        JsonMapper jsonMapper = JsonMapper.buildNonDefaultMapper();
        System.out.println(jsonMapper.toJson(users));
    }

}
