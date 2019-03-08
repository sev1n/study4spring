package com.xsq.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xsq.spring.service.Service;
import com.xsq.spring.service.impl.NewService;

@Controller
@RequestMapping("transaction")
public class TestTransationController {

	@Autowired
	private Service newService;
	
	@RequestMapping("test")
	@ResponseBody
	public String testTransation(){
		newService.add();
		return "OK";
	}
}
