package com.xsq.spring.proxy;

/**
 * cglib����
 */
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyObject implements MethodInterceptor {
	
	private Object target;

	public Object intercept(Object arg0, Method arg1, Object[] arg2, MethodProxy arg3) throws Throwable {
		System.out.println("ǰ�����");
		Object invoke = arg3.invokeSuper(arg0, arg2);
		//Object invoke = arg3.invoke(target, arg2);
		System.out.println("�������");
		return invoke;
	}

	public ProxyObject(Object target) {
		super();
		this.target = target;
	}

}
