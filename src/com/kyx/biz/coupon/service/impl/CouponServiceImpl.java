package com.kyx.biz.coupon.service.impl;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.kyx.biz.coupon.service.CouponService;
import com.kyx.biz.custom.mapper.CustCouponMapper;
import com.kyx.biz.custom.model.CustCoupon;
@Service("couponService")
public class CouponServiceImpl implements CouponService {
	
	@Resource
	private CouponMapper couponMapper;
	
	
	@Resource
	private CustCouponMapper custCouponMapper;
	
	@Resource
	private CouponPackDtMapper couponPackDtMapper;
	
	
	@Override
	public GrdData getList(Coupon coupon, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Coupon> list=couponMapper.getList(coupon);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveCoupon(Coupon coupon) {
		coupon.setStatue(1);
		int count = couponMapper.insertSelective(coupon);
		if(count>0)
			return new RetInfo("success","保存成功");
		else
			return new RetInfo("error","保存失败");
	}

	@Override
	public Coupon selectByPrimaryKey(Integer id) {
		return couponMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo updateCoupon(Coupon coupon) {
		int count = couponMapper.updateByPrimaryKey(coupon);
		if(count>0)
			return new RetInfo("success","修改成功");
		else
			return new RetInfo("error","修改失败");
	}

	@Override
	public RetInfo delCoupon(Integer id) {
		if(custCouponMapper.selectCountByCouponId(id) > 0){
			return new RetInfo("error","已有用户使用该代金券");
		}
		
		if(couponPackDtMapper.selectCountByCouponId(id) > 0 ){
			return new RetInfo("error","已有代金券包使用该代金券");
		}
		
		
		int count = couponMapper.deleteByPrimaryKey(id);
		if(count>0)
			return new RetInfo("success","删除成功");
		else
			return new RetInfo("error","删除失败");
	}

	@Override
	public List<Coupon> getListNoLimit(Coupon coupon) {
		return couponMapper.getList(coupon);
	}

	@Override
	public RetInfo getAll() {
		Coupon coupon=new Coupon();
		coupon.setStatue(1);
		List<Coupon> list=couponMapper.getList(coupon);
		RetInfo ret=RetInfo.Success("");
		ret.setRetData(list);
		return ret;
	}

	@Override
	public RetInfo submitCoupon(String couponIds, String amounts, Integer customId, Integer orderId) {
		if(couponIds == null || amounts == null || customId == null || orderId == null){
			return RetInfo.Error("选择代金券有误");
		}
		String [] couponIdArr = couponIds.split(",");
		String [] amountArr = amounts.split(",");
		for(int i = 0;i < couponIdArr.length; i++){
			if(StringUtils.isNotBlank(couponIdArr[i])){
				Integer couponId = Integer.valueOf(couponIdArr[i]);
				BigDecimal amount = new BigDecimal(amountArr[i]);
				CustCoupon custCoupon =   custCouponMapper.selectByPrimaryKey(couponId);
				if (custCoupon != null){
					if(custCoupon.getState() == 0 || custCoupon.getAvailAmount().compareTo(BigDecimal.ZERO) == 0){
						return RetInfo.Error("代金券余额不足");
					}
					if (custCoupon.getCustId().equals(customId)){
						if (custCoupon.getAvailAmount().compareTo(amount) > -1){
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(new Date());
							calendar.set(Calendar.HOUR_OF_DAY, 0);
				            calendar.set(Calendar.MINUTE, 0);
				            calendar.set(Calendar.SECOND, 0);
							if (custCoupon.getEndTime().after(calendar.getTime())){
								//开始计算
							}else{
								return RetInfo.Error("代金券已过期");
							}
							
						}else{
							return RetInfo.Error("代金券余额不足");
						}
					}else{
						return RetInfo.Error("代金券客户信息有误");
					}
					
				}else{
					return RetInfo.Error("代金券信息有误");
				}
			}
		}
		
		for(int i = 0;i < couponIdArr.length; i++){
			if(StringUtils.isNotBlank(couponIdArr[i])){
				Integer couponId = Integer.valueOf(couponIdArr[i]);
				BigDecimal amount = new BigDecimal(amountArr[i]);
				CustCoupon custCoupon =   custCouponMapper.selectByPrimaryKey(couponId);
				
				CustCoupon updateCoupon = new CustCoupon();
				updateCoupon.setId(custCoupon.getId());
				updateCoupon.setSubAvailAmount(amount);
				if(custCoupon.getAvailAmount().compareTo(amount) == 0){
					updateCoupon.setState(0);
				}
				custCouponMapper.updateByPrimaryKeySelective(updateCoupon);
			}
		}
		
		 int couponCount = custCouponMapper.selectCountByCustId(customId);
		 return RetInfo.Success(couponCount+"");
	}
	
	
}
