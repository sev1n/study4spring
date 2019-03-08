package com.xsq.spring.proxy;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;


public class ProxyObject4 implements MethodBeforeAdvice {
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println(LocalDateTime.now());
	}

}
