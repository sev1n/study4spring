package com.xsq.spring.service.impl;

import java.util.Arrays;

import com.xsq.spring.service.DefaultMethodService;

public class TestDefaultMethod {

	public static void main(String[] args) {
		DefaultMethodService[] dm = {new DefaultMethodServiceImpl1(), new DefaultMethodServiceImpl2()};
		for (DefaultMethodService defaultMethodService : dm) {
			defaultMethodService.print();
			defaultMethodService.print("sevin");
		}
	}
}
