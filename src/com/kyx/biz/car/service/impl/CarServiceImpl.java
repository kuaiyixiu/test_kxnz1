package com.kyx.biz.car.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.car.mapper.CarMapper;
import com.kyx.biz.car.model.Car;
import com.kyx.biz.car.model.CarEnum;
import com.kyx.biz.car.model.KeyValue;
import com.kyx.biz.car.service.CarService;
import com.kyx.biz.carMaintain.mapper.CarMaintainMapper;
import com.kyx.biz.carMaintain.model.CarMaintain;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEnum;
import com.kyx.biz.orders.service.OrdersProductService;
import com.kyx.biz.orders.service.OrdersServeService;

@Service("carService")
public class CarServiceImpl implements CarService {
	@Resource
	private CarMapper carMapper;

	@Resource
	private CustomMapper customMapper;

	@Resource
	private CarMaintainMapper carMaintainMapper;

	@Resource
	private OrdersMapper ordersMapper;

	@Resource
	private OrdersProductService ordersProductService;

	@Resource
	private OrdersServeService ordersServeService;

	@Override
	public List<Car> selectCustomsCar(Car car) {

		return carMapper.selectCustomsCar(car);
	}

	@Override
	public List<Car> selectByCustomId(Integer customId) {
		Car car = new Car();
		car.setCustomId(customId);

		return this.selectCustomsCar(car);
	}

	@Override
	public GrdData selectAllCarList(Car car, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Car> list = carMapper.selectAllCarList(car);

		return new GrdData(page.getTotal(), list);
	}

	@Override
	public RetInfo delCar(String ids) {
		String[] idArr = ids.split(";");
		int count = 0;
		for (String id : idArr) {
			int customId = Integer.valueOf(id);
			count += carMapper.deleteByPrimaryKey(customId);
		}

		if (count == idArr.length) {
			return RetInfo.Success("删除成功");
		}

		return RetInfo.Error("删除失败");
	}

	@Override
	public Car selectCarById(Integer id) {
		Car car = new Car();
		car.setId(id);
		List<Car> list = carMapper.selectAllCarList(car);
		if (list.size() > 0) {
			return transferCarDetail(list.get(0));
		}

		return new Car();
	}

	@Override
	public List<KeyValue> queryAllCarType() {
		List<KeyValue> list = new ArrayList<>();
		for (CarEnum car : CarEnum.values()) {
			list.add(new KeyValue(String.valueOf(car.getType()), car.getName()));
		}

		return list;
	}

	/**
	 * 转换车辆详情
	 * 
	 * @param car
	 * @return
	 */
	private Car transferCarDetail(Car car) {
		car.setCommercialDateStr(AppUtils.date2String1(car.getCommercialDate()));
		car.setCheckDateStr(AppUtils.date2String1(car.getCheckDate()));
		car.setCompulsoryDateStr(AppUtils.date2String1(car.getCompulsoryDate()));

		return car;
	}

	@Override
	public RetInfo updateCar(Car car) {
		boolean bool = true;
		// 如果是会员类型，更新会员表
		if (car.getCarType() == Car.VIP) {
			bool = updateCarType(car);
		}

		boolean bool1 = carMapper.updateByPrimaryKeySelective(car) > 0;
		if (bool && bool1) {
			return RetInfo.Success("更新成功");
		}

		return RetInfo.Error("更新失败");
	}

	private boolean updateCarType(Car car) {
		Custom custom = new Custom();

		Integer id = car.getCustomId();
		custom.setId(id);

		String name = car.getOwnerName();
		custom.setCustName(name);

		String phone = car.getOwnerCellphone();
		custom.setCellphone(phone);

		// 不更改car表中的车主姓名和电话
		car.setOwnerName(null);
		car.setOwnerCellphone(null);

		return customMapper.updateByPrimaryKeySelective(custom) > 0;
	}

	@Override
	public GrdData selectCarByInsure(Car car, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Car> list = carMapper.selectCarByInsure(car);

		return new GrdData(page.getTotal(), list);
	}

	@Override
	public GrdData selectCarByCheck(Car car, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Car> list = carMapper.selectCarByCheck(car);

		return new GrdData(page.getTotal(), list);
	}

	@Override
	public Car selectByCarNo(String carNo) {
		return carMapper.selectByCarNo(carNo);
	}

