package com.kyx.biz.payRecord.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.payRecord.model.PayRecord;

@Repository("payRecordMapper")
public interface PayRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PayRecord record);

    PayRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayRecord record);

    /**
     * 查询订单列表
     * @param orders
     * @return
     */
    List<PayRecord> getList(PayRecord payRecord);
    /**
     * 根据门店和时间查询收入
     * @param time
     * @param shopId
     * @return
     */
	BigDecimal getInAmtByAddTime(@Param(value = "time") Date time, @Param(value = "shopId") Integer shopId);
    /**
     * 根据门店和时间查询支出
     * @param time
     * @param shopId
     * @return
     */
	BigDecimal getOutAmtByAddTime(@Param(value = "time") Date time, @Param(value = "shopId") Integer shopId);
	
	/**
	 * 订单统计：支付项统计
	 * @param shopId
	 * @param dateRangeStartTime
	 * @param dateRangeEndTime
	 * @return
	 */
	List<PayRecord> getPayRecordReport(@Param("shopId")Integer shopId,@Param("dateRangeStartTime")Date dateRangeStartTime,@Param("dateRangeEndTime")Date dateRangeEndTime);
}