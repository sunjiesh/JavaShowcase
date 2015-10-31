package cn.com.sunjiesh.thirdpartdemo.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventSubscribeMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventUnSubscribeMessage;
import cn.com.sunjiesh.wechat.model.dto.user.WechatUserDto;
import cn.com.sunjiesh.wechat.service.WechatMessageReceiveProcessServiceImpl;
import cn.com.sunjiesh.wechat.service.WechatUserService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

public class CustomerWechatMessageReceiveProcessServiceImpl extends WechatMessageReceiveProcessServiceImpl{
	
	private Logger LOGGER=LoggerFactory.getLogger(CustomerWechatMessageReceiveProcessServiceImpl.class);

	private ThirdpartyUserService thirdpartyUserService;
	
	private WechatUserService wechatUserService;
	
	@Override
	public Document messageRecive(WechatReceiveEventSubscribeMessage wechatMessage) throws ServiceException {
		
		//根据OpenId查询对应的信息
		String wechatOpenId=wechatMessage.getFromUserName();
		WechatUserDto wechatUserDto=new WechatUserDto();
		wechatUserDto.setOpenId(wechatOpenId);
		wechatUserDto=wechatUserService.getUserInfo(wechatUserDto);
		
		//封装WechatUser对象，插入數據到客戶端
		WechatUser wechatUser=new WechatUser();
		try {
			BeanUtils.copyProperties(wechatUser, wechatUserDto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOGGER.error("Convert WechatUserDto To WechatUser Error",e);
		}
		thirdpartyUserService.save(wechatUser);
		
		Document document=super.messageRecive(wechatMessage);
		return document;
	}

	@Override
	public Document messageRecive(WechatReceiveEventUnSubscribeMessage wechatMessage) throws ServiceException {
		Document document=super.messageRecive(wechatMessage);
		return document;
	}

	public ThirdpartyUserService getThirdpartyUserService() {
		return thirdpartyUserService;
	}

	public void setThirdpartyUserService(ThirdpartyUserService thirdpartyUserService) {
		this.thirdpartyUserService = thirdpartyUserService;
	}

	public WechatUserService getWechatUserService() {
		return wechatUserService;
	}

	public void setWechatUserService(WechatUserService wechatUserService) {
		this.wechatUserService = wechatUserService;
	}

	
	
}
