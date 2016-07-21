package cn.com.sunjiesh.thirdservice.wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.sunjiesh.wechat.handler.WechatValidHandler;

/**
 * Wechat
 * @author Tom
 *
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);
	
	
	@RequestMapping(value = "/receive.do", method = RequestMethod.GET)
	public String valid(@RequestParam("timestamp") String timestamp,
			@RequestParam("echostr") String echoStr,
			@RequestParam("nonce") String nonce,
			@RequestParam("signature") String signature){
		LOGGER.debug("Receive a get requeset");

		// 将token、timestamp、nonce三个参数进行字典序排序
		String responseStr = WechatValidHandler.valid(timestamp, echoStr, nonce, signature);
		return responseStr;
	}
}
