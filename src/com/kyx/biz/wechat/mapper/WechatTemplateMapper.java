package com.kyx.biz.wechat.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.wechat.model.WechatTemplate;
@Repository("wechatTemplateMapper")
public interface WechatTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatTemplate record);

    int insertSelective(WechatTemplate record);

    WechatTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatTemplate record);

    int updateByPrimaryKey(WechatTemplate record);

	WechatTemplate selectByWechatIdAndType(@Param(value = "wechatId") Integer wechatId, @Param(value = "type") Integer type);
}