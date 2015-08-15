package cn.com.sunjiesh.thirdpartdemo.service;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sunjiesh.wechat.entity.WechatMessage;
import cn.com.sunjiesh.wechat.service.IMessageReceiveProcessService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

public class MessageReceiveProcessServiceImpl  implements IMessageReceiveProcessService{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public Document messageRecive(WechatMessage wechatMessage)
			throws ServiceException {
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
		return doc4j;
	}

}
