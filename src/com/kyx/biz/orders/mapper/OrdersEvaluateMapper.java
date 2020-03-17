package com.kyx.biz.orders.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.orders.model.OrdersEvaluate;

@Repository("ordersEvaluateMapper")
public interface OrdersEvaluateMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(OrdersEvaluate record);

    OrdersEvaluate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrdersEvaluate record);
    
    List<OrdersEvaluate> getList(OrdersEvaluate ordersEvaluate);
    
    List<OrdersEvaluate> getCustList(OrdersEvaluate ordersEvaluate);

}