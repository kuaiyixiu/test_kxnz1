package com.kyx.biz.repertory.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertory.model.RepertoryDt;

public interface RepertoryDtService {

	GrdData getInfo(RepertoryDt dt, Pager pager);

	List<RepertoryDt> getList(RepertoryDt dt);
    @Transactional
	RetInfo saveData(RepertoryDt repertoryDt);
    @Transactional
	RetInfo delData(Integer id);
    
	RepertoryDt getById(Integer id);
	
	GrdData getRepertoryInfo(RepertoryDt dt);
	@Transactional
	RetInfo saveBatch(List<RepertoryDt> dtList);

}
