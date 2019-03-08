package com.xsq.spring.service.impl;

import java.util.function.Consumer;

public class BaseService {

	public String doBase(){
/*		Consumer<String> c = System.out::println;
		c.accept("xieshiqi");*/
		int a = 1, b =1;
		System.out.println(a + b);
		return "xieshiqi";
	}
}
