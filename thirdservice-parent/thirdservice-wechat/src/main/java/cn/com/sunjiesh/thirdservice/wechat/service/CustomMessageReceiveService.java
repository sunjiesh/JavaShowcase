package cn.com.sunjiesh.thirdservice.wechat.service;

import org.dom4j.Document;
import org.springframework.stereotype.Service;

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
import cn.com.sunjiesh.wechat.service.AbstractWechatMessageReceiveService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

@Service
public class CustomMessageReceiveService extends AbstractWechatMessageReceiveService{

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventClickMessageRequest clickMessage) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
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

}
