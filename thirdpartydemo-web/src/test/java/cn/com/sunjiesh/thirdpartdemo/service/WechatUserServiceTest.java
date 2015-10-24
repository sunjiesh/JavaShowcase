package cn.com.sunjiesh.thirdpartdemo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;

@Test
@ContextConfiguration(locations={"/applicationContext.xml","/applicationContext-persistment.xml"}) 
@Transactional 
public class WechatUserServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private WechatUserService wechatUserService;
	
	public void testSave(){
		WechatUser wechatUser=new WechatUser();
		wechatUser.setCity("shanghai");
		wechatUser.setProvince("shanghai");
		wechatUser.setCountry("shanghai");
		wechatUser.setNickname("nickName");
		wechatUser.setOpenId("reat");
		wechatUser.setRemark("remark");
		wechatUser.setSubscribe("");
		wechatUser.setSubscribeTime(new Date());
		wechatUser.setCreateTime(new Date());
		wechatUser.setCreateUser("test");
		wechatUser.setUpdateTime(new Date());
		wechatUser.setUpdateUser("test");
		wechatUserService.save(wechatUser);
	}
}
