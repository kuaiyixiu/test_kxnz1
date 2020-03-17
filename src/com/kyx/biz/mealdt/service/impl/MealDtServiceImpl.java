package com.kyx.biz.mealdt.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.biz.meal.model.Meal;
import com.kyx.biz.mealdt.mapper.MealDtMapper;
import com.kyx.biz.mealdt.model.MealDt;
import com.kyx.biz.mealdt.service.MealDtService;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.service.ProductService;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.service.ServeService;

@Service("mealDtService")
public class MealDtServiceImpl implements MealDtService {
	@Resource
	private MealDtMapper mealDtMapper;

	@Resource
	private ProductService productService;

	@Resource
	private ServeService serveService;

	@Override
	public List<MealDt> selectByMealId(Integer mealId) {
		MealDt mealDt = new MealDt();
		mealDt.setMealId(mealId);
		List<MealDt> mealDts = mealDtMapper.selectMeals(mealDt);

		return transferMealDts(mealDts);
	}

	/**
	 * <pre>
	 * 转换订单明细
	 * type:1 产品 type:2服务
	 * </pre>
	 * 
	 * @param list
	 * @return
	 */
	@Override
	public List<MealDt> transferMealDts(List<MealDt> list) {
		for (MealDt mealDt : list) {
			String typeName = "";
			String mealName = "";
			BigDecimal price;
			int type = mealDt.getType();
			if (type == MealDt.PRODUCT) {
				Product product = productService.getById(mealDt.getItemId());
				mealName = product.getProductName();
				price = product.getPrice();
				typeName = "产品";
			} else {
				Serve serve = serveService.getById(mealDt.getItemId());
				mealName = serve.getServeName();
				price = serve.getPrice();
				typeName = "服务";
			}
			mealDt.setMealDtName(mealName);
			mealDt.setPrice(price);
			mealDt.setTypeName(typeName);
		}

		return list;
	}

	/**
	 * 设置套餐明细和总价格
	 * 
	 * @param meal
	 * @return
	 */
	@Override
	public Meal setMealDts(Meal meal) {
		int total = 0;
		for (MealDt mealDt : meal.getMealDts()) {
			String typeName = "";
			String mealName = "";
			BigDecimal price;
			int type = mealDt.getType();
			if (type == MealDt.PRODUCT) {
				Product product = productService.getById(mealDt.getItemId());
				mealName = product.getProductName();
				price = product.getPrice();
				typeName = "产品";
			} else {
				Serve serve = serveService.getById(mealDt.getItemId());
				mealName = serve.getServeName();
				price = serve.getPrice();
				typeName = "服务";
			}
			mealDt.setMealDtName(mealName);
			mealDt.setPrice(price);
			mealDt.setTypeName(typeName);

			total += price.intValue() * mealDt.getQuantity();
		}

		meal.setOriginalPrice(total);

		return meal;
	}

	@Override
	public int getCountByTypeAndId(int type, Integer itemId) {
		
		return mealDtMapper.getCountByTypeAndId(type,itemId);
	}
}
