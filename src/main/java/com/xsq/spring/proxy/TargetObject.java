package com.xsq.spring.proxy;

public class TargetObject {

	public void say(String name){
		System.out.println(String.format("i'am %s, ŒÁ∑π ±º‰¡À", name));
	}
	
	public int getAge(){
		return 25;
	}
}
