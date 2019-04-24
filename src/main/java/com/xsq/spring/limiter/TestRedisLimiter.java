package com.xsq.spring.limiter;

import org.springframework.stereotype.Service;

import com.xsq.spring.redis.RedisClient;
import redis.clients.jedis.Jedis;

/**
 * 集群限流
 * @author Administrator
 *
 */
@Service("redisLimit")
public class TestRedisLimiter {
	public boolean discard(String key){
		boolean discard = false;
		Jedis jedis = RedisClient.getJedis();
		try{
			Long num = jedis.incr(key);//自增计数器
			if (1 == num) {
				jedis.expire(key, 5);//设置过期时间，超时计数器置空
			}else if (num > 10) {
				discard = true;//有效期内，请求超过10个丢弃
			}
		}finally{
			jedis.close();
		}
		return discard;
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}