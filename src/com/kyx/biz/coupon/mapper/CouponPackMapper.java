package com.kyx.biz.coupon.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.coupon.model.CouponPack;
import com.kyx.biz.coupon.model.CouponPackDt;

@Repository("couponPackMapper")
public interface CouponPackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponPack record);

    int insertSelective(CouponPack record);

    CouponPack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponPack record);

    int updateByPrimaryKey(CouponPack record);
    
    List<CouponPack> getList(CouponPack record);
    
}