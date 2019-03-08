package com.xsq.spring.proxy;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;

public class AfterProxyObject implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("方法开始执行");	
		Object proceed = invocation.proceed();
		System.out.println("AfterProxyObject.returnVal : " + proceed);
		return proceed; 
	}

}
