package cn.com.sunjiesh.thirdservice.wechat.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.thirdservice.wechat.common.WechatEventClickMessageEventkeyEnum;
import cn.com.sunjiesh.thirdservice.wechat.common.WechatReceiveMessageConstants;
import cn.com.sunjiesh.thirdservice.wechat.dao.RedisWechatMessageDao;
import cn.com.sunjiesh.wechat.dao.IWechatAccessTokenDao;
import cn.com.sunjiesh.wechat.entity.message.WechatSendTemplateMessage;
import cn.com.sunjiesh.wechat.handler.WechatTemplateMessageHandler;
import cn.com.sunjiesh.wechat.handler.WechatUserHandler;
import cn.com.sunjiesh.wechat.helper.WechatMessageConvertDocumentHelper;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventClickMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventLocationMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventLocationSelectMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventPicPhotoOrAlbumMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventPicSysphotoMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventScanMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventScancodeCommonMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventSubscribeMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventUnSubscribeMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventViewMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalImageMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalLinkMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalLocationMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalShortvideoMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalTextMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalVideoMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalVoiceMessageRequest;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayImageMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayNewsMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayTextMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayVoiceMessageResponse;
import cn.com.sunjiesh.wechat.model.user.WechatUserDto;
import cn.com.sunjiesh.wechat.service.AbstractWechatMessageReceiveService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 自定义消息接收处理
 * @author Tom
 *
 */
@Service
public class CustomMessageReceiveService extends AbstractWechatMessageReceiveService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomMessageReceiveService.class);

	private static final String LAST_IMAGE_MESSAGE_MEDIA_ID = "lastImageMessageMediaId";
    
    private static final String LAST_VOICE_MESSAGE_MEDIA_ID = "lastVoiceMessageMediaId";
    
	private static final String LAST_VIDEO_MESSAGE_MEDIA_ID = "lastVideoMessageMediaId";
	
	private static final String LAST_SHORT_VIDEO_MESSAGE_MEDIA_ID = "lastShortVideoMessageMediaId";
	
	private static final Long REDIS_SAVE_TIME_OUT=24L;
	
	@Autowired
    private RedisWechatMessageDao redisWechatMessageDao;
	
	@Autowired
	protected IWechatAccessTokenDao wechatAccessTokenDao;
	
