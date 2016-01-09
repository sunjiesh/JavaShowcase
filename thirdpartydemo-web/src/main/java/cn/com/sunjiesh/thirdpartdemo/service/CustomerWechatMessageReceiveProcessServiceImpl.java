package cn.com.sunjiesh.thirdpartdemo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.sunjiesh.thirdpartdemo.common.ThirdpartyDemoConstants;
import cn.com.sunjiesh.thirdpartdemo.helper.tuling123.TulingConstants;
import cn.com.sunjiesh.thirdpartdemo.helper.tuling123.TulingHelper;
import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.thirdpartdemo.response.tuling.TulingResponse;
import cn.com.sunjiesh.utils.thirdparty.base.HttpService;
import cn.com.sunjiesh.wechat.dao.IWechatAccessTokenDao;
import cn.com.sunjiesh.wechat.handler.WechatMediaHandler;
import cn.com.sunjiesh.wechat.handler.WechatUserHandler;
import cn.com.sunjiesh.wechat.model.user.WechatUserDto;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventSubscribeMessageRequest;
import cn.com.sunjiesh.wechat.model.request.event.WechatEventUnSubscribeMessageRequest;
import cn.com.sunjiesh.wechat.model.request.message.WechatNormalTextMessageRequest;
import cn.com.sunjiesh.wechat.model.response.media.WechatUploadMediaResponse;
import cn.com.sunjiesh.wechat.service.WechatMessageReceiveProcessServiceImpl;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CustomerWechatMessageReceiveProcessServiceImpl extends WechatMessageReceiveProcessServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerWechatMessageReceiveProcessServiceImpl.class);

    private ThirdpartyUserService thirdpartyUserService;

    protected IWechatAccessTokenDao wechatAccessTokenDao;

    private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    @Override
    public Document messageRecive(WechatEventSubscribeMessageRequest wechatMessage) throws ServiceException {

        //根据OpenId查询对应的信息
        String wechatOpenId = wechatMessage.getFromUserName();
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

        Document document = super.messageRecive(wechatMessage);
        return document;
    }

    @Override
    public Document messageRecive(WechatEventUnSubscribeMessageRequest wechatMessage) throws ServiceException {
        Document document = super.messageRecive(wechatMessage);
        //查詢OpenId並創建用戶對象
        String wechatOpenId = wechatMessage.getFromUserName();
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(wechatOpenId);
        thirdpartyUserService.delete(wechatUser);

        return document;
    }

    @Override
    public Document messageRecive(WechatNormalTextMessageRequest wechatMessage) throws ServiceException {
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
            final TulingResponse response = new TulingHelper().callTuling(message);
            int tulingCode = response.getCode();
            switch (tulingCode) {
                case TulingConstants.TULING_RESPONSE_CODE_TEXT:
                    return replayTextMessage(toUserName, fromUserName, response.getText());
                case TulingConstants.TULING_RESPONSE_CODE_LINK:
                    //返回圖片需要處理時間，直接返回NULL值，通過異步進行處理發送消息
                    scheduledThreadPool.submit(() -> {
                        try {
                            String url = response.getUrl();
                            String text = response.getText();
                            //下載圖片並且上傳到微信上，生成圖文消息
                            File tmpFile = new HttpService().getFileResponseFromHttpGetMethod(url);
                            if (tmpFile != null) {
                                String fileName = tmpFile.getName().toLowerCase();
                                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                                if (fileType.contains("jpg") || fileType.contains("jpeg") || fileType.contains("png")) {
                                    //僅支持的圖片類型
                                    WechatUploadMediaResponse uploadMediaResponse = WechatMediaHandler.uploadMedia(tmpFile, "image", wechatAccessTokenDao.get());
                                    String mediaId = uploadMediaResponse.getMediaId();
                                    LOGGER.debug("微信臨時圖片素材上傳成功，mediaId=" + mediaId);
                                }

                            }
                        } catch (ServiceException ex) {
                            LOGGER.error("Server Error", ex);
                        }
                    });

                    return replayTextMessage(toUserName, fromUserName, response.getText() + response.getUrl());
                default:
                    return replayErrorTextMessage(toUserName, fromUserName, message);
            }
        }
        switch (TextMessageReceiveContentEnum.valueOf(message.toUpperCase())) {
            case TEXT:
                return replayTextMessage(toUserName, fromUserName);
            case IMAGE:
                return replayImageMessage(toUserName, fromUserName, wechatAccessTokenDao.get());
            case VOICE:
                return replayVoiceMessage(toUserName, fromUserName, wechatAccessTokenDao.get());
            case VIDEO:
                return replayVideoMessage(toUserName, fromUserName, wechatAccessTokenDao.get());
            case MUSIC:
                return replayMusicMessage(toUserName, fromUserName, wechatAccessTokenDao.get());
            case NEWS:
                return replayMultiNewsMessage(toUserName, fromUserName);
            default: {
                TulingResponse response = new TulingHelper().callTuling(message);
                int tulingCode = response.getCode();
                switch (tulingCode) {
                    case TulingConstants.TULING_RESPONSE_CODE_TEXT:
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

    public IWechatAccessTokenDao getWechatAccessTokenDao() {
        return wechatAccessTokenDao;
    }

    public void setWechatAccessTokenDao(IWechatAccessTokenDao wechatAccessTokenDao) {
        this.wechatAccessTokenDao = wechatAccessTokenDao;
    }

}
