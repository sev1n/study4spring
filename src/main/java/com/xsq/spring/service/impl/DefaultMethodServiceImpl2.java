package com.xsq.spring.service.impl;

import com.xsq.spring.service.DefaultMethodService;

public class DefaultMethodServiceImpl2 implements DefaultMethodService {

	@Override
	public void print() {
		System.out.println(this.getClass().getName());
	}

	public void print(String name){
		System.out.println("hello, i am " + name);
	}
}
