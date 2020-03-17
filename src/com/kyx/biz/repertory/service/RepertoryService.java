package com.kyx.biz.repertory.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.payRecord.vo.ProfitVo;
import com.kyx.biz.repertory.model.Repertory;

public interface RepertoryService {

	GrdData getInfo(Repertory repertory, Pager pager);
    @Transactional
	RetInfo saveData(Repertory repertory, HttpSession httpSession);

	Repertory getById(Integer id);
	/**
	 * kind=‘1’产品入库 分为挂账入库和直接入库 根据付款金额自动生成挂账记录
	 * kind=‘2’产品退货 分为挂账退货和直接退货 根据付款金额自动生成挂账记录
	 * @param repertory
	 * @param httpSession 
	 * @return
	 */
	@Transactional
	public RetInfo saveRepertoryin(Repertory repertory);

	/**
	 * 提交入库/退货申请
	 * @param repertory
	 * @return
	 */
	@Transactional
	public RetInfo applyRepertoryin(HttpServletRequest request,Repertory repertory);
//	
//	/**
//	 * 审核通过入库/退货
//	 * @param request
//	 * @param repertory
//	 * @return
//	 */
//	@Transactional
//	public RetInfo submitRepertoryin(HttpServletRequest request,Integer id);
	
	@Transactional
	RetInfo delData(String ids);
	/**
	 * 库存单据作废
	 * @param repertory
	 * @param httpSession 
	 * @return
	 */
	@Transactional
	RetInfo saveDestoryData(Repertory repertory, HttpSession httpSession);
	
	
	/**
	 * 获取订单退货金额 
	 * @return
	 */
	ProfitVo getOrderReturn(Date dateRangeStartTime,Date  dateRangeEndTime);
	/**
	 * 存档
	 * @param repertory
	 * @return
	 */
	@Transactional
	RetInfo save(Repertory repertory);

}
