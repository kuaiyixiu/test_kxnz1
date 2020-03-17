package com.kyx.biz.custom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.coupon.model.Coupon;
import com.kyx.biz.custom.mapper.CustCouponMapper;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.service.CustCouponService;
@Service("custCouponService")
public class CustCouponServiceImpl implements CustCouponService {
	@Resource
	private CustCouponMapper custCouponMapper;

	@Override
	public List<CustCoupon> getRechargeId(Integer sourceType, Integer sourceId) {
		return custCouponMapper.selectByResource(sourceType,sourceId);
	}

	@Override
	public List<CustCoupon> selectByCustId(Integer custId) {
		return custCouponMapper.selectByCustId(custId);
	}

	@Override
	public int selectCountByCustId(Integer custId) {
		return custCouponMapper.selectCountByCustId(custId);
	}
	
	
	@Override
	public GrdData getList(CustCoupon custCoupon, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<CustCoupon> list=custCouponMapper.getList(custCoupon);
		return new GrdData(page.getTotal(),list);
	}


}
