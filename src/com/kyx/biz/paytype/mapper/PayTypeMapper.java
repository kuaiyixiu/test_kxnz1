package com.kyx.biz.paytype.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.dailypay.model.DailyPayType;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.serve.model.Serve;

@Repository("payTypeMapper")
public interface PayTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(PayType record);

    PayType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayType record);

    List<PayType> getPayTypeList(PayType record);

	int getCountByName(@Param("payName") String payName);

	List<PayType> getPayType(@Param("type") Integer type);
	
	List<PayType> getPayTypes(@Param("type") Integer type);

}