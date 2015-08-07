/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.sunjiesh.thirdpartdemo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信消息对象
 * 
 * @author tomsun
 */
public class WechatMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8508663335613598179L;

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

	private String content;

	/**
	 * 事件信息
	 */
	private String event;

	/**
	 * 群发的消息ID
	 */
	private String msgId;

	/**
	 * 群发的结构，为“send success”或“send fail”或“err(num)”。但send
	 * success时，也有可能因用户拒收公众号的消息、系统错误等原因造成少量用户接收失败。err(num)是审核失败的具体原因，可能的情况如下：
	 */
	private String status;

	/**
	 * group_id下粉丝数；或者openid_list中的粉丝数
	 */
	private Integer totalCount;

	/**
	 * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，FilterCount =
	 * SentCount + ErrorCount
	 */
	private Integer filterCount;

	/**
	 * 发送成功的粉丝数
	 */
	private Integer sendCount;

	/**
	 * 发送失败的粉丝数
	 */
	private Integer errorCount;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(Integer filterCount) {
		this.filterCount = filterCount;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	@Override
	public String toString() {
		return "WechatMessage{" + "toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", msgType=" + msgType + ", content=" + content
				+ ", msgId=" + msgId + '}';
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
