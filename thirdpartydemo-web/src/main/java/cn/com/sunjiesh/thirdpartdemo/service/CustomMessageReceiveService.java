package cn.com.sunjiesh.thirdpartdemo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.thirdpartdemo.common.ThirdpartyDemoConstants;
import cn.com.sunjiesh.thirdpartdemo.common.WechatEventClickMessageEventkeyEnum;
import cn.com.sunjiesh.thirdpartdemo.dao.RedisWechatMessageDao;
import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.wechat.dao.IWechatAccessTokenDao;
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
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventUnSubscribeMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventViewMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventWeixinMessage;
import cn.com.sunjiesh.wechat.handler.WechatUserHandler;
import cn.com.sunjiesh.wechat.helper.WechatMessageConvertDocumentHelper;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalTextMessageRequest;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayImageMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayNewsMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayTextMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayVoiceMessageResponse;
import cn.com.sunjiesh.wechat.model.user.WechatUserDto;
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
    
    @Autowired
    protected IWechatAccessTokenDao wechatAccessTokenDao;
    
    @Autowired
    private ThirdpartyUserService thirdpartyUserService;

    private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

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
    protected Document messageRecive(WechatReceiveEventSubscribeMessage subscribeMessage) throws ServiceException {
    	//根据OpenId查询对应的信息
        String wechatOpenId = subscribeMessage.getFromUserName();
        WechatUserDto wechatUserDto = new WechatUserDto();
        wechatUserDto.setOpenId(wechatOpenId);
        wechatUserDto = WechatUserHandler.getUserInfo(wechatUserDto, wechatAccessTokenDao.get());

        //封装WechatUser对象，插入數據到客戶端
        WechatUser wechatUser = new WechatUser();
        try {
            BeanUtils.copyProperties(wechatUser, wechatUserDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Convert WechatUserDto To WechatUser Error", e);
        }
        wechatUser.setCreateTime(new Date());
        wechatUser.setCreateUser(ThirdpartyDemoConstants.CREATE_USER_THIRDPARTYDEMO_WEB);
        wechatUser.setUpdateTime(new Date());
        wechatUser.setUpdateUser(ThirdpartyDemoConstants.CREATE_USER_THIRDPARTYDEMO_WEB);
        thirdpartyUserService.save(wechatUser);
        
        String responseToUserName=subscribeMessage.getFromUserName();
		String responseFromUserName=subscribeMessage.getToUserName();
        WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
		textMessageResponse.setContent("谢谢关注公众号");
		return WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    }
    
    @Override
	protected Document messageRecive(WechatReceiveEventUnSubscribeMessage unSubscribeMessage) throws ServiceException {
    	//根据OpenId查询对应的信息
        String wechatOpenId = unSubscribeMessage.getFromUserName();
        WechatUserDto wechatUserDto = new WechatUserDto();
        wechatUserDto.setOpenId(wechatOpenId);
        wechatUserDto = WechatUserHandler.getUserInfo(wechatUserDto, wechatAccessTokenDao.get());
        
        //封装WechatUser对象，插入數據到客戶端
        WechatUser wechatUser = new WechatUser();
        try {
            BeanUtils.copyProperties(wechatUser, wechatUserDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("Convert WechatUserDto To WechatUser Error", e);
        }
        thirdpartyUserService.delete(wechatUser);
        
        String responseToUserName=unSubscribeMessage.getFromUserName();
		String responseFromUserName=unSubscribeMessage.getToUserName();
        WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
		textMessageResponse.setContent("希望下次再次关注公众号");
		return WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
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
