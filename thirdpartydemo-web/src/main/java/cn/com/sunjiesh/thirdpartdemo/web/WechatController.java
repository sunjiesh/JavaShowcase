package cn.com.sunjiesh.thirdpartdemo.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qq.weixin.mp.aes.AesException;

import cn.com.sunjiesh.utils.web.SpringBaseController;
import cn.com.sunjiesh.wechat.service.MessageReceiveService;
import cn.com.sunjiesh.wechat.service.WechatValidService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 微信服务请求
 * 
 * @author tomsun
 */
@Controller
@RequestMapping("/wechat")
public class WechatController extends SpringBaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);

	@Autowired
	private MessageReceiveService messageReceiveService;
	
	@Autowired
	private WechatValidService wechatValidService;
	

	/**
	 * 微信服务GET请求
	 * 
	 * @param request HttpServletRequest请求
	 * @param response HttpServletResponse请求
	 * @throws IOException 异常
	 */
	@RequestMapping(value = "/receive.do", method = RequestMethod.GET)
	public void valid(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		LOGGER.debug("Receive a get requeset");

		// 将token、timestamp、nonce三个参数进行字典序排序
		String timestamp = request.getParameter("timestamp");
		String echoStr = request.getParameter("echostr");
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String responseStr = wechatValidService.valid(timestamp, echoStr, nonce, signature);
		super.responseText(response, responseStr);

	}

	

	/**
	 * 微信服务POST请求
	 * @param contentType contentType
	 * @param request request
	 * @param response response
	 */
	@RequestMapping(value = "/receive.do", method = RequestMethod.POST)
	public void receive(@RequestHeader("Content-Type") String contentType,
			HttpServletRequest request,HttpServletResponse response) {
		LOGGER.debug("Receive a post requeset");
		try {
			String requestBody = getRequestContent(request);
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
			String respXml=messageReceiveService.messageReceive(requestBody,queryParams);
			super.responseXml(response,respXml);
		} catch (ServiceException ex) {
			LOGGER.error("接收微信消息处理失败", ex);
		} catch (AesException e) {
			LOGGER.error("接收微信消息处理失败", e);
		}
	}
	
}
