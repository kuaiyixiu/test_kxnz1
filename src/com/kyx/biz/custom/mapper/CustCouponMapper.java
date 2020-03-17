package com.kyx.biz.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.custom.model.CustCoupon;
@Repository("custCouponMapper")
public interface CustCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustCoupon record);

    int insertSelective(CustCoupon record);

    CustCoupon selectByPrimaryKey(Integer id);
    
    String selectCouponNameById(Integer id);

    int updateByPrimaryKeySelective(CustCoupon record);

    int updateByPrimaryKey(CustCoupon record);

	List<CustCoupon> selectByResource(@Param("sourceType") Integer sourceType, @Param("sourceId") Integer sourceId);
	
	List<CustCoupon> selectByCustId(Integer custId);
	
	int selectCountByCustId(Integer custId);
	
	int selectCountByCouponId(Integer couponId);
	
	List<CustCoupon> getList(CustCoupon custCoupon);
    /**
     * 赠送代金券报表
     * @param custCoupon
     * @return
     */
	List<CustCoupon> getRepList(CustCoupon custCoupon);
}