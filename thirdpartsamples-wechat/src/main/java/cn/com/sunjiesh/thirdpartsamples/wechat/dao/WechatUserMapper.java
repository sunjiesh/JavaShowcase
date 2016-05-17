package cn.com.sunjiesh.thirdpartsamples.wechat.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

import cn.com.sunjiesh.thirdpartdemo.model.WechatUser;
import cn.com.sunjiesh.thirdpartdemo.model.WechatUserExample;

public interface WechatUserMapper {

	@Insert("insert into wechat_user (id, city, country, create_time, create_user, head_img_url, language, nickname, open_id, province, remark, sex, subscribe, subscribe_time, union_id, update_time, update_user)values (#{id,jdbcType=BIGINT}, #{city,jdbcType=VARCHAR}, #{country,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=VARCHAR}, #{headImgUrl,jdbcType=VARCHAR}, #{language,jdbcType=VARCHAR}, #{nickname,jdbcType=VARCHAR}, #{openId,jdbcType=VARCHAR}, #{province,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR}, #{sex,jdbcType=INTEGER}, #{subscribe,jdbcType=VARCHAR}, #{subscribeTime,jdbcType=TIMESTAMP}, #{unionId,jdbcType=VARCHAR}, #{updateTime,jdbcType=TIMESTAMP}, #{updateUser,jdbcType=VARCHAR})")
	void insert(WechatUser wechatUser);

	@Delete("<script>delete from wechat_user   <if test=\"_parameter != null\" >   <where >    <foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\" >      <if test=\"criteria.valid\" >        <trim prefix=\"(\" suffix=\")\" prefixOverrides=\"and\" >          <foreach collection=\"criteria.criteria\" item=\"criterion\" >            <choose >              <when test=\"criterion.noValue\" >                and ${criterion.condition}              </when>              <when test=\"criterion.singleValue\" >                and ${criterion.condition} #{criterion.value}              </when>              <when test=\"criterion.betweenValue\" >                and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}              </when>              <when test=\"criterion.listValue\" >                and ${criterion.condition}                <foreach collection=\"criterion.value\" item=\"listItem\" open=\"(\" close=\")\" separator=\",\" >                  #{listItem}                </foreach>              </when>            </choose>          </foreach>        </trim>      </if>    </foreach>  </where></if></script>")
	void deleteByExample(WechatUserExample example);

}
