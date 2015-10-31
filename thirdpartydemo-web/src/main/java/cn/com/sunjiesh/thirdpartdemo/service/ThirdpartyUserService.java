package cn.com.sunjiesh.thirdpartdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.sunjiesh.thirdpartdemo.dao.WechatUserMapper;
import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.thirdpartdemo.model.WechatUserExample;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

@Service
@Transactional(rollbackFor={ServiceException.class,Exception.class})
public class ThirdpartyUserService {
	
	@Autowired
	private WechatUserMapper wechatUserMapper;

	/**
	 * 新建微信用戶數據
	 * @param wechatUser
	 */
	public void save(WechatUser wechatUser){
		wechatUserMapper.insert(wechatUser);
	}
	
	/**
	 * 刪除微信用戶數據
	 * @param wechatUser
	 */
	public void delete(WechatUser wechatUser){
		WechatUserExample example=new WechatUserExample();
		WechatUserExample.Criteria criteria= example.createCriteria();
		criteria.andOpenIdEqualTo(wechatUser.getOpenId());
		wechatUserMapper.deleteByExample(example);
	}
}
