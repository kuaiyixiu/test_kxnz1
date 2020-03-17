package com.kyx.biz.coupon.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.coupon.model.CouponPack;
import com.kyx.biz.coupon.model.CouponPackDt;


public interface CouponPackService {
	/**
	 * 分页获取列表
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData getList(CouponPack couponPack, Pager pager);
	
	
	@Transactional
	RetInfo saveCouponPack(CouponPack couponPack);
	
	
	CouponPack selectByPrimaryKey(Integer id);
	
	@Transactional
	RetInfo updateCouponPack(CouponPack couponPack);
	
	@Transactional
	RetInfo delCouponPack(Integer id);
	  /**
     * 获取所有代金券包信息
     * @return
     */
	RetInfo getAll();

}
