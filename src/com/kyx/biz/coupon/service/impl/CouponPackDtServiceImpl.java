package com.kyx.biz.coupon.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.coupon.mapper.CouponMapper;
import com.kyx.biz.coupon.mapper.CouponPackDtMapper;
import com.kyx.biz.coupon.mapper.CouponPackMapper;
import com.kyx.biz.coupon.model.Coupon;
import com.kyx.biz.coupon.model.CouponPack;
import com.kyx.biz.coupon.model.CouponPackDt;
import com.kyx.biz.coupon.service.CouponPackDtService;
@Service("couponPackDtService")
public class CouponPackDtServiceImpl implements CouponPackDtService {
	
	@Resource
	private CouponPackDtMapper couponPackDtMapper;
	
	@Resource
	private CouponPackMapper couponPackMapper;
	
	@Resource
	private CouponMapper couponMapper;

	@Override
	public GrdData getListByPackId(Integer packId, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<CouponPackDt> list= couponPackDtMapper.getListByPackId(packId);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveCouponPackDt(CouponPackDt couponPackDt) {
		if(couponPackDt.getCouponId() == null || couponPackDt.getPackId() == null){
			return new RetInfo("success","信息有误");
		}
		
		//异动卡包的金额
		Coupon coupon = couponMapper.selectByPrimaryKey(couponPackDt.getCouponId());
		CouponPack couponPack = new CouponPack();
		couponPack.setId( couponPackDt.getPackId());
		couponPack.setAddValue(coupon.getValue());
		couponPackMapper.updateByPrimaryKeySelective(couponPack);
		
		int count = couponPackDtMapper.insertSelective(couponPackDt);
		
		if(count>0)
			return new RetInfo("success","保存成功");
		else
			return new RetInfo("error","保存失败");
	}

	@Override
	public CouponPackDt selectByPrimaryKey(Integer id) {
		return couponPackDtMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo delCouponPackDt(Integer id) {
		//异动卡包的金额
		CouponPackDt couponPackDt = couponPackDtMapper.selectByPrimaryKey(id);
		Coupon coupon = couponMapper.selectByPrimaryKey(couponPackDt.getCouponId());
		CouponPack couponPack = new CouponPack();
		couponPack.setId( couponPackDt.getPackId());
		couponPack.setSubValue(coupon.getValue());
		couponPackMapper.updateByPrimaryKeySelective(couponPack);
		
		
		int count = couponPackDtMapper.deleteByPrimaryKey(id);
		if(count>0)
			return new RetInfo("success","删除成功");
		else
			return new RetInfo("error","删除失败");
	}

}
