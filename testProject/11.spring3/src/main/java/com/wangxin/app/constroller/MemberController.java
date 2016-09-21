package com.wangxin.app.constroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
public class MemberController{



	@RequestMapping("/test2")
	public void  test(HttpServletRequest request, HttpServletResponse resp){
		System.out.println("1111111111");
	}
//
//	@RequestMapping("/test1")
//	public String  test1(HttpServletRequest request, HttpServletResponse resp){
//		System.out.println("1111111111");
//		return "message";
//	}

}