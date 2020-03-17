package com.kyx.biz.coupon.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.coupon.model.Coupon;


public interface CouponService {
	/**
	 * 分页获取列表
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData getList(Coupon coupon, Pager pager);
	
	List<Coupon> getListNoLimit(Coupon coupon);
	
	
	@Transactional
	RetInfo saveCoupon(Coupon coupon);
	
	
	Coupon selectByPrimaryKey(Integer id);
	
	@Transactional
	RetInfo updateCoupon(Coupon coupon);
	
	@Transactional
	RetInfo delCoupon(Integer id);
    /**
     * 获取优惠券列表
     * @return
     */
	RetInfo getAll();
	
	
	/**
	 * 订单里提交选择的代金卷
	 * @param couponId
	 * @param amount
	 * @param customId
	 * @param orderId
	 * @return
	 */
	RetInfo submitCoupon(String couponIds, String amounts, Integer customId,Integer orderId); 
   
}
