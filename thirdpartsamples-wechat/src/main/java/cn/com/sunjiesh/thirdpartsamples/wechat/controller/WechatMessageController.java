package cn.com.sunjiesh.thirdpartsamples.wechat.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.sunjiesh.utils.web.SpringBaseController;

/**
 * 微信消息发送Demo
 * @author tomsun
 *
 */
@RestController
@RequestMapping("/wechat")
public class WechatMessageController extends SpringBaseController{
	
	@RequestMapping(value = "/messageSend.do", method = RequestMethod.GET)
	public void messageSend(HttpServletResponse response){
		
	}
}
