package com.kyx.basic.sms.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.basic.sms.model.SmsRecharge;

@Repository("smsRechargeMapper")
public interface SmsRechargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SmsRecharge record);

    SmsRecharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsRecharge record);

	List<SmsRecharge> getList(SmsRecharge smsRecharge);

}