package cn.com.sunjiesh.thirdpartsamples.wechat.dao;

import org.apache.ibatis.annotations.Insert;

import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;

public interface WechatUserMapper {

	@Insert("insert into wechat_user (id, city, country, create_time, create_user, head_img_url, language, nickname, open_id, province, remark, sex, subscribe, subscribe_time, union_id, update_time, update_user)values (#{id,jdbcType=BIGINT}, #{city,jdbcType=VARCHAR}, #{country,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, #{headImgUrl,jdbcType=VARCHAR}, #{language,jdbcType=VARCHAR}, #{nickname,jdbcType=VARCHAR}, #{openId,jdbcType=VARCHAR}, #{province,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, #{sex,jdbcType=INTEGER}, #{subscribe,jdbcType=VARCHAR}, #{subscribeTime,jdbcType=TIMESTAMP}, #{unionId,jdbcType=VARCHAR}, #{updateTime,jdbcType=TIMESTAMP}, #{updateUser,jdbcType=VARCHAR})")
	void insert(WechatUser wechatUser);

}
