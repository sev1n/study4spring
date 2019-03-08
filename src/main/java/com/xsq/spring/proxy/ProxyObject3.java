package com.xsq.spring.proxy;

/**
 * spring aop代理类
 */
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;


public class ProxyObject3 implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		Object[] args = invocation.getArguments();
		if ("学习".equals(args[0])) {
			return "无权限";
		}
		Object invoke = invocation.proceed();
		//Object invoke = arg3.invoke(target, arg2);
		System.out.println(System.currentTimeMillis() - start);
		return invoke;
	}

}
