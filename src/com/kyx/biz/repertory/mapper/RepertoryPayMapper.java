package com.kyx.biz.repertory.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.repertory.model.RepertoryPay;
@Repository("repertoryPayMapper")
public interface RepertoryPayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepertoryPay record);

    int insertSelective(RepertoryPay record);

    RepertoryPay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RepertoryPay record);

    int updateByPrimaryKey(RepertoryPay record);

	List<RepertoryPay> getInfo(RepertoryPay repertoryPay);

	int deleteByRepertoryId(Integer repertoryId);
}