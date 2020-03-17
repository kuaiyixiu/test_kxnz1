package com.kyx.biz.supply.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.supply.model.Supply;
@Repository("supplyMapper")
public interface SupplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Supply record);

    int insertSelective(Supply record);

    Supply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Supply record);

    int updateByPrimaryKey(Supply record);

	List<Supply> getInfo(Supply supply);
}