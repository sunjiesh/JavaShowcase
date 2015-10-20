package cn.com.sunjiesh.thirdpartdemo.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.com.sunjiesh.wechat.service.AccessTokenService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 微信任务服务
 * @author tomsun
 *
 */
public class WechatAccessTokenJob extends QuartzJobBean{

	private static Logger logger=LoggerFactory.getLogger(WechatAccessTokenJob.class);
	
	private AccessTokenService accessTokenService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		String accessToken = null;
		try {
			accessToken = accessTokenService.getAccessTokenAndSaveToLocal();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("accessToken="+accessToken);
	}

	public void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}
	
	

}
