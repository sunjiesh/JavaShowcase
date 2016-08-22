package cn.com.sunjiesh.thirdservice.wechat.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.sunjiesh.thirdservice.wechat.common.WechatEventClickMessageEventkeyEnum;
import cn.com.sunjiesh.thirdservice.wechat.common.WechatReceiveMessageConstants;
import cn.com.sunjiesh.thirdservice.wechat.dao.RedisWechatMessageDao;
import cn.com.sunjiesh.thirdservice.wechat.domain.response.TulingNewsResponse;
import cn.com.sunjiesh.thirdservice.wechat.helper.tuling123.TulingConstants;
import cn.com.sunjiesh.thirdservice.wechat.helper.tuling123.TulingHelper;
import cn.com.sunjiesh.wechat.dao.IWechatAccessTokenDao;
import cn.com.sunjiesh.wechat.entity.message.WechatSendTemplateMessage;
import cn.com.sunjiesh.wechat.handler.WechatMediaHandler;
import cn.com.sunjiesh.wechat.handler.WechatTemplateMessageHandler;
import cn.com.sunjiesh.wechat.handler.WechatUserHandler;
import cn.com.sunjiesh.wechat.helper.WechatMessageConvertDocumentHelper;
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
import cn.com.sunjiesh.wechat.model.response.media.WechatUploadMediaResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayImageMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayNewsMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayTextMessageResponse;
import cn.com.sunjiesh.wechat.model.response.message.WechatReceiveReplayVoiceMessageResponse;
import cn.com.sunjiesh.wechat.model.user.WechatUserDto;
import cn.com.sunjiesh.wechat.service.AbstractWechatMessageReceiveService;
import cn.com.sunjiesh.xcutils.common.base.ServiceException;

/**
 * 自定义消息接收处理
 * @author Tom
 *
 */
@Service
public class CustomMessageReceiveService extends AbstractWechatMessageReceiveService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomMessageReceiveService.class);

	private static final String LAST_IMAGE_MESSAGE_MEDIA_ID = "lastImageMessageMediaId";
    
    private static final String LAST_VOICE_MESSAGE_MEDIA_ID = "lastVoiceMessageMediaId";
    
	private static final String LAST_VIDEO_MESSAGE_MEDIA_ID = "lastVideoMessageMediaId";
	
	private static final String LAST_SHORT_VIDEO_MESSAGE_MEDIA_ID = "lastShortVideoMessageMediaId";
	
	private static final Long REDIS_SAVE_TIME_OUT=24L;
	
	@Autowired
    private RedisWechatMessageDao redisWechatMessageDao;
	
	@Autowired
	protected IWechatAccessTokenDao wechatAccessTokenDao;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
