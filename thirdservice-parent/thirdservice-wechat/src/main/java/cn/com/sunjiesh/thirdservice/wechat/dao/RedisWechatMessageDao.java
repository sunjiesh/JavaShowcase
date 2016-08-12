package cn.com.sunjiesh.thirdservice.wechat.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisWechatMessageDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisWechatMessageDao.class);
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void save(String key,String value){
		stringRedisTemplate.execute(new RedisCallback<Long>(){

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection jedis = (StringRedisConnection)connection;
				jedis.select(0);
				jedis.set(key, value);
				LOGGER.debug(key+" in redis is "+stringRedisTemplate.opsForValue().get(key));
				return null;
			}
			
		});
		
	}
	
	public void save(String key,String value,long timeout){
		stringRedisTemplate.execute(new RedisCallback<Long>(){

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection jedis = (StringRedisConnection)connection;
				jedis.select(0);
				jedis.set(key, value);
				jedis.expire(key, timeout*60*60);
				LOGGER.debug(key+" in redis is "+stringRedisTemplate.opsForValue().get(key));
				return null;
			}
			
		});
		
	}
	
	public String get(String key){
		return stringRedisTemplate.opsForValue().get(key);
	}
}
