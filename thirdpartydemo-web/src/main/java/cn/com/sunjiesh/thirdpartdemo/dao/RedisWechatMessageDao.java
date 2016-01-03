package cn.com.sunjiesh.thirdpartdemo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisWechatMessageDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisWechatMessageDao.class);
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void save(String key,String value){
		stringRedisTemplate.opsForValue().set(key, value);
		LOGGER.debug(key+" in redis is "+stringRedisTemplate.opsForValue().get(key));
	}
	
	public String get(String key){
		return stringRedisTemplate.opsForValue().get(key);
	}
}
