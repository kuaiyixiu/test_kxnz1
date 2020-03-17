package com.kyx.basic.car.mapper;

import com.kyx.basic.car.model.CarCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("carCategoryMapper")
public interface CarCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarCategory record);

    int insertSelective(CarCategory record);

    CarCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarCategory record);

    int updateByPrimaryKey(CarCategory record);

    List<CarCategory> queryAll();
}