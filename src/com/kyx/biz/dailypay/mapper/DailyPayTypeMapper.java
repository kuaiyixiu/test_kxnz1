package com.kyx.biz.dailypay.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.dailypay.model.DailyPayType;
@Repository("dailyPayTypeMapper")
public interface DailyPayTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DailyPayType record);

    DailyPayType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DailyPayType record);
    
    /**
     * 查询收支类型列表
     * @param record
     * @return
     */
    List<DailyPayType> getList(DailyPayType record);
    
    
}