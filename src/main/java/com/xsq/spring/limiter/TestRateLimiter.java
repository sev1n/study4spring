package com.xsq.spring.limiter;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 单机情况下的限流
 * @author Administrator
 *
 */
@Service(value = "limiter")
public class TestRateLimiter{
	private RateLimiter rl = RateLimiter.create(1);//限流器：一秒一个请求

	public String limiter(){
		if (rl.tryAcquire(100, TimeUnit.MILLISECONDS)) {//最多等待前一个请求100ms，否则丢弃当前请求
			return "Go";
		}else{
			return "Reject";
		}
	}
}
