package com.kyx.biz.carMaintain.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.car.model.Car;
import com.kyx.biz.carMaintain.model.CarMaintain;
import com.kyx.biz.remind.model.Invitation;

@Repository("carMaintainMapper")
public interface CarMaintainMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CarMaintain record);

	int insertSelective(CarMaintain record);

	CarMaintain selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CarMaintain record);

	int updateByPrimaryKey(CarMaintain record);

	List<CarMaintain> selectCarMaintainByCarId(Integer carId);

	/**
	 * 查询车辆保养到期
	 * 
	 * @param car
	 * @return
	 */
	List<CarMaintain> selectCarMaintain(Car car);

	List<CarMaintain> selectCarMaintainDesc(Integer carId);

	/**
	 * 按照车辆id查询车辆保养信息
	 * 
	 * @param list
	 * @return
	 */
	List<CarMaintain> selectCarMaintainByCarIds(
			@Param("list") List<Invitation> list,
			@Param("status") Integer status);
}