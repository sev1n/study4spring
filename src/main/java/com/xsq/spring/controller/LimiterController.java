package com.xsq.spring.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xsq.spring.limiter.TestRateLimiter;
import com.xsq.spring.limiter.TestRedisLimiter;
import com.xsq.spring.redis.RedisClient;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("limit")
public class LimiterController {

	@Resource(name = "limiter")
	TestRateLimiter limiter;
	
	@Resource(name = "redisLimit")
	TestRedisLimiter redisLimit;
	
	@ResponseBody
	@RequestMapping("go1")
	public String go1(){
		return limiter.limiter();
	}
	
	@ResponseBody
	@RequestMapping("go2")
	public String go2(@RequestParam("name") String name){
		boolean stoped = redisLimit.discard(name);
		if (stoped) {
			return "Reject";
		}
		return "Go";
	}
}
