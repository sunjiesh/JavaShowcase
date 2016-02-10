package cn.com.sunjiesh.thirdpartdemo.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.wechat.service.WechatAccessTokenService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 微信任务服务
 * 
 * @author tomsun
 *
 */
@Service
public class WechatAccessTokenJob{

	private static Logger LOGGER = LoggerFactory.getLogger(WechatAccessTokenJob.class);

	@Autowired
	private WechatAccessTokenService accessTokenService;

	public void getAccessTokenAndSaveToLocal(){
		String accessToken = null;
		try {
			accessToken = accessTokenService.getAccessTokenAndSaveToLocal();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		LOGGER.debug("accessToken=" + accessToken);
	}
	
	public void setAccessTokenService(WechatAccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

	
}
