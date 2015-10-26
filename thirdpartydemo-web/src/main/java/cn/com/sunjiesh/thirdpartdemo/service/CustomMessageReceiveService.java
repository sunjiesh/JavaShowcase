package cn.com.sunjiesh.thirdpartdemo.service;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.wechat.service.WechatMessageReceiveService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

public class CustomMessageReceiveService extends WechatMessageReceiveService{

	private static final Logger LOGGER=LoggerFactory.getLogger(CustomMessageReceiveService.class);

	@Override
	protected Document messageReceive(Document doc4j) throws ServiceException {
		LOGGER.info("Call CustomMessageReceiveService.messageReceive(Document doc4j)方法");
		return super.messageReceive(doc4j);
	}
	
	

}
