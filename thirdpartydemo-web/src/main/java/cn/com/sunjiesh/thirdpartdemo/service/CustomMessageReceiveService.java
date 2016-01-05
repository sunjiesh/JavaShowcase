package cn.com.sunjiesh.thirdpartdemo.service;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.thirdpartdemo.common.WechatEventClickMessageEventkeyEnum;
import cn.com.sunjiesh.thirdpartdemo.dao.RedisWechatMessageDao;
import cn.com.sunjiesh.wechat.entity.message.WechatReceiveNormalImageMessage;
import cn.com.sunjiesh.wechat.entity.message.WechatReceiveNormalLinkMessage;
import cn.com.sunjiesh.wechat.entity.message.WechatReceiveNormalLocationMessage;
import cn.com.sunjiesh.wechat.entity.message.WechatReceiveNormalShortvideoMessage;
import cn.com.sunjiesh.wechat.entity.message.WechatReceiveNormalVideoMessage;
import cn.com.sunjiesh.wechat.entity.message.WechatReceiveNormalVoiceMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventClickMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventLocationMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventLocationSelectMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventPicCommonMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventPicPhotoOrAlbumMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventPicSysphotoMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventScanMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventScancodeCommonMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventSubscribeMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventViewMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventWeixinMessage;
import cn.com.sunjiesh.wechat.helper.WechatMessageConvertDocumentHelper;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalTextMessageRequest;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayImageMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayNewsMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayNewsMessageResponse.WechatReceiveReplayNewsMessageResponseItem;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayTextMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayVoiceMessageResponse;
import cn.com.sunjiesh.wechat.service.AbstractWechatMessageReceiveService;
import cn.com.sunjiesh.wechat.service.IWechatMessageReceiveProcessService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

@Service
public class CustomMessageReceiveService extends AbstractWechatMessageReceiveService {

    private static final String LAST_IMAGE_MESSAGE_MEDIA_ID = "lastImageMessageMediaId";
    
