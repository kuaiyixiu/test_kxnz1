package com.kyx.biz.repertory.service;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertory.model.RepertoryPay;

public interface RepertoryPayService {

	GrdData getInfo(RepertoryPay repertoryPay, Pager pager);
    @Transactional
	RetInfo saveData(RepertoryPay repertoryPay);
    @Transactional
	RetInfo delData(Integer ids);

}
