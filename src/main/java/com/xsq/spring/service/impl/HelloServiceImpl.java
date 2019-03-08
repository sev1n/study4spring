package com.xsq.spring.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xsq.spring.service.HelloService;

public class HelloServiceImpl implements HelloService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public void say() {
		System.out.println("i'm sevin");
	}
	
	public void say(String s) {
		System.out.println(s + ", i'm sevin");
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void add2() {
		jdbcTemplate.execute("insert into stop_dics(stop_dic) values('ok2')");
		//Map<String, Object> map = jdbcTemplate.queryForMap("select stop_dic_id as id, stop_dic as abcs from stop_dics where stop_dic_id=1");
	}
}
