package com.xsq.spring.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xsq.spring.service.HelloService;

@Service
public class NewService implements com.xsq.spring.service.Service{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private HelloService helloService;

	@Transactional
	public void add(){
		System.out.println("我来看下");
		jdbcTemplate.execute("insert into stop_dics(stop_dic) values('ok')");
		System.out.println("执行完毕");
		helloService.add2();
		throw new RuntimeException("插入失败，进行回滚");
	}
}
