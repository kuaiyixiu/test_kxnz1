package com.kyx.basic.sms.service.impl;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.mapper.ShopsMapper;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.sms.mapper.SmsCallbackMapper;
import com.kyx.basic.sms.mapper.SmsSendMapper;
import com.kyx.basic.sms.model.SmsSend;
import com.kyx.basic.sms.service.SmsSendService;
import com.kyx.basic.sms.util.SyMsgUtil;
import com.kyx.basic.util.RetInfo;

@Service("smsSendService")
public class SmsSendServiceImpl implements SmsSendService{
	
	@Resource
	private SmsSendMapper smsSendMapper;
	@Resource
	private ShopsMapper shopsMapper;
	
	
	@Override
	public RetInfo sendMessage(Integer shopId,String mobile, String content, Integer platType) throws ClientProtocolException, IOException {
		Dbs.setDbName(Dbs.getMainDbName()); //切换主库
		Integer smsAmount =  shopsMapper.selectByPrimaryKey(shopId).getSmsAmount();
		Integer useCount = calcSmsCount(content);
		if(smsAmount == null || smsAmount < useCount){ //校验用户短信是否有余量
			return RetInfo.Error("用户短信余量不足，请联系平台充值");
		}
		
		if(platType == 1){
			SmsSend smsSend = SyMsgUtil.sendMsg(mobile, content); //发送短信
			smsSend.setUseCount(useCount);//记录本次短信使用的条数
			smsSendMapper.insertSelective(smsSend);//插入记录
			Shops shops = new Shops();
			shops.setId(shopId);//更新用户短信余量
			shops.setAddQuantity(-useCount);
   			shopsMapper.updateShopSmsCount(shops); 
		}
		return RetInfo.Success("发送成功");
	}


	@Override
	public int calcSmsCount(String content) {
		int divisor = 65;
		if(StringUtils.isBlank(content)){
			return 0;
		}
		int mod = 0;
		if(content.length()%divisor > 0){
			mod = 1;
		}
		return content.length()/divisor + mod;
	}

	

}
