package cn.com.sunjiesh.thirdpartdemo.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import cn.com.sunjiesh.wechat.service.AccessTokenService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 微信任务服务
 * @author tomsun
 *
 */
//@Service
public class WechatAccessTokenJob extends QuartzJobBean{

	private static Logger logger=LoggerFactory.getLogger(WechatAccessTokenJob.class);
	
//	@Autowired
	private AccessTokenService accessTokenService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			String accessToken=accessTokenService.getAccessToken();
			logger.debug("accessToken="+accessToken);
			WechatConfig.ACCESS_TOKEN=accessToken;
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}
	
	

}
