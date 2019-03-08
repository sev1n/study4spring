package com.xsq.spring.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/second")
public class SecondController {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private DataSource dataSource;
	
	@ResponseBody
	@RequestMapping("/say")
	public Person getSomeBody(){
		return new Person("sevin",25);
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
	
	@RequestMapping("/{bookId}")
	@ResponseBody
	public int getBookName(@PathVariable("bookId") String bookId){
		List<Map<String, String>> list = jdbcTemplate.query("select book_name, author_name from content where id in (?, ?)",
				new PreparedStatementSetter(){

					@Override
					public void setValues(PreparedStatement ps) throws SQLException {
						ps.setString(1, "1000003394979");
						ps.setString(2, "1000003411442");
					}
			
				},
				new ResultSetExtractor<List<Map<String, String>>>() {

					public List<Map<String, String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
						List<Map<String, String>> result = new ArrayList<Map<String, String>>();
						Map<String, String> map;
						while(rs.next()){
							map = new HashMap<String, String>(); 
							map.put("bookName", rs.getString(1));
							map.put("authorName", rs.getString(2));
							result.add(map);
						}
						return result;
					}
				});
		System.out.println(list);
		return jdbcTemplate.queryForInt("select count(*) from content");
	}
	
	@RequestMapping("/testRdbms")
	public void testRdbms(){
		SqlFunction<Integer> sf = new SqlFunction<>(dataSource, "select count(*) from content");
		sf.compile();
		System.out.println(sf.run());
	}
}
