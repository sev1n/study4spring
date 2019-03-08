package com.xsq.spring.proxy;

/**
 * cglib代理
 */
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyObject implements MethodInterceptor {
	
	private Object target;

	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
		System.out.println("前置输出");
		Object invoke = arg3.invokeSuper(arg0, arg2);
		//Object invoke = arg3.invoke(target, arg2);
		System.out.println("后置输出");
		return invoke;
	}

	public ProxyObject(Object target) {
		super();
		this.target = target;
	}

}
