/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.sunjiesh.thirdpartdemo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信消息对象
 * 
 * @author tomsun
 */
public class WechatMessage implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7009530410737807581L;

	public WechatMessage() {
	}

	private String toUserName;

	private String fromUserName;

	/**
	 * 创建时间的时间戳
	 */
	private Date createTime;

	/**
	 * 消息类型
	 */
	private String msgType;

	/**
	 * 其它属性
	 */
	private Map<String,Object> otherProperties=new HashMap<String,Object>();

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Map<String, Object> getOtherProperties() {
		return otherProperties;
	}

	public void setOtherProperties(Map<String, Object> otherProperties) {
		this.otherProperties = otherProperties;
	}
	
}
