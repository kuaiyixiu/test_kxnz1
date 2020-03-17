package com.kyx.biz.customMealDt.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.customMealDt.model.CustomMealDt;

@Repository("customMealDtMapper")
public interface CustomMealDtMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(CustomMealDt record);

	int insertSelective(CustomMealDt record);

	CustomMealDt selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(CustomMealDt record);

	int updateByPrimaryKey(CustomMealDt record);

	List<CustomMealDt> selectCustomMealDt(CustomMealDt record);

	/**
	 * 按照客户套餐id表删除明细
	 * 
	 * @param CustomMealId
	 * @return
	 */
	int deleteByCustomMealId(Integer CustomMealId);

	/**
	 * 查询过期客户套餐
	 * 
	 * @param dateRangeStartTime
	 * @param dateRangeEndTime
	 * @return
	 */
	List<CustomMealDt> selectExpireCustomMeals(
			@Param("dateRangeStartTime") Date dateRangeStartTime,
			@Param("dateRangeEndTime") Date dateRangeEndTime);

	/**
	 * 根据id查询套餐明细（带itemId）
	 */
	CustomMealDt selectCustMealById(@Param("id") Integer id,
			@Param("custId") Integer custId);

	/**
	 * 根据客户套餐明细 ID查询 套餐主表的SHOPID
	 * 
	 * @param id
	 * @return
	 */
	int selectShopIdByCustMealDtId(Integer id);

	/**
	 * 查询过期套餐个数
	 * 
	 * @return
	 */
	int selectExpireCustomMealsCount(
			@Param("dateRangeStartTime") Date dateRangeStartTime,
			@Param("dateRangeEndTime") Date dateRangeEndTime);
}