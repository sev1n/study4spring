package com.xsq.spring.proxy;

/**
 * cglib代理
 */
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyObject2 implements MethodInterceptor{

	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
		System.out.println("当前时间：" + LocalDateTime.now());
		Object invokeSuper = arg3.invoke(arg0, arg2);
		System.out.println("当前时间：" + LocalDateTime.now());
		return invokeSuper;
	}

}
