package com.kyx.biz.recharge.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.recharge.model.Recharge;
@Repository("rechargeMapper")
public interface RechargeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recharge record);

    int insertSelective(Recharge record);

    Recharge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Recharge record);

    int updateByPrimaryKey(Recharge record);

	List<Recharge> getList(Recharge recharge);
}