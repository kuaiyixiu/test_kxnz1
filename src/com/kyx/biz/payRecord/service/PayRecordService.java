package com.kyx.biz.payRecord.service;

import java.util.Map;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.payRecord.model.PayRecord;


public interface PayRecordService {
	/**
	 * 分页获取列表
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData getList(PayRecord payRecord, Pager pager);
    /**
     * 根据门店获取主页面图标数据
     * @param shopId
     * @return
     */
	Map getEchatInfo(Integer shopId);
	
}
