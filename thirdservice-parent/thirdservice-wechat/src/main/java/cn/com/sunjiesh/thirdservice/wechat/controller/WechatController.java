package cn.com.sunjiesh.thirdservice.wechat.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qq.weixin.mp.aes.AesException;

import cn.com.sunjiesh.thirdservice.wechat.service.CustomMessageReceiveService;
import cn.com.sunjiesh.wechat.handler.WechatValidHandler;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * Wechat
 * @author Tom
 *
 */
@RestController
@RequestMapping("/wechat")
public class WechatController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);
	
	@Autowired
	private CustomMessageReceiveService messageReceiveService;
	
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
	
	@RequestMapping(value = "/receive.do", method = RequestMethod.POST)
	@ResponseBody
	public String receive(@RequestHeader("Content-Type") String contentType,
			HttpServletRequest request,
			@RequestBody String requestBody) {
		
		try {
			LOGGER.debug("Receive a post requeset");
			Map<String,String> queryParams=new HashMap<String,String>();
			String requestQueryString=request.getQueryString();
			LOGGER.debug("requestQueryString="+requestQueryString);
			if(!StringUtils.isEmpty(requestQueryString)){
				String[] queryStringArr=requestQueryString.split("&");
				for(String queryString:queryStringArr){
					if(queryString.contains("=")){
						String[] paramKeyAndValue=queryString.split("=");
						String paramKey=paramKeyAndValue[0];
						String paramValue=paramKeyAndValue[1];
						queryParams.put(paramKey, paramValue);
					}
				}
			}
			LOGGER.debug("requestPart=" + requestBody);
			LOGGER.debug("contentType=" + contentType);
			String respXml = messageReceiveService.messageReceive(requestBody,queryParams);
			LOGGER.debug("respXml="+respXml);
//			super.responseXml(response,respXml);
			return respXml;
		} catch (ServiceException | AesException e) {
			e.printStackTrace();
			LOGGER.error("接收微信消息处理失败", e);
		}
		return "";
	} 
}
