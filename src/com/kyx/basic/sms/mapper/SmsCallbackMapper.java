package com.kyx.basic.sms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.basic.sms.model.SmsCallback;

@Repository("smsCallbackMapper")
public interface SmsCallbackMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SmsCallback record);

    SmsCallback selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsCallback record);
    
    int insertBatches(@Param("list") List<SmsCallback> list);

}