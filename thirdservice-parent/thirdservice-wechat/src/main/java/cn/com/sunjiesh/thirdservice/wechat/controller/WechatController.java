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
	
	/**
	 * 
	 * @api {get} /wechat/receive.do Valid
	 * @apiName Valid
	 * @apiGroup wechat
	 * @apiDescription 验证
	 * @apiVersion 1.0.0
	 * 
	 * @apiExample {curl} Example usage:
	 * 	curl -i "http://localhost:18081/wechat/receive.do?timestamp=1469112344&nonce=2096446109&echostr=6272519237797280058&signature=57277cb1d4bc60698cfdf079982c6e99d52f1836"
	 * @apiParam {String} timestamp	时间戳
	 * @apiParam {String} nonce   随机数
	 * @apiParam {String} echostr   随机字符串
	 * @apiParam {String} signature   微信加密签名 
	 * 
	 * @apiSuccess {String} echostr 随机字符串
	 * @apiSuccessExample {text} 正确返回:
	 * HTTP/1.1 200 OK
	 * Content-Type: text/plain;charset=UTF-8
	 * 6272519237797280058

	 * 
	 */
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
	
	/**
	 * 
	 * @api {post} /wechat/receive.do Receive
	 * @apiName Receive
	 * @apiGroup wechat
	 * @apiDescription 接收消息
	 * @apiVersion 1.0.0
	 * 
	 * @apiExample {curl} Example usage:
	 * 	curl -i -X POST "http://localhost:18081/wechat/receive.do?signature=04c790ff9b319a4d3a7889d23002fcbdbeff4c0f&timestamp=1469113396&nonce=822252680&openid=oiY-ExO09BRAH3kP80a_l438aBwQ" --data '<xml><ToUserName><![CDATA[gh_554f61b4d4c8]]></ToUserName><FromUserName><![CDATA[oiY-ExO09BRAH3kP80a_l438aBwQ]]></FromUserName><CreateTime>1469113396</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[GetTemplateMessage]]></EventKey></xml>' --header "Content-Type:text/xml"
	 * @apiParam {String} signature	微信加密签名
	 * @apiParam {String} timestamp   时间戳
	 * @apiParam {String} nonce   随机数
	 * @apiParam {String} openid   随机字符串
	 * @apiParam {String} requestBody   XML文档
	 * 
	 * @apiParamExample {json} Request-Example: 
	 * &lt;xml&gt;&lt;ToUserName&gt;&lt;![CDATA[gh_554f61b4d4c8]]&gt;&lt;/ToUserName&gt;&lt;FromUserName&gt;&lt;![CDATA[oiY-ExO09BRAH3kP80a_l438aBwQ]]&gt;&lt;/FromUserName&gt;&lt;CreateTime&gt;1469113396&lt;/CreateTime&gt;&lt;MsgType&gt;&lt;![CDATA[event]]&gt;&lt;/MsgType&gt;&lt;Event&gt;&lt;![CDATA[CLICK]]&gt;&lt;/Event&gt;&lt;EventKey&gt;&lt;![CDATA[GetTemplateMessage]]&gt;&lt;/EventKey&gt;&lt;/xml&gt;
	 * 
	 * @apiSuccess {String} xml xml文档
	 * 
	 * @apiSuccessExample {xml} 正确返回:
	 * HTTP/1.1 200 OK
	 * Content-Type: text/plain;charset=UTF-8
	 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
	 * &lt;xml&gt;&lt;ToUserName&gt;oiY-ExO09BRAH3kP80a_l438aBwQ&lt;/ToUserName&gt;&lt;FromUserName&gt;gh_554f61b4d4c8&lt;/FromUserName&gt;&lt;CreateTime&gt;1470407769120&lt;/CreateTime&gt;&lt;MsgType&gt;text&lt;/MsgType&gt;&lt;Content&gt;模板消息已发送&lt;/Content&gt;&lt;/xml&gt;
	 */
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
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("接收微信消息处理失败", e);
		}
		return "";
	} 
}
