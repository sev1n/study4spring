package com.xsq.spring.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.CallbackFilter;

public class TargetFilter implements CallbackFilter {

	public int accept(Method method) {
		if ("say".equals(method.getName())) {
			return 0;
		}
		return 1;
	}

}
