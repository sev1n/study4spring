package com.xsq.spring.service;

public interface DefaultMethodService {

	void print();
	
	default void print(String name){
		System.out.println(name);	
	}
}
