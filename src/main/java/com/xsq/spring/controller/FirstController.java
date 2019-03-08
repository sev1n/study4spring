package com.xsq.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xsq.spring.service.UserService;

@Controller
@RequestMapping("/")
public class FirstController {
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping("/someBody")
	public Person getSomeBody(){
		return new Person("sevin",25);
	}
	
	@ResponseBody
	@RequestMapping("test")
	public void test(){
		userService.doWork("นคื๗");
	}
	static class Person {
		private String name;
		private int age;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Person [name=");
			builder.append(name);
			builder.append(", age=");
			builder.append(age);
			builder.append("]");
			return builder.toString();
		}
		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
	}
}
