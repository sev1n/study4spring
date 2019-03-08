package com.xsq.spring.service.impl;

import com.xsq.spring.service.DefaultMethodService;

public class DefaultMethodServiceImpl1 implements DefaultMethodService {

	@Override
	public void print() {
		System.out.println(this.getClass().getName());
	}

}
