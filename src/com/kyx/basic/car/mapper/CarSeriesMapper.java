package com.kyx.basic.car.mapper;

import com.kyx.basic.car.model.CarBrand;
import com.kyx.basic.car.model.CarSeries;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("carSeriesMapper")
public interface CarSeriesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarSeries record);

    int insertSelective(CarSeries record);

    CarSeries selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarSeries record);

    int updateByPrimaryKey(CarSeries record);

    List<CarSeries> queryByBrand(int brandId);
}