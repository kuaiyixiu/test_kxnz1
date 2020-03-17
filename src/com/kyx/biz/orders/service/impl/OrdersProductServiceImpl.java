package com.kyx.biz.orders.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.customMealDt.mapper.CustomMealDtMapper;
import com.kyx.biz.customMealDt.model.CustomMealDt;
import com.kyx.biz.orders.mapper.OrdersProductMapper;
import com.kyx.biz.orders.mapper.OrdersRoyaltyMapper;
import com.kyx.biz.orders.model.OrdersProduct;
import com.kyx.biz.orders.model.OrdersRoyalty;
import com.kyx.biz.orders.service.OrdersProductService;
import com.kyx.biz.payRecord.mapper.MealPayRecordMapper;
import com.kyx.biz.payRecord.model.MealPayRecord;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.service.ProductRepertoryService;

@Service("ordersProductService")
public class OrdersProductServiceImpl implements OrdersProductService {

	@Resource
	private OrdersProductMapper ordersProductMapper;

	@Resource
	private ProductMapper productMapper;

	@Resource
	private OrdersRoyaltyMapper ordersRoyaltyMapper;
	
	@Resource
	private MealPayRecordMapper mealPayRecordMapper;
	
	@Resource
	private CustomMealDtMapper customMealDtMapper;

	@Resource
	private ProductRepertoryService productRepertoryService;

	
	@Override
	public RetInfo saveOrdersProductList(List<OrdersProduct> ordersProducts, Integer orderId) throws Exception {
		String msg = "";
		 if(!ObjectUtils.isEmpty(ordersProducts)){
			 for (int i=0;i<ordersProducts.size();i++){
				 OrdersProduct op = ordersProducts.get(i);
					Product pro = productMapper.selectByPrimaryKey(op.getProductId());
					if (pro.getQuantity() < op.getQuantity()){
						msg =  pro.getProductName()+"库存不足";
						break;
					}
			 }
		 }
		 
		 if (StringUtils.isNotBlank(msg)){
			return RetInfo.Error(msg); 
		 }
		 
		 if(!ObjectUtils.isEmpty(ordersProducts)){
			 for (int i=0;i<ordersProducts.size();i++){
				 OrdersProduct op = ordersProducts.get(i);
				 op.setOrderId(orderId);
				 productRepertoryService.useProduct(op.getProductId(), op.getQuantity(), orderId);
			 }
		 }
		 
		
		StringBuffer sb = new StringBuffer("");
		if (!ObjectUtils.isEmpty(ordersProducts)) {
			ordersProductMapper.insertBatches(ordersProducts);
			List<OrdersRoyalty> ordersRoyalties = new ArrayList<OrdersRoyalty>();
			for (OrdersProduct ordersProduct : ordersProducts) {
				if (ordersProduct.getProductUser() != null) {
					OrdersRoyalty or = new OrdersRoyalty();
					or.setKind(3);
					or.setOrderId(orderId);
					or.setOrdersDtId(ordersProduct.getId());
					or.setShopId(ordersProduct.getShopId());
					or.setAmount(ordersProduct.getProductRoyal());
					or.setUserId(ordersProduct.getProductUser());
					or.setQuantity(ordersProduct.getQuantity());
					or.setItemId(ordersProduct.getProductId());
					ordersRoyalties.add(or);
				}
				sb.append(ordersProduct.getId());
				sb.append(",");
			}

			if (!ObjectUtils.isEmpty(ordersRoyalties))
				ordersRoyaltyMapper.insertBatches(ordersRoyalties);
			
			//修改订单金额
			productRepertoryService.updateOrderAmt(orderId);
		}
		return RetInfo.Success(sb.toString());
	}

	@Override
	public OrdersProduct queryById(Integer id, Integer shopId) {
		OrdersProduct ordersProduct = ordersProductMapper
				.selectByPrimaryKey(id);
		Product product = productMapper.selectByPrimaryKey(ordersProduct
				.getProductId());
		if (product != null) {
			ordersProduct.setProductName(product.getProductName());
		}

		ordersProduct.setProductRoyal(BigDecimal.ZERO);

		OrdersRoyalty qOr = new OrdersRoyalty();
		qOr.setOrdersDtId(id);
		qOr.setKind(3);
		List<OrdersRoyalty> royalties = ordersRoyaltyMapper.getRoyalList(qOr);
		if (!ObjectUtils.isEmpty(royalties)) {
			for (OrdersRoyalty or : royalties) {
				ordersProduct.setProductUser(or.getUserId());
				ordersProduct.setProductRoyal(or.getAmount());
			}
		}
		return ordersProduct;
	}

