package com.xsq.spring.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {
	private static JedisPool pool = null;
	static{
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(15);
			config.setMaxWaitMillis(10000);
			config.setMinIdle(8);
			config.setMaxTotal(-1);
			// 在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(false);
			// 在空闲时检查有效性, 默认false
			config.setTestWhileIdle(true);
			config.setTestOnReturn(true);
			pool = new JedisPool(config, "127.0.0.1", 6379, 10000);
	}
	
	public static Jedis getJedis(){
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		return jedis;
	}
	public static void main(String[] args) {
		Jedis jedis = getJedis();
		if ("OK".equals(jedis.set("age", "25", "NX", "EX", 10))) {
			System.out.println("加锁成功");
			jedis.del("age");//删除
		}
	}
}
