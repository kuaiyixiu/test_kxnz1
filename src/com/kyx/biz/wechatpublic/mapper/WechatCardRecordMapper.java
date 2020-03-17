package com.kyx.biz.wechatpublic.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.wechatpublic.model.WechatCardRecord;
@Repository("wechatCardRecordMapper")
public interface WechatCardRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WechatCardRecord record);

    int insertSelective(WechatCardRecord record);

    WechatCardRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WechatCardRecord record);

    int updateByPrimaryKey(WechatCardRecord record);

	List<WechatCardRecord> getInfo(WechatCardRecord wechatCardRecord);
    
    List<WechatCardRecord> selectByCustId(Integer custId);
    
	int selectCountByCustId(Integer custId);
	
	int unlockRecord(Integer id);
}