package com.kyx.biz.customprice.service;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.customprice.model.CustomPrice;

public interface CustomPriceService {

	GrdData getInfo(CustomPrice customPrice, Pager pager);
    @Transactional
	RetInfo saveData(CustomPrice customPrice);
    /**
     * 批次存档
     * @param customPrice
     * @return
     */
    @Transactional
	RetInfo saveBatchData(CustomPrice customPrice);

}
