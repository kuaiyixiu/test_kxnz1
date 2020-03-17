package com.kyx.biz.wechat.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.wechat.model.WechatConfig;
@Repository("wechatConfigMapper")
public interface WechatConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatConfig record);

    int insertSelective(WechatConfig record);

    WechatConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatConfig record);

    int updateByPrimaryKey(WechatConfig record);

	WechatConfig getByAppId(@Param(value = "appId") String appId);
}