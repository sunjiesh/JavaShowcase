package cn.com.sunjiesh.thirdpartdemo.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.sunjiesh.utils.web.SpringBaseController;
import cn.com.sunjiesh.wechat.entity.User;
import cn.com.sunjiesh.wechat.entity.message.WechatSendTemplateMessage;
import cn.com.sunjiesh.wechat.entity.message.WechatSendTemplateMessageData;
import cn.com.sunjiesh.wechat.service.TemplateMessageService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

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
	
	private String token="ns8DDRMN7022vOeeKhMCAw1-I9VkOCO9J3AqI_-9lyxB3sbWRxTbeZnSywbmV4pU7TTSH1SwZTjo5D15G54Br6fydrGOv1Q_GYAhOMAKFkM";
	
	@RequestMapping(value = "/messageSend.do", method = RequestMethod.GET)
	public void messageSend(HttpServletResponse response){
		User user=new User();
		user.setOpenId("oiY-ExHTPPJ2bBs2VJl1L4cM35Ig");
		WechatSendTemplateMessage message=new WechatSendTemplateMessage();
		message.setTemplateId("C88oa-jbT7K6hlDmGwUnJztSSrSCt1yWOdAtWChOEoo");
		message.setUrl("http://www.baidu.com");
		message.setTopColor("#FF0000");
		WechatSendTemplateMessageData messageData1=new WechatSendTemplateMessageData();
		messageData1.setKeyName("参数1");
		messageData1.setValue("ca");
		messageData1.setColor("#173177");
		message.getData().add(messageData1);
		String result="";
		try {
			result=templateMessageService.send(user, message, token);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		super.responseText(response, result);
	}
}
