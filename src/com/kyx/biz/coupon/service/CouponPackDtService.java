package com.kyx.biz.coupon.service;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.coupon.model.CouponPackDt;


public interface CouponPackDtService {
	
	@Transactional
	RetInfo saveCouponPackDt(CouponPackDt couponPackDt);
	
	
	CouponPackDt selectByPrimaryKey(Integer id);
	
	
	@Transactional
	RetInfo delCouponPackDt(Integer id);

	GrdData getListByPackId(Integer packId, Pager pager);
}