	@Override
	public List<OrdersProduct> queryListByOrderId(Integer orderId) {
		OrdersRoyalty qOr = new OrdersRoyalty();
		qOr.setOrderId(orderId);
		qOr.setKind(3);
		List<OrdersRoyalty> royalties = ordersRoyaltyMapper.getRoyalList(qOr);
		List<OrdersProduct> list = ordersProductMapper
				.queryListByOrderId(orderId);
		if (!ObjectUtils.isEmpty(royalties) && !ObjectUtils.isEmpty(list)) {
			for (OrdersProduct op : list) {
				for (OrdersRoyalty or : royalties) {
					if (op.getId().equals(or.getOrdersDtId())) {
						if (or.getKind() == 3) {
							op.setProductUser(or.getUserId());
							op.setProductRoyal(or.getAmount());
						}
					}

				}
			}
		}

		return list;
	}

	@Override
	public GrdData queryProductsByOrderId(Integer orderId) {
		List<OrdersProduct> list = this.queryListByOrderId(orderId);
		return new GrdData(Long.valueOf(list.size()), list);
	}

	@Override
	public RetInfo updateOrdersProduct(OrdersProduct ordersProduct) throws Exception {
		if (ordersProduct.getId() == null){
			return RetInfo.Error("订单产品主键为空"); 
		}
		
		String msg = productRepertoryService.updateProduct(ordersProduct.getId(),ordersProduct.getQuantity() );
		if (StringUtils.isNotBlank(msg)){
			return RetInfo.Error(msg); 
		}
		
		ordersProductMapper.updateByPrimaryKeySelective(ordersProduct);
		
		ordersRoyaltyMapper.deleteByDtId(ordersProduct.getId(), 3); //先删除提成记录，再新增
		if(ordersProduct.getProductUser() != null){ //说明有提成人员
			OrdersRoyalty or = new OrdersRoyalty();
			or.setKind(3);
			or.setOrderId(ordersProduct.getOrderId());
			or.setOrdersDtId(ordersProduct.getId());
			or.setShopId(ordersProduct.getShopId());
			or.setAmount(ordersProduct.getProductRoyal());
			or.setUserId(ordersProduct.getProductUser());
			or.setQuantity(ordersProduct.getQuantity());
			or.setItemId(ordersProduct.getProductId());
			ordersRoyaltyMapper.insertSelective(or);
		}
		
		//修改订单金额
		productRepertoryService.updateOrderAmt(ordersProduct.getOrderId());
		
		return RetInfo.Success("");
	}

	@Override
	public RetInfo delOrdersProduct(Integer ordersProductId) throws Exception {
		if (ordersProductId == null){
			return RetInfo.Error("订单产品主键为空"); 
		}
		
		String msg = productRepertoryService.retrunProduct(ordersProductId, -1);
		if (StringUtils.isNotBlank(msg)){
			return RetInfo.Error(msg); 
		}
		
		OrdersProduct ordersProduct = ordersProductMapper.selectByPrimaryKey(ordersProductId);
		
		if (ordersProduct.getMealPayRecordId() != null){ //删除客户套餐
			MealPayRecord mRecord =  mealPayRecordMapper.selectByPrimaryKey(ordersProduct.getMealPayRecordId());
			//异动客户套餐数量
			CustomMealDt cMealDt = new  CustomMealDt();
			cMealDt.setId(mRecord.getCustMealDtId());
			cMealDt.setAddQuantity(mRecord.getQuantity());
			customMealDtMapper.updateByPrimaryKeySelective(cMealDt);
			//删除套餐使用记录表
			mealPayRecordMapper.deleteByPrimaryKey(ordersProduct.getMealPayRecordId());
		}
		ordersProductMapper.deleteByPrimaryKey(ordersProductId);
		ordersRoyaltyMapper.deleteByDtId(ordersProduct.getId(), 3); //删除提成记录
		
		//修改订单金额
		productRepertoryService.updateOrderAmt(ordersProduct.getOrderId());
		
		return RetInfo.Success("");
	}
}
