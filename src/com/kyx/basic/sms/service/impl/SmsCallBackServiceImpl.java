package com.kyx.basic.sms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.basic.sms.mapper.SmsCallbackMapper;
import com.kyx.basic.sms.mapper.SmsSendMapper;
import com.kyx.basic.sms.model.SmsCallback;
import com.kyx.basic.sms.model.SmsSend;
import com.kyx.basic.sms.service.SmsCallBackService;

@Service("smsCallBackService")
public class SmsCallBackServiceImpl implements SmsCallBackService {
	
	@Resource
	private SmsCallbackMapper smsCallbackMapper;
	
	@Resource
	private SmsSendMapper smsSendMapper;

	@Override
	public int batchSaveSmsCallBack(List<SmsCallback> list) {
		if(smsCallbackMapper.insertBatches(list) > 0){
			for(SmsCallback sr : list){
				SmsSend ss = new SmsSend();
				if("DELIVRD".equalsIgnoreCase(sr.getStatus())){ //表示发送成功
					ss.setMsgStatus(1);
				}else{
					ss.setMsgStatus(2);
					if(sr.getStatus() == null)
						sr.setStatus("");
					if(sr.getStatus().length()>20){
						sr.setStatus(sr.getStatus().substring(0,20));
					}
					ss.setStatusCode(sr.getStatus()); //数据库就存20位
				}
				ss.setMsgId(sr.getMsgId());
				smsSendMapper.updateByMsgId(ss);
			}
			return 1;
		}
		return 0;
	}

}
