package com.xsq.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.xsq.spring.service.UserService;


public class UserServiceImpl implements UserService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String getName() {
		return "xieshiqi";
	}

	public void doWork(String something) {
		System.out.println("一二三四五：" + something);
	}
}
