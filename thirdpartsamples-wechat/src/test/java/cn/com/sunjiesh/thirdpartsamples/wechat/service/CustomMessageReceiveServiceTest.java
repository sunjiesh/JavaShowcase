package cn.com.sunjiesh.thirdpartsamples.wechat.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.sunjiesh.thirdpartsamples.wechat.WechatApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WechatApplication.class)
public class CustomMessageReceiveServiceTest {

	@Autowired
	private CustomMessageReceiveService messageReceiveService;
	
	@Test
	public void testSaveReceiveMessage(){
		String wechatReceiveMessageFromUserName="xx";
		String message="测试消息";
		String messageType="text";
		messageReceiveService.saveReceiveMessage(wechatReceiveMessageFromUserName,message,messageType);
	}
}
