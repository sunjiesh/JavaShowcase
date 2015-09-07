package cn.com.sunjiesh.thirdpartdemo.service;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.wechat.service.MessageReceiveService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

@Service
public class CustomMessageReceiveService extends MessageReceiveService{

	private static final Logger LOGGER=LoggerFactory.getLogger(CustomMessageReceiveService.class);

	@Override
	protected Document messageReceive(Document doc4j) throws ServiceException {
		LOGGER.info("Call CustomMessageReceiveService.messageReceive(Document doc4j)方法");
		return super.messageReceive(doc4j);
	}
	
	

}
