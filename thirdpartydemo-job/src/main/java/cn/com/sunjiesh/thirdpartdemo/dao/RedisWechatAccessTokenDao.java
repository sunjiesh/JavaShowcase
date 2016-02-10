package cn.com.sunjiesh.thirdpartdemo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import cn.com.sunjiesh.wechat.dao.IWechatAccessTokenDao;

/**
 * Redis保存WechatAccessToken
 * @author tom
 *
 */
@Repository("wechatAccessTokenDao")
public class RedisWechatAccessTokenDao implements IWechatAccessTokenDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisWechatAccessTokenDao.class);
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void save(String accessToken) {
		stringRedisTemplate.opsForValue().set("wechatAccessToken", accessToken);
		LOGGER.debug("wechatAccessToken in redis is "+stringRedisTemplate.opsForValue().get("wechatAccessToken"));
	}

	@Override
	public String get() {
		String accessToken=stringRedisTemplate.opsForValue().get("wechatAccessToken");
		LOGGER.debug("wechatAccessToken in redis is "+accessToken);
		return accessToken;
	}

}