    private static final String LAST_VOICE_MESSAGE_MEDIA_ID = "lastVoiceMessageMediaId";

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomMessageReceiveService.class);
    
    @Autowired
    private IWechatMessageReceiveProcessService messageReceiveProcessService;
    
    @Autowired
    private RedisWechatMessageDao redisWechatMessageDao;

    @Override
    protected Document messageReceive(Document doc4j) throws ServiceException {
        LOGGER.info("Call CustomMessageReceiveService.messageReceive(Document doc4j)方法");
        return super.messageReceive(doc4j);
    }

    @Override
    protected Document messageReceive(WechatReceiveEventLocationSelectMessage wechatMessage) {
        try {
            return messageReceiveProcessService.messageReceive(wechatMessage);
        } catch (ServiceException ex) {
            LOGGER.error(ex.getMessage(),ex);
        }
        return null;
    }

    @Override
    protected Document messageRecive(WechatNormalTextMessageRequest textMessage) {
    	String responseToUserName=textMessage.getFromUserName();
		String responseFromUserName=textMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalImageMessage imageMessage) {
    	String responseToUserName=imageMessage.getFromUserName();
		String responseFromUserName=imageMessage.getToUserName();
		String mediaId=imageMessage.getMediaId();
		redisWechatMessageDao.save(LAST_IMAGE_MESSAGE_MEDIA_ID, mediaId);
		
		WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
		textMessageResponse.setContent("图片已经上传，midiaId为="+mediaId);
		return WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalVoiceMessage voiceMessage) {
    	String responseToUserName=voiceMessage.getFromUserName();
		String responseFromUserName=voiceMessage.getToUserName();
		String mediaId=voiceMessage.getMediaId();
		redisWechatMessageDao.save(LAST_VOICE_MESSAGE_MEDIA_ID, mediaId);
		WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
		textMessageResponse.setContent("语音已经上传，midiaId为="+mediaId);
		return WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalVideoMessage videoMessage) {
    	String responseToUserName=videoMessage.getFromUserName();
		String responseFromUserName=videoMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalShortvideoMessage shortVodeoMessage) {
    	String responseToUserName=shortVodeoMessage.getFromUserName();
		String responseFromUserName=shortVodeoMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalLocationMessage locationMessage) {
    	String responseToUserName=locationMessage.getFromUserName();
		String responseFromUserName=locationMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalLinkMessage linkMessage) {
    	String responseToUserName=linkMessage.getFromUserName();
		String responseFromUserName=linkMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventClickMessage clickMessage) {
    	
    	//返回对象
    	Document respDoc=null;
    	
    	final String eventKey=clickMessage.getEventKey();
    	final String responseToUserName = clickMessage.getFromUserName();
		final String responseFromUserName = clickMessage.getToUserName();
    	LOGGER.debug("EventKey="+eventKey);
    	WechatEventClickMessageEventkeyEnum eventKeyEnum=WechatEventClickMessageEventkeyEnum.valueOf(eventKey);
    	
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
    		WechatReceiveReplayNewsMessageResponse newsReplayMessage = new WechatReceiveReplayNewsMessageResponse(responseToUserName, responseFromUserName, "news");
            WechatReceiveReplayNewsMessageResponse.WechatReceiveReplayNewsMessageResponseItem newsReplayMessageItem = newsReplayMessage.new WechatReceiveReplayNewsMessageResponseItem();
            newsReplayMessageItem.setDescription("测试图文消息");
            newsReplayMessageItem.setTitle("测试图片消息");
            newsReplayMessageItem.setUrl("http://ubuntu-sunjiesh.myalauda.cn/index.html");
            newsReplayMessageItem.setPicUrl("http://ubuntu-sunjiesh.myalauda.cn/360_200.jpg");
            newsReplayMessage.addItem(newsReplayMessageItem);
            respDoc=WechatMessageConvertDocumentHelper.newsMessageResponseToDocument(newsReplayMessage);
    	};break;
    	case GETNEWSMESSAGE2:{
    		
    	};break;
    	default:{
    		respDoc=respError(responseToUserName, responseFromUserName);
    	}
    	}
    	
		return respDoc;

    }

    @Override
    protected Document messageRecive(WechatReceiveEventLocationMessage locationMessage) {
    	String responseToUserName=locationMessage.getFromUserName();
		String responseFromUserName=locationMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
		
    }


    @Override
    protected Document messageRecive(WechatReceiveEventScanMessage scanMessage) {
    	String responseToUserName=scanMessage.getFromUserName();
		String responseFromUserName=scanMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventSubscribeMessage subscribeMessage) {
    	String responseToUserName=subscribeMessage.getFromUserName();
		String responseFromUserName=subscribeMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventViewMessage viewMessage) {
    	String responseToUserName=viewMessage.getFromUserName();
		String responseFromUserName=viewMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventScancodeCommonMessage scanCodePushMessage) {
    	String responseToUserName=scanCodePushMessage.getFromUserName();
		String responseFromUserName=scanCodePushMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventWeixinMessage picPhotoOrAlbumEventMessage) {
    	String responseToUserName=picPhotoOrAlbumEventMessage.getFromUserName();
		String responseFromUserName=picPhotoOrAlbumEventMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventPicSysphotoMessage picSysphotoMessage) {
    	String responseToUserName=picSysphotoMessage.getFromUserName();
		String responseFromUserName=picSysphotoMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventPicPhotoOrAlbumMessage picPhotoOrAlbumEventMessage) {
    	String responseToUserName=picPhotoOrAlbumEventMessage.getFromUserName();
		String responseFromUserName=picPhotoOrAlbumEventMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
    }

    @Override
    protected Document messageRecive(WechatReceiveEventPicCommonMessage wechatMessage) {
    	String responseToUserName=wechatMessage.getFromUserName();
		String responseFromUserName=wechatMessage.getToUserName();
		return respError(responseToUserName, responseFromUserName);
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
}
