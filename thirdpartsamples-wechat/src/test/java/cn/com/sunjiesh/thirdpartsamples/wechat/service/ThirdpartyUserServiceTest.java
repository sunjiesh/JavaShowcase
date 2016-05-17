package cn.com.sunjiesh.thirdpartsamples.wechat.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.thirdpartsamples.wechat.WechatApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WechatApplication.class)
public class ThirdpartyUserServiceTest {

	@Autowired
	private ThirdpartyUserService thirdpartyUserService;
	
	@Test
	public void testSave(){
		WechatUser wechatUser=new WechatUser();
		wechatUser.setCity("city");
		wechatUser.setCountry("country");
		wechatUser.setCreateTime(new Date());
		wechatUser.setCreateUser("createuser");
		wechatUser.setHeadImgUrl("http://");
		wechatUser.setLanguage("en");
		wechatUser.setNickname("nickname");
		wechatUser.setOpenId("fa");
		wechatUser.setProvince("province");
		wechatUser.setRemark("remark");
		wechatUser.setSex(1);
		wechatUser.setSubscribe("subscribe");
		wechatUser.setSubscribeTime(new Date());
		wechatUser.setUnionId("11");
		wechatUser.setUpdateTime(new Date());
		wechatUser.setUpdateUser("updateUser");
		thirdpartyUserService.save(wechatUser);
	}
	
	@Test
	public void delete(){
		WechatUser wechatUser=new WechatUser();
		wechatUser.setCity("shanghai");
		wechatUser.setOpenId("fa");
		thirdpartyUserService.delete(wechatUser);
	}
}
