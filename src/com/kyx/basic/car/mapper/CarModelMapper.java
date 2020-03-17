package com.kyx.basic.car.mapper;


import com.kyx.basic.car.model.CarBrand;
import com.kyx.basic.car.model.CarModel;
import com.kyx.basic.util.Pager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("carModelMapper")
public interface CarModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarModel record);

    int insertSelective(CarModel record);

    CarModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarModel record);

    int updateByPrimaryKey(CarModel record);

    List<CarModel> queryByBrandSeries(@Param("seriesId") int seriesId, @Param("year") String year);

    List<String> queryCarModelYears(int seriesId);

    List<CarModel> searchCarModelList(@Param("search") String search);
}