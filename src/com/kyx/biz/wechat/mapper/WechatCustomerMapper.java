package com.kyx.biz.wechat.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.wechat.model.WechatCustomer;

@Repository("wechatCustomerMapper")
public interface WechatCustomerMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(WechatCustomer record);

	int insertSelective(WechatCustomer record);

	WechatCustomer selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(WechatCustomer record);

	int updateByPrimaryKey(WechatCustomer record);

	WechatCustomer getByAppidAndOpenId(@Param(value = "appId") String appId,
			@Param(value = "openId") String openId);

	WechatCustomer selectByCardNo(@Param(value = "cardNo") String cardNo);
}