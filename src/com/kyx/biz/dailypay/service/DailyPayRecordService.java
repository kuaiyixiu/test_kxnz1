package com.kyx.biz.dailypay.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.dailypay.model.DailyPayRecord;
import com.kyx.biz.payRecord.vo.ProfitVo;


public interface DailyPayRecordService {
	
	/**
	 * 分页获取列表
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData getList(DailyPayRecord dailyPayRecord, Pager pager);
	
	/**
	 * 保存
	 * @param request
	 * @param orders
	 * @return
	 */
	@Transactional
	RetInfo saveRecord(DailyPayRecord dailyPayRecord);
	
	
	@Transactional
	RetInfo updateRecord(DailyPayRecord dailyPayRecord);
	
	
	@Transactional
	RetInfo deleteRecord(Integer id);
	
	/**
	 * 查询
	 * @param request
	 * @param id
	 * @return
	 */
	DailyPayRecord queryById(Integer id);
	
	/**
	 * 获取利润统计
	 * @param dateRangeStartTime
	 * @param dateRangeEndTime
	 * @return
	 */
	List<ProfitVo> getProfitList(Date dateRangeStartPeriod,Date  dateRangeEndPeriod);
	
}
