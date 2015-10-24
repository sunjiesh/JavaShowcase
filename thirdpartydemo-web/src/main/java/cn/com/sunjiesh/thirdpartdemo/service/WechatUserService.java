package cn.com.sunjiesh.thirdpartdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.sunjiesh.thirdpartdemo.dao.WechatUserMapper;
import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

@Service
@Transactional(rollbackFor={ServiceException.class,Exception.class})
public class WechatUserService {
	
	@Autowired
	private WechatUserMapper wechatUserMapper;

	public void save(WechatUser wechatUser){
		wechatUserMapper.insert(wechatUser);
	}
}
