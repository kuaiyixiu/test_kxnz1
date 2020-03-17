package com.kyx.biz.custom.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.form.CustomRequest;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.customMeal.model.CustomMeal;
import com.kyx.biz.orders.model.Orders;

public interface CustomService {
	/**
	 * 查询菜单
	 * 
	 * @param resource
	 * @return
	 */
	GrdData queryCustoms(Custom custom, Pager pager);

	/**
	 * 根据id查询一条客户信息
	 * 
	 * @param id
	 * @return
	 */
	Custom selectByPrimaryKey(Integer id);

	/**
	 * 保存客户信息
	 * 
	 * @param custom
	 * @return
	 */
	@Transactional
	RetInfo saveCustom(Custom custom);

	/**
	 * 删除会员信息和车辆信息
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	RetInfo delCustomAndCar(String ids);

	/**
	 * 查询会员信息和车辆信息
	 * 
	 * @param id
	 * @return
	 */
	Custom selectCustomAndCar(Integer id);

	/**
	 * 更新会员信息
	 * 
	 * @param custom
	 * @return
	 */
	@Transactional
	RetInfo updateCustom(Custom custom);

	/**
	 * 保存客户套餐信息
	 * 
	 * @param customRequest
	 * @return
	 */
	@Transactional
	RetInfo saveCustomMeal(CustomRequest customRequest);

	/**
	 * 查询会员套餐
	 * 
	 * @param customRequest
	 * @return
	 */
	GrdData queryCustomMeal(CustomRequest customRequest);

	/**
	 * 删除套餐
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	RetInfo delCustomMeal(Integer id);

	/**
	 * <pre>
	 * 删除套餐内容明细
	 * pid:如果不为empty，则表示该明细为最后一笔，并删除客户套餐
	 * </pre>
	 * 
	 * @param id
	 * @param pId
	 * @return
	 */
	@Transactional
	RetInfo delCustomMealDt(Integer id, String pId);

	/**
	 * 查询过期套餐详情
	 * 
	 * @param customRequest
	 * @param pager
	 * @return
	 */
	GrdData queryExpireMealDts(CustomRequest customRequest, Pager pager);

	/**
	 * 查询会员跨店
	 * 
	 * @param orders
	 * @param pager
	 * @return
	 */
	GrdData queryOtherShop(Orders orders, Pager pager);

	/**
	 * 查询会员套餐(serveMealDt,productMealDts)
	 * 
	 * @param customRequest
	 * @return
	 */
	List<CustomMeal> queryCustomMealList(CustomRequest customRequest,
			Integer shopId);

	/**
	 * 查询客户套餐
	 * 
	 * @param customRequest
	 * @return
	 */
	List<CustomMeal> queryCustomMeals(CustomRequest customRequest);

	/**
	 * 添加会员信息，提供给app端接口
	 * 
	 * @param custom
	 * @return
	 */
	@Transactional
	RetInfo addCustom(Custom custom);

	/**
	 * 根据会员号得到会员
	 * 
	 * @param cardNo
	 * @return
	 */
	Custom getByCarNo(String cardNo);

	/**
	 * 查询微信端展示套餐
	 * 
	 * @param customRequest
	 * @return
	 */
	List<CustomMeal> queryWeChartCustomMeals(CustomRequest customRequest);

	/**
	 * 修改会员信息，给app端提供的接口
	 * 
	 * @param custom
	 * @return
	 */
	@Transactional
	RetInfo editCustom(Custom custom);

	/**
	 * 该会员是否存在
	 * 
	 * @param custom
	 * @return
	 */
	boolean isHasCustom(Custom custom, HttpServletRequest request);

	/**
	 * 查询过期套餐数量
	 * 
	 * @return
	 */
	int queryExpireMealDtsCount();
	
	/**
	 * 查出会员卡号
	 * @param id
	 * @return
	 */
	String selectCardNoById(Integer id);


  /**
   * 查询客户信息和
   * 
   * @param resource
   * @return
   */
  GrdData queryCustomAndCar(Custom custom, Pager pager);
}
