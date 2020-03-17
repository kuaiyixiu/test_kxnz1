package com.kyx.biz.product.service;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.product.model.ProductAllocation;
import com.kyx.biz.product.vo.ProductAllocationVo;

public interface ProductAllocationService {
	/**
	 * 库存调拨确定
	 * @param productAllocationVo
	 * @param session
	 * @return
	 */
    @Transactional
	RetInfo saveAllocationInfo(ProductAllocationVo productAllocationVo,HttpSession session);

	GrdData getInfo(ProductAllocation productAllocation, Pager pager, HttpSession session);

	ProductAllocation getById(Integer id);
	/**
	 * 保存付款信息
	 * @param productAllocation
	 * @return
	 */
	@Transactional
	RetInfo savePayInfo(ProductAllocation productAllocation);
	/**
	 * 调拨单作废
	 * @param productAllocation
	 * @param httpSession
	 * @return
	 */
	@Transactional
	RetInfo saveDestoryData(ProductAllocation productAllocation,HttpSession httpSession);

}
