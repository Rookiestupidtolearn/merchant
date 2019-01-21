package com.doubao.merchant.server.util.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
// 启用缓存，这个注解很重要；
public class RedisBasicCacheConfig {

	Logger logger = Logger.getLogger(RedisBasicCacheConfig.class);

	@Value("${redis.maxWait}")
	private long maxWaitMillis;

	@Value("${redis.host}")
	private String qsdHostName;

	@Value("${redis.port}")
	private int qsdPort;

	@Value("${redis.password}")
	private String qsdPassWord;
	
    @Value("${redis.maxTotal}")  
    private int maxTotal;  
	
    @Value("${redis.maxIdle}")  
    private int maxIdle;  
    
    @Value("${redis.minIdle}")  
    private int minIdle; 

    @Value("${redis.timeout}")  
    private int timeout;  
    
    @Bean
    public JedisPool redisPoolFactory() {
        logger.info("===JedisPool注入成功！！");
        logger.info("redis地址：" + qsdHostName + ":" + qsdPort);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);        
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, qsdHostName, qsdPort, timeout, qsdPassWord);
        return jedisPool;
    }

}
