package com.kyx.biz.debt.service;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.debt.model.DebtRecord;

public interface DebtRecordService {
    @Transactional
	int save(DebtRecord debtRecord);

	GrdData getInfo(DebtRecord debtRecord, Pager pager);

	DebtRecord getById(Integer id);
	/**
	 * 入库挂账还款
	 * @param debtRecord
	 * @return
	 */
	@Transactional
	RetInfo saveReturnDebt(DebtRecord debtRecord);
	
    /**
     * 批量还款
     * @param payId 付款类型
     * @param ids 挂账id
     * @param shopId 
     * @return
     */
	@Transactional
	RetInfo saveReturnDebt(Integer payId, String ids, Integer shopId);
    
	BigDecimal getSumDebtAmount(DebtRecord debtRecord);

}
