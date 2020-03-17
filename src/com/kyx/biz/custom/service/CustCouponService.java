package com.kyx.biz.custom.service;

import java.util.List;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.custom.model.CustCoupon;

public interface CustCouponService {
    /**
     * 根据sourceId查询
     * @param sourceType 
     * @param sourceId
     * @return
     */
	List<CustCoupon> getRechargeId(Integer sourceType, Integer sourceId);

	/**
	 * 根据custid查询
	 * @param custId
	 * @return
	 */
	List<CustCoupon> selectByCustId(Integer custId);
	
	int selectCountByCustId(Integer custId);

	GrdData getList(CustCoupon custCoupon, Pager pager);
}

