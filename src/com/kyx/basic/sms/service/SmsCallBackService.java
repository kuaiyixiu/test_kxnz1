package com.kyx.basic.sms.service;

import java.util.List;

import com.kyx.basic.sms.model.SmsCallback;
import com.kyx.basic.util.RetInfo;

public interface SmsCallBackService {
	
	/**
	 * 批量插入回调记录
	 * @param list
	 * @return
	 */
	int batchSaveSmsCallBack(List<SmsCallback> list);
}
