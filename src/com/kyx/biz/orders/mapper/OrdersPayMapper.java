package com.kyx.biz.orders.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.orders.model.OrdersPay;

@Repository("ordersPayMapper")
public interface OrdersPayMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrdersPay record);

    OrdersPay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrdersPay record);
    
	int deleteByOrderId(Integer orderId);

//	int insertBatches(@Param("list") List<OrdersPay> ordersPays);
	
	List<OrdersPay> queryListByOrderId(Integer orderId);
	
	BigDecimal getOrderPayAmt(Integer orderId);
}