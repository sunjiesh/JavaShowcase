package cn.com.sunjiesh.thirdpartdemo.service;

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
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalTextMessageRequest;
import cn.com.sunjiesh.wechat.service.AbstractWechatMessageReceiveService;
import cn.com.sunjiesh.wechat.service.IWechatMessageReceiveProcessService;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sunjiesh.xcutils.common.base.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomMessageReceiveService extends AbstractWechatMessageReceiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMessageReceiveService.class);
    
    @Autowired
    private IWechatMessageReceiveProcessService messageReceiveProcessService;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalImageMessage imageMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalVoiceMessage voiceMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalVideoMessage videoMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalShortvideoMessage shortVodeoMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalLocationMessage locationMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveNormalLinkMessage linkMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventClickMessage clickMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventLocationMessage locationMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventScanMessage scanMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventSubscribeMessage subscribeMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventViewMessage viewMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventScancodeCommonMessage scanCodePushMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventWeixinMessage picPhotoOrAlbumEventMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventPicSysphotoMessage picSysphotoMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventPicPhotoOrAlbumMessage picPhotoOrAlbumEventMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Document messageRecive(WechatReceiveEventPicCommonMessage wechatMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
