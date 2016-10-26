package com.wangxin.app.controller;

import com.wangxin.app.service.HelloService;
import com.wangxin.app.service.HelloServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HelloController {

	@Autowired
	private HelloServiceClient helloServiceClient;
	
	@RequestMapping("/hello1")
	public void hello1(HttpServletRequest request, HttpServletResponse resp){
		helloServiceClient.sayHello();
	}

	@RequestMapping("/hello2")
	public void hello2(HttpServletRequest request, HttpServletResponse resp){
		helloServiceClient.sayHello("qqq");
	}

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse resp){
		List<String> list = helloServiceClient.getList();
		for (String s : list) {
			System.out.println(s);
		}
	}



}