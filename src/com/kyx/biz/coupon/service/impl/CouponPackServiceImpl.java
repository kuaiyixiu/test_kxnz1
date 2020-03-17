package com.kyx.biz.coupon.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.coupon.mapper.CouponPackDtMapper;
import com.kyx.biz.coupon.mapper.CouponPackMapper;
import com.kyx.biz.coupon.model.CouponPack;
import com.kyx.biz.coupon.model.CouponPackDt;
import com.kyx.biz.coupon.service.CouponPackService;
@Service("couponPackService")
public class CouponPackServiceImpl implements CouponPackService {
	
	@Resource
	private CouponPackMapper couponPackMapper;
	
	@Resource
	private CouponPackDtMapper couponPackDtMapper;
	
	@Override
	public GrdData getList(CouponPack couponPack, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<CouponPack> list=couponPackMapper.getList(couponPack);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveCouponPack(CouponPack couponPack) {
		couponPack.setValue(BigDecimal.ZERO);
		int count = couponPackMapper.insertSelective(couponPack);
		if(count>0)
			return new RetInfo("success","保存成功");
		else
			return new RetInfo("error","保存失败");
	}

	@Override
	public CouponPack selectByPrimaryKey(Integer id) {
		return couponPackMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo updateCouponPack(CouponPack couponPack) {
		int count = couponPackMapper.updateByPrimaryKey(couponPack);
		if(count>0)
			return new RetInfo("success","修改成功");
		else
			return new RetInfo("error","修改失败");
	}

	@Override
	public RetInfo delCouponPack(Integer id) {
		 List<CouponPackDt> dts = couponPackDtMapper.selectByPackId(id);
		 if(dts.size() > 0 ){
			 return new RetInfo("error","请先删除卡包里代金券");
		 }
		
		int count = couponPackMapper.deleteByPrimaryKey(id);
		if(count>0)
			return new RetInfo("success","删除成功");
		else
			return new RetInfo("error","删除失败");
	}
	
	@Override
	public RetInfo getAll() {
		CouponPack couponPack = new CouponPack();
		List<CouponPack> list=couponPackMapper.getList(couponPack);
		RetInfo ret=RetInfo.Success("");
		ret.setRetData(list);
		return ret;
	}

}
