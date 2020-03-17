package com.kyx.biz.repertorycheck.service;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertorycheck.model.RepertoryCheck;

public interface RepertoryCheckService {
    /**
     * 分页查询
     * @param repertoryCheck
     * @param pager
     * @return
     */
	GrdData getInfo(RepertoryCheck repertoryCheck, Pager pager);
	/**
	 * 保存
	 * @param repertoryCheck
	 * @return
	 */
    @Transactional
	RetInfo savePDData(RepertoryCheck repertoryCheck);
    
	GrdData getAppInfo(RepertoryCheck repertoryCheck, Pager pager);

}
