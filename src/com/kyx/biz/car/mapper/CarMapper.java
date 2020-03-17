package com.kyx.biz.car.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.car.model.Car;
import com.kyx.biz.remind.model.Invitation;

@Repository("carMapper")
public interface CarMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Car record);

	int insertSelective(Car record);

	Car selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Car record);

	int updateByPrimaryKey(Car record);

	/**
	 * 车牌号查询车辆信息
	 * 
	 * @param carNumber
	 * @return
	 */
	Car selectByCarNo(@Param("carNumber") String carNumber);

	/**
	 * 查询会员车辆
	 * 
	 * @param car
	 * @return
	 */
	List<Car> selectCustomsCar(Car car);

	/**
	 * 查询所有车辆信息
	 * 
	 * @param car
	 * @return
	 */
	List<Car> selectAllCarList(Car car);

	/**
	 * 按照id查询车辆信息
	 * 
	 * @param id
	 * @return
	 */
	Car selectCarById(Integer id);

	/**
	 * 查询车辆过期
	 * 
	 * @param car
	 * @return
	 */
	List<Car> selectCarByInsure(Car car);

	/**
	 * 查询车辆车检到期
	 * 
	 * @param car
	 * @return
	 */
	List<Car> selectCarByCheck(Car car);

	/**
	 * 按照会员id解绑
	 * 
	 * @param customId
	 * @return
	 */
	int updateByCustomId(Integer customId);

	/**
	 * 重新绑定车辆会员
	 * 
	 * @param car
	 * @return
	 */
	int updateByCarNumber(Car car);

	/**
	 * 按照车牌号查询非vip用户的车
	 * 
	 * @param carNumber
	 * @return
	 */
	Car selectByCarNoVip(@Param("carNumber") String carNumber);

	/**
	 * 查询车辆邀约
	 * 
	 * @param status
	 * @param shopId
	 * @return
	 */
	List<Invitation> selectInvitationCar(@Param("status") Integer status,
			@Param("shopId") Integer shopId);

	/**
	 * 手机端快速查询车辆信息
	 * 
	 * @param keyword
	 * @param shopId
	 * @return
	 */
	List<Car> selectCarInfo(@Param("keyword") String keyword,
			@Param("shopId") Integer shopId);

	List<String> selectByCarNumber(@Param("carNumber") String carNumber);
}