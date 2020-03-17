package com.kyx.biz.wechat.mapper;

import com.kyx.biz.wechat.model.WechatCommunity;
import com.kyx.biz.wechat.model.WechatCustomer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("wechatCommunityMapper")
public interface WechatCommunityMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(WechatCommunity record);

	int insertSelective(WechatCommunity record);

	WechatCommunity selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(WechatCommunity record);

	int updateByPrimaryKey(WechatCommunity record);

	WechatCommunity getByAppidAndOpenId(@Param(value = "appId") String appId,
                                       @Param(value = "openId") String openId);

	WechatCommunity selectByUserName(@Param(value = "userName") String userName);
}