package com.kyx.biz.payRecord.mapper;

import org.springframework.stereotype.Repository;

import com.kyx.biz.payRecord.model.MealPayRecord;

@Repository("mealPayRecordMapper")
public interface MealPayRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(MealPayRecord record);

    MealPayRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MealPayRecord record);

}