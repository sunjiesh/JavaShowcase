package cn.com.sunjiesh.thirdpartdemo.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import cn.com.sunjiesh.wechat.service.WechatAccessTokenService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 微信任务服务
 * 
 * @author tomsun
 *
 */
public class WechatAccessTokenJob extends QuartzJobBean {

	private static Logger LOGGER = LoggerFactory.getLogger(WechatAccessTokenJob.class);

	private WechatAccessTokenService accessTokenService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
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