//	@Autowired
//    private ThirdpartyUserService thirdpartyUserService;

    private ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
    
    
	
	@Override
	protected Document messageReceive(Document doc4j) throws ServiceException {
		Document respDoc=super.messageReceive(doc4j);
		//保存接收与发送消息，暂时存入Reids中
		stringRedisTemplate.execute(new RedisCallback<Long>(){

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnection jedis = (StringRedisConnection)connection;
				jedis.select(1);
				String key=System.currentTimeMillis()+"-receive";
				jedis.set(key, doc4j.asXML());
				if(respDoc!=null){
					key=System.currentTimeMillis()+"-resp";
					jedis.set(key, respDoc.asXML());
				}
				
				return null;
			}
			
		});
		return respDoc;
	}

	@Override
	protected Document messageReceive(WechatEventLocationSelectMessageRequest wechatMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Document messageRecive(WechatNormalTextMessageRequest textMessage) throws ServiceException {
		LOGGER.debug("receive a WechatReceiveNormalTextMessage request ");

    	String responseToUserName=textMessage.getFromUserName();
		String responseFromUserName=textMessage.getToUserName();
        String toUserName = textMessage.getFromUserName();
        String fromUserName = textMessage.getToUserName();

        String message = textMessage.getContent();
        String wechatReceiveMessageFromUserName=textMessage.getFromUserName();
        saveReceiveMessage(wechatReceiveMessageFromUserName, message, WechatReceiveMessageConstants.MESSAGE_TYPE_TEXT);
        
        final JSONObject response = new TulingHelper().callTuling(message);
        int tulingCode = response.getIntValue("code");
		switch (tulingCode) {
            case TulingConstants.TULING_RESPONSE_CODE_TEXT:
            	WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
        		textMessageResponse.setContent(response.getString("text"));
        		return WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
            case TulingConstants.TULING_RESPONSE_CODE_LINK:
                //返回圖片需要處理時間，直接返回NULL值，通過異步進行處理發送消息
                scheduledThreadPool.submit(() -> {
                    try {
                        String url = response.getString("url");
                        String text = response.getString("text");
                        LOGGER.debug("text="+text);
                        
                        //判断返回格式内容
                        HttpClient httpClient=HttpClients.createDefault();
                        HttpGet httGet=new HttpGet(url);
                        HttpResponse httpResponse = httpClient.execute(httGet);
                        Header contentType=httpResponse.getHeaders("Content-Type")[0];
                        if(contentType.getValue().contains("text/html")){
                        	LOGGER.debug("返回属于HTML，不进行处理，直接返回URL");
                        	return replayTextMessage(responseToUserName, responseFromUserName,  text+"，请访问:"+url);
                        }
                    	//下載圖片並且上傳到微信上，生成圖文消息
                        File tmpFile = this.getFileResponseFromHttpGetMethod(httpResponse);
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
                    return replayTextMessage(responseToUserName, responseFromUserName,  response.getString("text"));
                });

            case TulingConstants.TULING_RESPONSE_CODE_NEWS:{
            	String url = response.getString("url");
                String text = response.getString("text");
                if(!response.containsKey("list")){
                	//判断返回格式内容
                	HttpClient httpClient=HttpClients.createDefault();
                    HttpGet httGet=new HttpGet(url);
                    HttpResponse httpResponse = null;
					try {
						httpResponse = httpClient.execute(httGet);
						Header contentType=httpResponse.getHeaders("Content-Type")[0];
	                    if(contentType.getValue().contains("text/html")){
	                    	LOGGER.debug("返回属于HTML，不进行处理，直接返回URL");
	                    	return replayTextMessage(responseToUserName, responseFromUserName,  text+"，请访问:"+url);
	                    }
	                    return replayTextMessage(responseToUserName, responseFromUserName,  response.getString(text+"，请访问:"+url));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                  
                }
                JSONArray listJsonArr=response.getJSONArray("list");
                List<TulingNewsResponse> newsResponseList=new ArrayList<TulingNewsResponse>();
                for(int listJsonIndex=0;listJsonIndex<listJsonArr.size();listJsonIndex++){
                	JSONObject jsonObject=listJsonArr.getJSONObject(listJsonIndex);
                	String name=jsonObject.getString("article");
                	String icon=jsonObject.getString("icon");
                	String info=jsonObject.getString("source");
                	String detailurl=jsonObject.getString("detailurl");
                	LOGGER.debug("name="+name);
                	LOGGER.debug("icon="+icon);
                	LOGGER.debug("info="+info);
                	LOGGER.debug("detailurl="+detailurl);
                	
                	TulingNewsResponse newsResponse=new TulingNewsResponse();
                	newsResponse.setArticle(name);
                	newsResponse.setIcon(icon);
                	newsResponse.setSource(info);
                	newsResponse.setDetailurl(detailurl);
                	newsResponseList.add(newsResponse);
                }
                
                //图文消息
                WechatReceiveReplayNewsMessageResponse newsReplayMessage = new WechatReceiveReplayNewsMessageResponse(responseToUserName, responseFromUserName);
                for(int index=0;index<Math.min(newsResponseList.size(), 10);index++){
                	TulingNewsResponse newsResponse=newsResponseList.get(index);
                	WechatReceiveReplayNewsMessageResponse.WechatReceiveReplayNewsMessageResponseItem newsReplayMessageItem1 = newsReplayMessage.new WechatReceiveReplayNewsMessageResponseItem();
                    newsReplayMessageItem1.setDescription(newsResponse.getSource());
                    newsReplayMessageItem1.setTitle(newsResponse.getArticle());
                    newsReplayMessageItem1.setUrl(newsResponse.getDetailurl());
                    newsReplayMessageItem1.setPicUrl(newsResponse.getIcon());
                    newsReplayMessage.addItem(newsReplayMessageItem1);
                }
                
                Document respDoc=WechatMessageConvertDocumentHelper.newsMessageResponseToDocument(newsReplayMessage);
                return respDoc;
                
            }
            default:
                return respError(toUserName, fromUserName);
        }
	}

	@Override
	protected Document messageRecive(WechatNormalImageMessageRequest imageMessage) throws ServiceException {
		String responseToUserName=imageMessage.getFromUserName();
		String responseFromUserName=imageMessage.getToUserName();
		String mediaId=imageMessage.getMediaId();
		redisWechatMessageDao.save(LAST_IMAGE_MESSAGE_MEDIA_ID, mediaId,REDIS_SAVE_TIME_OUT);
		
		final String content = "图片已经上传，midiaId为="+mediaId;
		return replayTextMessage(responseToUserName, responseFromUserName, content);
	}

	@Override
	protected Document messageRecive(WechatNormalVoiceMessageRequest voiceMessage) {
		String responseToUserName=voiceMessage.getFromUserName();
		String responseFromUserName=voiceMessage.getToUserName();
		String mediaId=voiceMessage.getMediaId();
		redisWechatMessageDao.save(LAST_VOICE_MESSAGE_MEDIA_ID, mediaId,REDIS_SAVE_TIME_OUT);
		String content = "语音已经上传，midiaId为="+mediaId;
		return replayTextMessage(responseToUserName, responseFromUserName, content);
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
		return null;
	}

	@Override
	protected Document messageRecive(WechatEventClickMessageRequest clickMessage) throws ServiceException {
		//返回对象
    	Document respDoc=null;
    	
    	final String eventKey=clickMessage.getEventKey();
    	final String responseToUserName = clickMessage.getFromUserName();
		final String responseFromUserName = clickMessage.getToUserName();
    	LOGGER.debug("EventKey="+eventKey);
    	WechatEventClickMessageEventkeyEnum eventKeyEnum=WechatEventClickMessageEventkeyEnum.valueOf(eventKey);
    	
    	//保存到DB
    	saveReceiveMessage(clickMessage.getFromUserName(), clickMessage.getEventKey(), WechatReceiveMessageConstants.MESSAGE_TYPE_CLICK);
    	
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
    	case GetTemplateMessage:{
    		WechatUserDto user = new WechatUserDto();
            user.setOpenId(responseToUserName);
            user=WechatUserHandler.getUserInfo(user, wechatAccessTokenDao.get());
            
            WechatSendTemplateMessage message = new WechatSendTemplateMessage();
            message.setTemplateId("RhnX8IXCkY2oStIvdTcJcjJDpZcglUUMbeb7dXJ6h1E");
            message.setUrl("http://www.baidu.com");
            message.setTopColor("#FF0000");
            WechatSendTemplateMessage.WechatSendTemplateMessageData messageData1 = message.new WechatSendTemplateMessageData();
            messageData1.setKeyName("Nickname");
            messageData1.setValue(user.getNickname());
            messageData1.setColor("#173177");
            WechatSendTemplateMessage.WechatSendTemplateMessageData messageData2 = message.new WechatSendTemplateMessageData();
            messageData2.setKeyName("Time");
            messageData2.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            messageData2.setColor("#173177");
            message.getData().add(messageData1);
            message.getData().add(messageData2);
            WechatTemplateMessageHandler.send(user, message, wechatAccessTokenDao.get());
            WechatReceiveReplayTextMessageResponse textMessageResponse=new WechatReceiveReplayTextMessageResponse(responseToUserName, responseFromUserName);
    		textMessageResponse.setContent("模板消息已发送");
    		respDoc=WechatMessageConvertDocumentHelper.textMessageResponseToDocument(textMessageResponse);
    	};break;
    	default:{
    		respDoc=respError(responseToUserName, responseFromUserName);
    	}
    	}
    	
		return respDoc;
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


	/**
     * 保存消息
     * @param wechatReceiveMessageFromUserName
     * @param message
     * @param messageType
     */
    public void saveReceiveMessage(String wechatReceiveMessageFromUserName, String message, final String messageType) {
		//TODO need to save 
	}
    
    /**
	 * 下载文件
	 * @param httpResponse HttpResponse对象
	 * @return
	 * @throws ServiceException
	 */
	public File getFileResponseFromHttpGetMethod(HttpResponse httpResponse)
			throws ServiceException {
		File file=null;
		try {
			StatusLine httpStatusLine = httpResponse.getStatusLine();
			
			int statusCode = httpStatusLine.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new ServiceException("调用服务失败");
			}
			//判断Content-disposition，是否为流数据
			if(!httpResponse.containsHeader("Content-disposition")){
				throw new ServiceException("调用服务失败，无法下载文件");
			}
			Header contentDisposition=httpResponse.getHeaders("Content-disposition")[0];
			String contentDispositionValue=contentDisposition.getValue();
			String fileName=contentDispositionValue.replace("attachment; filename=\"", "");
			fileName=fileName.replace("\"", "");
			
			HttpEntity httpEntity = httpResponse.getEntity();
			
		    InputStream is=httpEntity.getContent();
			if(!StringUtils.isEmpty(fileName)){
				//下载文件，如果文件存在，则删除重新生成文件
				file=new File("/var/tmp",fileName);
				if(file.exists()){
					file.delete();
				}
				FileOutputStream fos=new FileOutputStream(file,false);
				FileChannel fileChannel = fos.getChannel();
			    ByteBuffer byteBuffer = null;
			    byte[] tempBytes=new byte[4096];
			    while(is.read(tempBytes)>0){
			    	byteBuffer = ByteBuffer.wrap(tempBytes);
			        fileChannel.write(byteBuffer);         
			    }
			    if(fos!=null){
			    	fos.close();
			    }
			    fileChannel.close();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new ServiceException("调用服务失败，无法下载文件");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ServiceException("调用服务失败，无法下载文件");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("调用服务失败，无法下载文件");
		}
		
		return file;
	}
}