	@Override
	public RetInfo saveCar(Car car) {
		if (car == null) {
			return RetInfo.Error("车辆信息不能为空");
		}

		if (car.getCarType() == null) {
			return RetInfo.Error("车辆类型不能为空");
		}
		if (StringUtils.isBlank(car.getCarNumber())) {
			return RetInfo.Error("车牌号不能为空");
		}

		if (carMapper.insertSelective(car) <= 0) {
			return RetInfo.Error("保存车辆失败");
		}

		return RetInfo.Success("保存车辆成功");
	}

	@Override
	public RetInfo addCarMaintain(CarMaintain carMaintain) {
		boolean bool = carMaintainMapper.insertSelective(carMaintain) > 0;
		if (bool) {
			return RetInfo.Success("添加成功");
		}

		return RetInfo.Error("添加失败");
	}

	@Override
	public GrdData queryCarMaintain(Integer carId, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<CarMaintain> list = carMaintainMapper
				.selectCarMaintainByCarId(carId);

		return new GrdData(page.getTotal(), list);
	}

	@Override
	public GrdData queryConsumeOrders(Orders orders, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Orders> list = ordersMapper.selectConsumeOrders(orders);
		transferOrdersList(list);

		return new GrdData(page.getTotal(), list);
	}

	/**
	 * 订单列表转换
	 * 
	 * @param list
	 */
	private void transferOrdersList(List<Orders> list) {
		for (Orders orders : list) {
			Integer orderId = orders.getId();
			orders.setOrdersProductList(ordersProductService
					.queryListByOrderId(orderId));
			orders.setOrdersServeList(ordersServeService
					.queryListByOrderId(orderId));
			orders.setOrderStatusName(OrdersEnum.getOrdersName(orders
					.getOrderStatus()));
		}
	}

	@Override
	public GrdData queryCarMaintain(Car car) {
		List<CarMaintain> list = carMaintainMapper.selectCarMaintain(car);
		list = tansferCarMaintains(list);

		return new GrdData(Long.valueOf(list.size()), list);
	}

	/**
	 * 一个车辆保留最后一次保养信息
	 * 
	 * @param list
	 * @return
	 */
	@Override
	public List<CarMaintain> tansferCarMaintains(List<CarMaintain> list) {
		List<CarMaintain> resultList = new ArrayList<>();
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (CarMaintain carMaintain : list) {
			Integer id = carMaintain.getCarId();
			if (StringUtils.isEmpty(map.get(id))) {
				resultList.add(carMaintain);
			}
			map.put(id, "hasValue");
		}
		return resultList;
	}

	@Override
	public String queryNextMaintainTime(Integer id) {
		List<CarMaintain> list = carMaintainMapper.selectCarMaintainDesc(id);
		if (list.size() < 2) {
			return "";
		}
		CarMaintain lastManintain = list.get(0);
		CarMaintain secondToLastManintain = list.get(1);
		int day = AppUtils.differentDaysByMillisecond(
				lastManintain.getCreateTime(),
				secondToLastManintain.getCreateTime());

		Date nowDate = new Date();
		Date date = AppUtils.getDayByCycle(nowDate, day);

		return AppUtils.date2String1(date);
	}

	@Override
	public List<Car> queryCarListByCustomId(Integer customId) {
		List<Car> list = selectByCustomId(customId);

		return transferCarList(list);
	}

	private List<Car> transferCarList(List<Car> list) {
		for (Car car : list) {
			car.setCommercialDateStr(AppUtils.date2String1(car
					.getCommercialDate()));
			car.setCompulsoryDateStr(AppUtils.date2String1(car
					.getCompulsoryDate()));
			car.setCheckDateStr(AppUtils.date2String1(car.getCheckDate()));
			car.setCarTypeName(CarEnum.getCarTypeName(car.getCarType()));
		}

		return list;
	}

	@Override
	public List<String> selectByCarNumber(String carNumber) {

		return carMapper.selectByCarNumber(carNumber);
	}
	
	/*
	@Override
	public List<Car> selectCarInfo(String keyword, Integer shopId, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		
		return carMapper.selectCarInfo(keyword, shopId);
	}*/

	@Override
	public List<Car> selectCarInfo(String keyword, Integer shopId, Pager pager) {
		// 超出查询最大数量返回空
		PageHelper.startPage(pager.getPage(), pager.getLimit(), true, false);
		List<Car> list = carMapper.selectCarInfo(keyword, shopId);

		return list;
	}

}
