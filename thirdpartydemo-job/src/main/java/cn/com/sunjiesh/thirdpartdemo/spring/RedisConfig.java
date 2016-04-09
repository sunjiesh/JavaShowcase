package cn.com.sunjiesh.thirdpartdemo.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import cn.com.sunjiesh.wechat.common.WechatConfigProperties;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置
 * @author thinkpad
 *
 */
@Configuration 
public class RedisConfig {

	@Bean
	public RedisConnectionFactory jedisConnectionFactory() {
		
		String redisHost=WechatConfigProperties.getProperty(WechatConfigProperties.REDIS_HOST);
		String redisPort=WechatConfigProperties.getProperty(WechatConfigProperties.REDIS_PORT);
		if(StringUtils.isEmpty(redisPort)){
			redisHost="localhost";
		}
		if(StringUtils.isEmpty(redisPort)){
			redisPort="6379";
		}
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(5);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		JedisConnectionFactory ob = new JedisConnectionFactory(poolConfig);
		ob.setUsePool(true);
		ob.setHostName(redisHost);
		ob.setPort(Integer.parseInt(redisPort));
		return ob;
	}
	
	@Bean
	public StringRedisTemplate  stringRedisTemplate(){
		return new StringRedisTemplate(jedisConnectionFactory());
	}
}
