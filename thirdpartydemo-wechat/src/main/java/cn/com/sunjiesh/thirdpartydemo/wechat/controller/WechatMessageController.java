package cn.com.sunjiesh.thirdpartydemo.wechat.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.sunjiesh.utils.web.SpringBaseController;

/**
 * 微信消息发送Demo
 * @author tomsun
 *
 */
@Controller
@RequestMapping("/wechat")
public class WechatMessageController extends SpringBaseController{
	
	@RequestMapping(value = "/messageSend.do", method = RequestMethod.GET)
	public void messageSend(HttpServletResponse response){
		
	}
}
