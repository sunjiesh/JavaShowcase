package cn.com.sunjiesh.thirdpartdemo.service;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventSubscribeMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventUnSubscribeMessage;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

@Test
@ContextConfiguration(locations={"/applicationContext.xml","/applicationContext-persistment.xml","/applicationContext-wechat.xml"}) 
@Transactional 
public class CustomMessageReceiveServiceTest  extends AbstractTestNGSpringContextTests {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(CustomMessageReceiveServiceTest.class);

	@Autowired
	private CustomMessageReceiveService messageReceiveService;
	
	public void testEventSubscribeMessageReceive() throws ServiceException{
        String toUserName="gh_554f61b4d4c8";
		String fromUserName="oiY-ExO09BRAH3kP80a_l438aBwQ";
		WechatReceiveEventSubscribeMessage subscribeMessage = new WechatReceiveEventSubscribeMessage(toUserName, fromUserName, "subscribe", "SUBSCRIBE");
		Document respDoc=messageReceiveService.messageRecive(subscribeMessage);
		LOGGER.debug(respDoc.asXML());
	}
	
	public void testEventUnSubscribeMessageReceive() throws ServiceException{
		String toUserName="gh_554f61b4d4c8";
		String fromUserName="oiY-ExO09BRAH3kP80a_l438aBwQ";
		WechatReceiveEventUnSubscribeMessage subscribeMessage = new WechatReceiveEventUnSubscribeMessage(toUserName, fromUserName, "subscribe", "SUBSCRIBE");
		Document respDoc=messageReceiveService.messageRecive(subscribeMessage);
		LOGGER.debug(respDoc.asXML());
	} 
	
}
