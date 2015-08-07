package cn.com.sunjiesh.thirdpartdemo.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.sunjiesh.thirdpartdemo.entity.WechatMessage;

@Controller
@RequestMapping("/wechat/message")
public class WechatMessageController {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	
	@RequestMapping(value = "/textMessageRecive.do", method = RequestMethod.POST)
	public @ResponseBody WechatMessage textMessageRecive(@RequestBody final WechatMessage message){
		logger.debug(message.getContent());
		return message;
	}
}
