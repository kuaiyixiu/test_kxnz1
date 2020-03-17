package com.kyx.biz.pay.mapper;

import com.kyx.biz.pay.model.CommunityMeal;

public interface CommunityMealMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommunityMeal record);

    int insertSelective(CommunityMeal record);

    CommunityMeal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommunityMeal record);

    int updateByPrimaryKey(CommunityMeal record);
}