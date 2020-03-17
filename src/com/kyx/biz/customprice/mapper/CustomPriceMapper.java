package com.kyx.biz.customprice.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.customprice.model.CustomPrice;
@Repository("customPriceMapper")
public interface CustomPriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomPrice record);

    int insertSelective(CustomPrice record);

    CustomPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomPrice record);

    int updateByPrimaryKey(CustomPrice record);

	List<CustomPrice> getInfo(CustomPrice customPrice);
}