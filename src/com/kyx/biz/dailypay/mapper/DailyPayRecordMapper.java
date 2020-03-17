package com.kyx.biz.dailypay.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.dailypay.model.DailyPayRecord;

@Repository("dailyPayRecordMapper")
public interface DailyPayRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DailyPayRecord record);

    DailyPayRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DailyPayRecord record);
    
    /**
     * 查询收支流水列表
     * @return
     */
    List<DailyPayRecord> getList(DailyPayRecord dailyPayRecord);
    
    int selectCountByPayTypeId(Integer payTypeId);

}