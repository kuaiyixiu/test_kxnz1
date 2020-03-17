package com.kyx.biz.coupon.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.coupon.model.CouponPackDt;

@Repository("couponPackDtMapper")
public interface CouponPackDtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CouponPackDt record);

    int insertSelective(CouponPackDt record);

    CouponPackDt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CouponPackDt record);

    int updateByPrimaryKey(CouponPackDt record);
    
    List<CouponPackDt> getListByPackId(Integer packId);
    
    List<CouponPackDt> selectByPackId(Integer packId);
    
    int selectCountByCouponId(Integer couponId);
}