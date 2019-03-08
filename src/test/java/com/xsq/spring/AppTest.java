package com.xsq.spring;

import org.junit.Test;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.xsq.spring.service.HelloService;

public class AppTest {

	@Test
	public void test(){
		XmlWebApplicationContext content = new XmlWebApplicationContext();
		HelloService service = (HelloService) content.getBean("helloService");
		service.say();
	}
}
