package com.kyx.biz.pay.mapper;

import com.kyx.biz.pay.model.CommunityOrder;

public interface CommunityOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommunityOrder record);

    int insertSelective(CommunityOrder record);

    CommunityOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommunityOrder record);

    int updateByPrimaryKey(CommunityOrder record);
}