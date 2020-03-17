package com.kyx.basic.sms.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.RetInfo;

public interface SmsSendService {
	
	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param content 短信内容
	 * @param platType 发送平台： 1示远
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Transactional(rollbackFor=Exception.class)
	RetInfo sendMessage(Integer shopId,String mobile,String content,Integer platType) throws ClientProtocolException, IOException;
	
	/**
	 * 计算此条短信使用短信条数（按照示远的计算方式 不管汉字还是英文还是标点符号，全部算一个字符） 暂时按照65个字符算一条短信
	 * @param content
	 * @return
	 */
	int calcSmsCount(String content);
}
