package com.kyx.basic.sms.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.kyx.basic.sms.model.SmsRecharge;
import com.kyx.basic.sms.model.SmsSend;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechat.vo.TemplateDataVo;

public interface SmsService {
    /**
     * 获取充值记录列表
     * @param smsRecharge
     * @param pager
     * @return
     */
	GrdData getRechargePage(SmsRecharge smsRecharge, Pager pager);
    /**
     * 获取短信消费记录
     * @param smsSend
     * @param pager
     * @return
     */
	GrdData getSendPage(SmsSend smsSend, Pager pager);
	
	/**
	 * 发送短信
	 * @param tplVo
	 * @param shopId
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	RetInfo sendSms(TemplateDataVo tplVo,String mobile, Integer msgType,Integer shopId) throws ClientProtocolException, IOException;

}
