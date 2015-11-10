package cn.com.sunjiesh.thirdpartdemo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sunjiesh.thirdpartdemo.common.ThirdpartyDemoConstants;
import cn.com.sunjiesh.thirdpartdemo.helper.tuling123.TulingHelper;
import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.thirdpartdemo.response.tuling.TulingResponse;
import cn.com.sunjiesh.wechat.entity.message.WechatReceiveNormalTextMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventSubscribeMessage;
import cn.com.sunjiesh.wechat.entity.message.event.WechatReceiveEventUnSubscribeMessage;
import cn.com.sunjiesh.wechat.model.dto.user.WechatUserDto;
import cn.com.sunjiesh.wechat.service.WechatMessageReceiveProcessServiceImpl;
import cn.com.sunjiesh.wechat.service.WechatUserService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

public class CustomerWechatMessageReceiveProcessServiceImpl extends WechatMessageReceiveProcessServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerWechatMessageReceiveProcessServiceImpl.class);

    private ThirdpartyUserService thirdpartyUserService;

    private WechatUserService wechatUserService;

    @Override
    public Document messageRecive(WechatReceiveEventSubscribeMessage wechatMessage) throws ServiceException {

        //根据OpenId查询对应的信息
        String wechatOpenId = wechatMessage.getFromUserName();
        WechatUserDto wechatUserDto = new WechatUserDto();
        wechatUserDto.setOpenId(wechatOpenId);
        wechatUserDto = wechatUserService.getUserInfo(wechatUserDto);

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

        Document document = super.messageRecive(wechatMessage);
        return document;
    }

    @Override
    public Document messageRecive(WechatReceiveEventUnSubscribeMessage wechatMessage) throws ServiceException {
        Document document = super.messageRecive(wechatMessage);
        //查詢OpenId並創建用戶對象
        String wechatOpenId = wechatMessage.getFromUserName();
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(wechatOpenId);
        thirdpartyUserService.delete(wechatUser);

        return document;
    }

    @Override
    public Document messageRecive(WechatReceiveNormalTextMessage wechatMessage) throws ServiceException {
        LOGGER.debug("receive a WechatReceiveNormalTextMessage request ");
        
        LOGGER.debug("receive a messageRecive request ");
        String toUserName = wechatMessage.getFromUserName();
        String fromUserName = wechatMessage.getToUserName();

        String message = wechatMessage.getContent();
        boolean continueReplay = false;
        for (TextMessageReceiveContentEnum contentEnum : TextMessageReceiveContentEnum.values()) {
            if (contentEnum.name().equalsIgnoreCase(message)) {
                continueReplay = true;
            }
        }
        if (!continueReplay) {
            TulingResponse response = new TulingHelper().callTuling(message);
            int tulingCode = response.getCode();
            switch (tulingCode) {
                case 100000:{
                    String replayMessage=response.getText();
                    return replayTextMessage(toUserName, fromUserName,replayMessage);
                }
                    
                default: 
                    return replayErrorTextMessage(toUserName, fromUserName, message);
            }
        }
        switch (TextMessageReceiveContentEnum.valueOf(message.toUpperCase())) {
            case TEXT:
                return replayTextMessage(toUserName, fromUserName);
            case IMAGE:
                return replayImageMessage(toUserName, fromUserName);
            case VOICE:
                return replayVoiceMessage(toUserName, fromUserName);
            case VIDEO:
                return replayVideoMessage(toUserName, fromUserName);
            case MUSIC:
                return replayMusicMessage(toUserName, fromUserName);
            case NEWS:
                return replayMultiNewsMessage(toUserName, fromUserName);
            default: {
                TulingResponse response = new TulingHelper().callTuling(message);
                int tulingCode = response.getCode();
                switch (tulingCode) {
                    case 100000:
                        return replayTextMessage(toUserName, fromUserName);
                    default: 
                        return replayErrorTextMessage(toUserName, fromUserName, message);
                }
            }
        }

    }

    public ThirdpartyUserService getThirdpartyUserService() {
        return thirdpartyUserService;
    }

    public void setThirdpartyUserService(ThirdpartyUserService thirdpartyUserService) {
        this.thirdpartyUserService = thirdpartyUserService;
    }

    public WechatUserService getWechatUserService() {
        return wechatUserService;
    }

    public void setWechatUserService(WechatUserService wechatUserService) {
        this.wechatUserService = wechatUserService;
    }
    
    
}
