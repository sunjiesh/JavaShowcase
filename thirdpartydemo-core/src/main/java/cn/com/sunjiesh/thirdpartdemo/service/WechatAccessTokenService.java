package cn.com.sunjiesh.thirdpartdemo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.wechat.common.WechatConfigProperties;
import cn.com.sunjiesh.wechat.dao.IWechatAccessTokenDao;
import cn.com.sunjiesh.wechat.handler.WechatAccessTokenHandler;
import cn.com.sunjiesh.wechat.service.AbstractWechatAccessTokenService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 获取AccessToken服务类
 * @author tom
 *
 */
@Service
public class WechatAccessTokenService extends AbstractWechatAccessTokenService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatAccessTokenService.class);

	@Autowired
    protected IWechatAccessTokenDao wechatAccessTokenDao;
	
	/**
	 * Get Wechat Access Token And Save
	 * @return
	 * @throws ServiceException
	 */
	public String getAccessTokenAndSaveToLocal() throws ServiceException{
		String accessToken=WechatAccessTokenHandler.getAccessToken();
		wechatAccessTokenDao.save(accessToken);
		return accessToken;
	}
	
	/**
	 * Get Wechat Access Token And Save To Local Config file
	 * @return
	 * @throws ServiceException
	 */
	public String getAccessTokenAndSaveToLocalConfig() throws ServiceException{
		String accessToken=WechatAccessTokenHandler.getAccessToken();
		FileOutputStream fos=null;
		try {
			fos=new FileOutputStream(new File(WechatConfigProperties.getProperty(WechatConfigProperties.LOCAL_PATH),"WechatAccessToken"));
			fos.write(accessToken.getBytes());
		} catch (FileNotFoundException e) {
			LOGGER.error("Get Wechat Access Token From LocalConfig Failure",e);
		} catch (IOException e) {
			LOGGER.error("Get Wechat Access Token From LocalConfig Failure",e);
		}finally{
			try {
				if(fos!=null){
					fos.close();
				}
				
			} catch (IOException e) {
				LOGGER.error("Get Wechat Access Token From LocalConfig Failure",e);
			}
		}
		return accessToken;
	}

	@Override
	public String getAccessTokenAndSave() throws ServiceException {
		return getAccessTokenAndSaveToLocal();
	}
}
