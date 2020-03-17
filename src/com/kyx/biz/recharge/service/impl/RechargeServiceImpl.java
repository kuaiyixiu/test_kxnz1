package com.kyx.biz.recharge.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.coupon.mapper.CouponMapper;
import com.kyx.biz.coupon.mapper.CouponPackDtMapper;
import com.kyx.biz.coupon.model.Coupon;
import com.kyx.biz.coupon.model.CouponPackDt;
import com.kyx.biz.custom.mapper.CustCouponMapper;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.recharge.mapper.RechargeMapper;
import com.kyx.biz.recharge.model.Recharge;
import com.kyx.biz.recharge.service.RechargeService;
@Service("rechargeService")
public class RechargeServiceImpl implements RechargeService {
	@Resource
	private RechargeMapper rechargeMapper;
	@Resource
	private CustomMapper customMapper;
	@Resource
	private CouponMapper couponMapper;
	@Resource
	private CustCouponMapper custCouponMapper;
	@Resource
	private CouponPackDtMapper couponPackDtMapper;
	@Override
	public RetInfo saveRechargeData(Recharge recharge) {
		Custom custom=new Custom();
		RetInfo ret=RetInfo.Success("充值成功");
		if(recharge.getCouponDivDisplay()==0){//不赠送代金券
			recharge.setCouponId(null);
			recharge.setSendCouponType(0);//不赠送代金券
		}
		//保存充值记录
		recharge.setRechargeTime(new Date());
		rechargeMapper.insertSelective(recharge);
		Custom cust=customMapper.selectByCardNo(recharge.getCardNo());
		if(recharge.getCouponDivDisplay()==1){//赠送代金券
			//String remark="";
			if(recharge.getSendCouponType()==1){//代金券
				saveCustCoupon(recharge.getCouponId(), cust.getId(),recharge.getId());
				//remark="充值赠送代金券(金额:"+amout+")";
			}else if(recharge.getSendCouponType()==2){//代金券包
				List<CouponPackDt> list=couponPackDtMapper.selectByPackId(recharge.getCouponId());
				for(CouponPackDt dt:list){
					saveCustCoupon(dt.getCouponId(),cust.getId(),recharge.getId());
				}
				//remark="充值赠送代金券包(金额:"+amout+")";
			}
			//recharge.setRemark(remark);
		}
		custom.setBalance(new BigDecimal(cust.getBalance().doubleValue()+recharge.getRechargeAmount().doubleValue()));
		custom.setCardNo(recharge.getCardNo());
		//根据卡号更新客户余额信息
		customMapper.updateByCardNo(custom);

		return ret;
	}
	private void saveCustCoupon(Integer couponId, Integer custId, Integer rechargeId) {
		Coupon coupon=couponMapper.selectByPrimaryKey(couponId);
		CustCoupon custCoupon=new CustCoupon();
		custCoupon.setCustId(custId);
		custCoupon.setCreateTime(new Date());
		custCoupon.setEndTime(AppUtils.dateAdd(custCoupon.getCreateTime(), coupon.getDays()));//截止日期
		custCoupon.setAvailAmount(coupon.getValue());
		custCoupon.setTotalAmount(coupon.getValue());
		custCoupon.setCouponId(couponId);
		custCoupon.setSourceType(1);//充值赠送
		custCoupon.setState(1);//可用
		custCoupon.setSourceId(rechargeId);//充值id
		custCoupon.setCouponName(coupon.getName());
		custCouponMapper.insertSelective(custCoupon);
	}
	@Override
	public GrdData getList(Recharge recharge, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
        List<Recharge> list=rechargeMapper.getList(recharge);
		return new GrdData(page.getTotal(),list);
	}

}
