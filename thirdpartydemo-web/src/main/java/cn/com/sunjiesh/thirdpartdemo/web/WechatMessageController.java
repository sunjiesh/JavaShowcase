package cn.com.sunjiesh.thirdpartdemo.web;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.sunjiesh.thirdpartdemo.entity.WechatMessage;
import cn.com.sunjiesh.utils.web.SpringBaseController;

@Controller
@RequestMapping("/wechat/message")
public class WechatMessageController extends SpringBaseController{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	/**
	 * 问题消息接收处理
	 * @param message
	 * @param response
	 */
	@RequestMapping(value = "/textMessageRecive.do", method = RequestMethod.POST)
	public void textMessageRecive(@RequestBody final WechatMessage message,HttpServletResponse response){
		logger.debug("receive a textMessageRecive request ");
		Document doc4j=DocumentHelper.createDocument();
		Element rootEle=doc4j.addElement("xml");
		Element toUserNameEle=rootEle.addElement("ToUserName");
		Element fromUserNameEle=rootEle.addElement("FromUserName");
		Element createTimeEle=rootEle.addElement("CreateTime");
		Element msgTypeEle=rootEle.addElement("MsgType");
		Element contentEle=rootEle.addElement("Content");
		toUserNameEle.setText("oiY-ExHTPPJ2bBs2VJl1L4cM35Ig");
		fromUserNameEle.setText("gh_554f61b4d4c8");
		createTimeEle.setText(String.valueOf(System.currentTimeMillis()));
		msgTypeEle.setText("text");
		contentEle.setText("你好");
		String xml=doc4j.asXML();
		super.responseXml(response, xml);
	}
}
