package com.kyx.biz.wechat.mapper;

import com.kyx.biz.wechat.model.WechatSendRecord;

public interface WechatSendRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatSendRecord record);

    int insertSelective(WechatSendRecord record);

    WechatSendRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatSendRecord record);

    int updateByPrimaryKey(WechatSendRecord record);
}