//	@Autowired
//    private ThirdpartyUserService thirdpartyUserService;

    private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
	
	@Override
	protected Document messageReceive(WechatEventLocationSelectMessageRequest wechatMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalTextMessageRequest textMessage) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalImageMessageRequest imageMessage) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalVoiceMessageRequest voiceMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalVideoMessageRequest videoMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalShortvideoMessageRequest shortVodeoMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalLocationMessageRequest locationMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalLinkMessageRequest linkMessage) {
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventClickMessageRequest clickMessage) throws ServiceException {
		//返回对象
    	Document respDoc=null;
    	
    	final String eventKey=clickMessage.getEventKey();
    	final String responseToUserName = clickMessage.getFromUserName();
		final String responseFromUserName = clickMessage.getToUserName();
    	LOGGER.debug("EventKey="+eventKey);
    	WechatEventClickMessageEventkeyEnum eventKeyEnum=WechatEventClickMessageEventkeyEnum.valueOf(eventKey);
    	
    	//保存到DB
    	saveReceiveMessage(clickMessage.getFromUserName(), clickMessage.getEventKey(), WechatReceiveMessageConstants.MESSAGE_TYPE_CLICK);
    	
		switch(eventKeyEnum){
    	case GetTextMessage:{
    		WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
    		textMessageResponse.setContent("Hello,This is a test text message.\n你好！這是一條測試文本消息");
    		respDoc=WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    	};break;
    	case GetImageMessage:{
    		String mediaId=redisWechatMessageDao.get(LAST_IMAGE_MESSAGE_MEDIA_ID);
    		if(StringUtils.isEmpty(mediaId)){
    			final String errorMsg = "没有找到用户上传的图片，请上传一张图片之后再试";
				LOGGER.warn(errorMsg);
    			WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
        		textMessageResponse.setContent(errorMsg);
        		respDoc=WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    		}else{
    			WechatReceiveReplayImageMessageResponse imageMessageResponse=new WechatReceiveReplayImageMessageResponse(responseToUserName, responseFromUserName);
        		imageMessageResponse.setMediaId(mediaId);
        		respDoc=WechatMessageConvertDocumentHelper.imageMessageResponseToDocumnet(imageMessageResponse);
    		}
    		
    	};break;
    	case GetVoiceMessage:{
    		String mediaId=redisWechatMessageDao.get(LAST_VOICE_MESSAGE_MEDIA_ID);
    		if(StringUtils.isEmpty(mediaId)){
    			final String errorMsg = "没有找到用户上传的语音，请重新发送一条语音消息之后再试";
				LOGGER.warn(errorMsg);
    			WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
        		textMessageResponse.setContent(errorMsg);
        		respDoc=WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    		}else{
    			WechatReceiveReplayVoiceMessageResponse voiceMessageResponse=new WechatReceiveReplayVoiceMessageResponse(responseToUserName, responseFromUserName);
    			voiceMessageResponse.setMediaId(mediaId);
    			respDoc=WechatMessageConvertDocumentHelper.voiceMessageResponseToDocumnet(voiceMessageResponse);
    		}
    	};break;
    	case GETNEWSMESSAGE1:{
    		WechatReceiveReplayNewsMessageResponse newsReplayMessage = new WechatReceiveReplayNewsMessageResponse(responseToUserName, responseFromUserName);
            WechatReceiveReplayNewsMessageResponse.WechatReceiveReplayNewsMessageResponseItem newsReplayMessageItem = newsReplayMessage.new WechatReceiveReplayNewsMessageResponseItem();
            newsReplayMessageItem.setDescription("测试图文消息");
            newsReplayMessageItem.setTitle("测试图片消息");
            newsReplayMessageItem.setUrl("http://ubuntu-sunjiesh.myalauda.cn/index.html");
            newsReplayMessageItem.setPicUrl("http://ubuntu-sunjiesh.myalauda.cn/360_200.jpg");
            newsReplayMessage.addItem(newsReplayMessageItem);
            respDoc=WechatMessageConvertDocumentHelper.newsMessageResponseToDocument(newsReplayMessage);
    	};break;
    	case GETNEWSMESSAGE2:{
    		WechatReceiveReplayNewsMessageResponse newsReplayMessage = new WechatReceiveReplayNewsMessageResponse(responseToUserName, responseFromUserName);
            WechatReceiveReplayNewsMessageResponse.WechatReceiveReplayNewsMessageResponseItem newsReplayMessageItem1 = newsReplayMessage.new WechatReceiveReplayNewsMessageResponseItem();
            newsReplayMessageItem1.setDescription("测试图文消息1");
            newsReplayMessageItem1.setTitle("测试图片消息1");
            newsReplayMessageItem1.setUrl("http://ubuntu-sunjiesh.myalauda.cn/index.html");
            newsReplayMessageItem1.setPicUrl("http://ubuntu-sunjiesh.myalauda.cn/360_200.jpg");
            newsReplayMessage.addItem(newsReplayMessageItem1);
            WechatReceiveReplayNewsMessageResponse.WechatReceiveReplayNewsMessageResponseItem newsReplayMessageItem2 = newsReplayMessage.new WechatReceiveReplayNewsMessageResponseItem();
            newsReplayMessageItem2.setDescription("测试图文消息2");
            newsReplayMessageItem2.setTitle("测试图片消息2");
            newsReplayMessageItem2.setUrl("http://ubuntu-sunjiesh.myalauda.cn/index.html");
            newsReplayMessageItem2.setPicUrl("http://ubuntu-sunjiesh.myalauda.cn/360_200.jpg");
            newsReplayMessage.addItem(newsReplayMessageItem2);
            respDoc=WechatMessageConvertDocumentHelper.newsMessageResponseToDocument(newsReplayMessage);
    	};break;
    	case GetTemplateMessage:{
    		WechatUserDto user = new WechatUserDto();
            user.setOpenId(responseToUserName);
            user=WechatUserHandler.getUserInfo(user, wechatAccessTokenDao.get());
            
            WechatSendTemplateMessage message = new WechatSendTemplateMessage();
            message.setTemplateId("RhnX8IXCkY2oStIvdTcJcjJDpZcglUUMbeb7dXJ6h1E");
            message.setUrl("http://www.baidu.com");
            message.setTopColor("#FF0000");
            WechatSendTemplateMessage.WechatSendTemplateMessageData messageData1 = message.new WechatSendTemplateMessageData();
            messageData1.setKeyName("Nickname");
            messageData1.setValue(user.getNickname());
            messageData1.setColor("#173177");
            WechatSendTemplateMessage.WechatSendTemplateMessageData messageData2 = message.new WechatSendTemplateMessageData();
            messageData2.setKeyName("Time");
            messageData2.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            messageData2.setColor("#173177");
            message.getData().add(messageData1);
            message.getData().add(messageData2);
            WechatTemplateMessageHandler.send(user, message, wechatAccessTokenDao.get());
            WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
    		textMessageResponse.setContent("模板消息已发送");
    		respDoc=WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    	};break;
    	default:{
    		respDoc=respError(responseToUserName, responseFromUserName);
    	}
    	}
    	
		return respDoc;
	}

	@Override
	protected Document messageRecive(WechatEventLocationMessageRequest locationMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventScanMessageRequest scanMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventSubscribeMessageRequest subscribeMessage) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventUnSubscribeMessageRequest unSubscribeMessage) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventViewMessageRequest viewMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventScancodeCommonMessageRequest scanCodePushMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventPicSysphotoMessageRequest picSysphotoMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventPicPhotoOrAlbumMessageRequest picPhotoOrAlbumEventMessage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
     * 错误消息返回
     * @param responseToUserName
     * @param responseFromUserName
     * @return
     */
    protected Document respError(String responseToUserName, String responseFromUserName) {
		WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
		textMessageResponse.setContent("暂不支持");
		return WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
	}


	/**
     * 保存消息
     * @param wechatReceiveMessageFromUserName
     * @param message
     * @param messageType
     */
    public void saveReceiveMessage(String wechatReceiveMessageFromUserName, String message, final String messageType) {
		//TODO need to save 
	}
}
