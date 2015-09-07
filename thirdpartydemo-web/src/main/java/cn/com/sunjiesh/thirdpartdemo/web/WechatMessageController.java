package cn.com.sunjiesh.thirdpartdemo.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.sunjiesh.utils.web.SpringBaseController;
import cn.com.sunjiesh.wechat.service.TemplateMessageService;

/**
 * 微信消息发送Demo
 * @author tomsun
 *
 */
@Controller
@RequestMapping("/wechat")
public class WechatMessageController extends SpringBaseController{

	@Autowired
	private TemplateMessageService templateMessageService;
	
	@RequestMapping(value = "/messageSend.do", method = RequestMethod.GET)
	public void messageSend(HttpServletResponse response){
		
	}
}
