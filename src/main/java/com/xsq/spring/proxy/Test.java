package com.xsq.spring.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class Test {

	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "d://sevin");
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(TargetObject.class);
		//enhancer.setCallback(new ProxyObject2());
		enhancer.setCallback(new ProxyObject(new TargetObject()));

		TargetObject to = (TargetObject) enhancer.create();
		to.say("sevin");
	}
}
