package com.kyx.basic.car.mapper;

import com.kyx.basic.car.model.CarVideoClass;

import java.util.List;

public interface CarVideoClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarVideoClass record);

    int insertSelective(CarVideoClass record);

    CarVideoClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarVideoClass record);

    int updateByPrimaryKey(CarVideoClass record);

    List<CarVideoClass> queryAll();
}