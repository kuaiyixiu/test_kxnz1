package com.kyx.biz.dailypay.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.dailypay.model.DailyPayType;


public interface DailyPayTypeService {
	/**
	 * 分页获取列表
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData getList(DailyPayType dailyPayType, Pager pager);
	
	List<DailyPayType> getTypeList(DailyPayType dailyPayType);
	
	
	/**
	 * 保存
	 * @param request
	 * @param orders
	 * @return
	 */
	@Transactional
	RetInfo saveType(DailyPayType dailyPayType);
	
	
	/**
	 * 更新
	 * @param request
	 * @param orders
	 * @return
	 */
	@Transactional
	RetInfo updateType(DailyPayType dailyPayType);
	
	/**
	 * 删除
	 * @param request
	 * @param orders
	 * @return
	 */
	@Transactional
	RetInfo deleteType(Integer id);
	
	/**
	 * 查询
	 * @param request
	 * @param id
	 * @return
	 */
	DailyPayType queryById(Integer id);
	
	
	Map<Integer, String> getPayTypeMap(Integer shopId);
	
	List<DailyPayType> getByShopId(Integer shopId);
	
}
