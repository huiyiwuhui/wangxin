package com.wangxin.app.controller;

import com.wangxin.app.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@Autowired
	private HelloService helloService;
	
	@RequestMapping("/hello1")
	public void hello1(Model model){
		helloService.sayHello();
	}

	@RequestMapping("/hello2")
	public void hello2(Model model){
		helloService.sayHello("qqq");
	}

}