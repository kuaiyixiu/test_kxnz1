package com.kyx.biz.supply.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.supply.model.Supply;

public interface SupplyService {

	GrdData getInfo(Supply supply, Pager pager);

	Supply getById(Integer id);
    @Transactional
	RetInfo saveData(Supply supply);
    @Transactional
	RetInfo delData(String ids);
    /**
     * 查询
     * @param supply
     * @return
     */
	List<Supply> getInfo(Supply supply);

}
