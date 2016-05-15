package cn.com.sunjiesh.thirdpartsamples.wechat.dao;

import org.apache.ibatis.annotations.Insert;

import cn.com.sunjiesh.thirdpartdemo.model.WechatReceiveMessage;


public interface WechatReceiveMessageMapper {

	@Insert("insert into wechat_receive_message (id, from_user, message_content, message_type, status, create_user,create_time, update_user, update_time)values (#{id,jdbcType=BIGINT}, #{fromUser,jdbcType=VARCHAR}, #{messageContent,jdbcType=VARCHAR}, #{messageType,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, #{createUser,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, #{updateUser,jdbcType=VARCHAR}, #{updateTime,jdbcType=TIMESTAMP})")
	public void insert(WechatReceiveMessage wechatReceiveMessage);
	
}
