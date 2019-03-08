package com.xsq.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.xsq.spring.controller.FirstController;
import com.xsq.spring.service.HelloService;
import com.xsq.spring.service.UserService;
import com.xsq.spring.service.impl.BaseService;

public class Test {

	public static void main(String[] args) {
		//FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src\\main\\resources\\applicationContext.xml");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		//HelloService bean = context.getBean("helloService", HelloService.class);
		//bean.say();	
		//bean.say("hello");
/*		UserService user = context.getBean("userService", UserService.class);
		user.doWork("����");*/
		BaseService bs = context.getBean("baseService", BaseService.class);
		System.out.println(bs.doBase());
	}
}
