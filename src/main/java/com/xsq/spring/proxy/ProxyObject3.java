package com.xsq.spring.proxy;

/**
 * spring aop������
 */
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;


public class ProxyObject3 implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		long start = System.currentTimeMillis();
		Object[] args = invocation.getArguments();
		if ("ѧϰ".equals(args[0])) {
			return "��Ȩ��";
		}
		Object invoke = invocation.proceed();
		//Object invoke = arg3.invoke(target, arg2);
		System.out.println(System.currentTimeMillis() - start);
		return invoke;
	}

}
