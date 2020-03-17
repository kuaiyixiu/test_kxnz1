package com.kyx.basic.sms.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.basic.sms.model.SmsSend;

@Repository("smsSendMapper")
public interface SmsSendMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SmsSend record);

    SmsSend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsSend record);
    
	int updateByMsgId(SmsSend smsSend);

	List<SmsSend> getList(SmsSend smsSend);

}