package com.kyx.biz.repertorycheck.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.repertorycheck.model.RepertoryCheck;
@Repository("repertoryCheckMapper")
public interface RepertoryCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepertoryCheck record);

    int insertSelective(RepertoryCheck record);

    RepertoryCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RepertoryCheck record);

    int updateByPrimaryKey(RepertoryCheck record);

	List<RepertoryCheck> getInfo(RepertoryCheck repertoryCheck);

	List<RepertoryCheck> getAppInfo(RepertoryCheck repertoryCheck);
}