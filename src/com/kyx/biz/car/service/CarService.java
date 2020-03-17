package com.kyx.biz.car.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.model.KeyValue;
import com.kyx.biz.carMaintain.model.CarMaintain;
import com.kyx.biz.orders.model.Orders;

/**
 * 车辆信息
 * 
 * @author daibin
 * @date 2019-4-15 上午9:50:03
 * 
 */
public interface CarService {
	/**
	 * 按照customId查询
	 * 
	 * @param customId
	 * @return
	 */
	List<Car> selectByCustomId(Integer customId);

	/**
	 * 查询会员车辆信息
	 * 
	 * @param car
	 * @return
	 */
	List<Car> selectCustomsCar(Car car);

	/**
	 * 查询所有车辆信息，会员/非会员
	 * 
	 * @param car
	 * @return
	 */
	GrdData selectAllCarList(Car car, Pager pager);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	RetInfo delCar(String ids);

	/**
	 * 按照id查询车辆信息
	 * 
	 * @param id
	 * @return
	 */
	Car selectCarById(Integer id);

	/**
	 * 查询所有车辆类型
	 * 
	 * @return
	 */
	List<KeyValue> queryAllCarType();

	/**
	 * 更新车辆信息
	 * 
	 * @param car
	 * @return
	 */
	RetInfo updateCar(Car car);

	/**
	 * 查询保险到期车辆信息
	 * 
	 * @param car
	 * @return
	 */
	GrdData selectCarByInsure(Car car, Pager pager);

	/**
	 * 查询车检到期车辆信息
	 * 
	 * @param car
	 * @param pager
	 * @return
	 */
	GrdData selectCarByCheck(Car car, Pager pager);

	/**
	 * 根据车牌查车辆信息
	 * 
	 * @param carNo
	 * @return
	 */
	Car selectByCarNo(String carNo);

	/**
	 * 保存车辆信息（不关联用户）
	 * 
	 * @param car
	 * @return
	 */
	@Transactional
	RetInfo saveCar(Car car);

	/**
	 * 添加车辆保养
	 * 
	 * @param carMaintain
	 * @return
	 */
	@Transactional
	RetInfo addCarMaintain(CarMaintain carMaintain);

	/**
	 * 查询车辆保养记录
	 * 
	 * @param custId
	 * @param pager
	 * @return
	 */
	GrdData queryCarMaintain(Integer custId, Pager pager);

	/**
	 * 查询消费记录
	 * 
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData queryConsumeOrders(Orders orders, Pager pager);

	/**
	 * 查询车辆保险到期
	 * 
	 * @param car
	 * @return
	 */
	GrdData queryCarMaintain(Car car);

	/**
	 * 根据车辆id查询下次保养时间
	 * 
	 * @param id
	 * @return
	 */
	String queryNextMaintainTime(Integer id);

	/**
	 * 一个车辆仅保留最后一条保养信息
	 * 
	 * @param list
	 * @return
	 */
	List<CarMaintain> tansferCarMaintains(List<CarMaintain> list);

	/**
	 * 按照会员id查询车辆列表，时间和类型转换成string的
	 * 
	 * @param customId
	 * @return
	 */
	List<Car> queryCarListByCustomId(Integer customId);

	/**
	 * 根据车牌号模糊查询 只查询前10笔
	 * 
	 * @param carNumber
	 * @return
	 */
	List<String> selectByCarNumber(String carNumber);

	/**
	 * 提供给手机端快查
	 * 
	 * @param keyword
	 * @param shopId
	 * @param pager
	 * @return
	 */
	List<Car> selectCarInfo(String keyword, Integer shopId, Pager pager);

}
