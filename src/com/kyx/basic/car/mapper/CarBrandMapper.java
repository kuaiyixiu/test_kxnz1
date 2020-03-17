package com.kyx.basic.car.mapper;


import com.kyx.basic.car.model.CarBrand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("carBrandMapper")
public interface CarBrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarBrand record);

    int insertSelective(CarBrand record);

    CarBrand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarBrand record);

    int updateByPrimaryKey(CarBrand record);

    List<CarBrand> queryByCategory(int category);
}