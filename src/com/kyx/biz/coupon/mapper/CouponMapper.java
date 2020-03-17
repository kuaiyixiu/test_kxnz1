package com.kyx.biz.coupon.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.coupon.model.Coupon;

@Repository("couponMapper")
public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);
    
    /**
     * 查询代金卷模版列表
     * @return
     */
    List<Coupon> getList(Coupon coupon);
}