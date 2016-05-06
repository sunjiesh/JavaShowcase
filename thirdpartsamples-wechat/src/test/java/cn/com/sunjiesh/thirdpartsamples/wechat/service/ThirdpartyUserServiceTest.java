package cn.com.sunjiesh.thirdpartsamples.wechat.service;

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
	public void delete(){
		WechatUser wechatUser=new WechatUser();
		wechatUser.setCity("shanghai");
		thirdpartyUserService.delete(wechatUser);
	}
}
