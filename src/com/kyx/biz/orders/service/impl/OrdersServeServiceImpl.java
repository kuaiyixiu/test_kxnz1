package com.kyx.biz.orders.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.customMealDt.mapper.CustomMealDtMapper;
import com.kyx.biz.customMealDt.model.CustomMealDt;
import com.kyx.biz.orders.mapper.OrdersRoyaltyMapper;
import com.kyx.biz.orders.mapper.OrdersServeMapper;
import com.kyx.biz.orders.model.OrdersRoyalty;
import com.kyx.biz.orders.model.OrdersServe;
import com.kyx.biz.orders.service.OrdersServeService;
import com.kyx.biz.payRecord.mapper.MealPayRecordMapper;
import com.kyx.biz.payRecord.model.MealPayRecord;
import com.kyx.biz.product.service.ProductRepertoryService;
import com.kyx.biz.serve.mapper.ServeMapper;
import com.kyx.biz.serve.model.Serve;

@Service("ordersServeService")
public class OrdersServeServiceImpl implements OrdersServeService {

	@Resource
	private OrdersServeMapper ordersServeMapper;

	@Resource
	private ServeMapper serveMapper;

	@Resource
	private OrdersRoyaltyMapper ordersRoyaltyMapper;
	
	@Resource
	private MealPayRecordMapper mealPayRecordMapper;
	
	@Resource
	private CustomMealDtMapper customMealDtMapper;
	
	@Resource
	private ProductRepertoryService productRepertoryService;

	@Override
	public RetInfo saveOrdersServeList(List<OrdersServe> ordersServes, Integer orderId) {
		 if(!ObjectUtils.isEmpty(ordersServes)){
			 for (int i=0;i<ordersServes.size();i++){
				 OrdersServe os = ordersServes.get(i);
				 os.setOrderId(orderId);
			 }
		 }
		
		StringBuffer sb = new StringBuffer("");
		if (!ObjectUtils.isEmpty(ordersServes)) {
			ordersServeMapper.insertBatches(ordersServes);
			List<OrdersRoyalty> ordersRoyalties = new ArrayList<OrdersRoyalty>();
			for (OrdersServe ordersServe : ordersServes) {
				if (ordersServe.getServeUser() != null) { // 施工人员
					OrdersRoyalty or = new OrdersRoyalty();
					or.setKind(1);
					or.setOrderId(orderId);
					or.setOrdersDtId(ordersServe.getId());
					or.setShopId(ordersServe.getShopId());
					or.setAmount(ordersServe.getServeRoyal());
					or.setUserId(ordersServe.getServeUser());
					or.setItemId(ordersServe.getServeId());
					or.setQuantity(1);
					ordersRoyalties.add(or);
				}

				if (ordersServe.getSellUser() != null) { // 销售人员
					OrdersRoyalty or = new OrdersRoyalty();
					or.setKind(2);
					or.setOrderId(orderId);
					or.setOrdersDtId(ordersServe.getId());
					or.setShopId(ordersServe.getShopId());
					or.setAmount(ordersServe.getSellRoyal());
					or.setUserId(ordersServe.getSellUser());
					or.setItemId(ordersServe.getServeId());
					or.setQuantity(1);
					ordersRoyalties.add(or);
				}
				sb.append(ordersServe.getId());
				sb.append(",");
			}

			if (!ObjectUtils.isEmpty(ordersRoyalties))
				ordersRoyaltyMapper.insertBatches(ordersRoyalties);

		}
		
		//修改订单金额
		productRepertoryService.updateOrderAmt(orderId);
		
		return RetInfo.Success(sb.toString());
	}
	
	
	@Override
	public RetInfo delOrdersServe(Integer ordersServeId) {
		if (ordersServeId == null){
			return RetInfo.Error("订单服务主键为空"); 
		}
		
		OrdersServe os = ordersServeMapper.selectByPrimaryKey(ordersServeId);
		if (os.getMealPayRecordId() != null){
			MealPayRecord mRecord =  mealPayRecordMapper.selectByPrimaryKey(os.getMealPayRecordId());
			//异动客户套餐数量
			CustomMealDt cMealDt = new  CustomMealDt();
			cMealDt.setId(mRecord.getCustMealDtId());
			cMealDt.setAddQuantity(mRecord.getQuantity());
			customMealDtMapper.updateByPrimaryKeySelective(cMealDt);
			//删除套餐使用记录表
			mealPayRecordMapper.deleteByPrimaryKey(os.getMealPayRecordId());	
		}
		
		ordersServeMapper.deleteByPrimaryKey(ordersServeId);  //删除服务表明细
		ordersRoyaltyMapper.deleteByDtId(ordersServeId, 1); //删除提成表
		ordersRoyaltyMapper.deleteByDtId(ordersServeId, 2);//删除提成表
		
		//修改订单金额
		productRepertoryService.updateOrderAmt(os.getOrderId());
		
		return RetInfo.Success("");
	}
	


	@Override
	public OrdersServe queryById(HttpServletRequest request, Integer id,
			Integer shopId) {
		OrdersServe ordersServe = ordersServeMapper.selectByPrimaryKey(id);
		Serve serve = serveMapper.selectByPrimaryKey(ordersServe.getServeId());
		if (serve != null) {
			ordersServe.setServeName(serve.getServeName());
		}
		ordersServe.setServeRoyal(BigDecimal.ZERO);
		ordersServe.setSellRoyal(BigDecimal.ZERO);

		OrdersRoyalty qOr = new OrdersRoyalty();
		qOr.setOrdersDtId(id);
		qOr.setInKind("1,2");
		List<OrdersRoyalty> royalties = ordersRoyaltyMapper.getRoyalList(qOr);
		if (!ObjectUtils.isEmpty(royalties)) {
			for (OrdersRoyalty or : royalties) {
				if (or.getKind() == 1) { // '施工服务提成
					ordersServe.setServeUser(or.getUserId());
					ordersServe.setServeRoyal(or.getAmount());
				} else if (or.getKind() == 2) { // 销售服务提成
					ordersServe.setSellUser(or.getUserId());
					ordersServe.setSellRoyal(or.getAmount());
				}
			}
		}
		return ordersServe;
	}

	@Override
	public List<OrdersServe> queryListByOrderId(Integer orderId) {
		OrdersRoyalty qOr = new OrdersRoyalty();
		qOr.setOrderId(orderId);
		qOr.setInKind("1,2");
		List<OrdersRoyalty> royalties = ordersRoyaltyMapper.getRoyalList(qOr);
		List<OrdersServe> list = ordersServeMapper.queryListByOrderId(orderId);
		if (!ObjectUtils.isEmpty(royalties) && !ObjectUtils.isEmpty(list)) {
			for (OrdersServe os : list) {
				for (OrdersRoyalty or : royalties) {
					if (os.getId().equals(or.getOrdersDtId())) {
						if (or.getKind() == 1) {
							os.setServeUser(or.getUserId());
							os.setServeRoyal(or.getAmount());
						} else if (or.getKind() == 2) {
							os.setSellUser(or.getUserId());
							os.setSellRoyal(or.getAmount());
						}
					}

				}
			}
		}
		return list;
	}

	@Override
	public GrdData queryServesByOrderId(Integer orderId) {
		List<OrdersServe> list = this.queryListByOrderId( orderId);

		return new GrdData(Long.valueOf(list.size()), list);
	}


	@Override
	public RetInfo updateOrdersServe(OrdersServe ordersServe) throws Exception {
		if (ordersServe.getId() == null){
			return RetInfo.Error("订单服务主键为空"); 
		}
		
		ordersServeMapper.updateByPrimaryKeySelective(ordersServe);
		
		ordersRoyaltyMapper.deleteByDtId(ordersServe.getId(), 1); // 删除提成明细
		ordersRoyaltyMapper.deleteByDtId(ordersServe.getId(), 2); // 删除提成明细
		
		if (ordersServe.getServeUser() != null) { // 施工人员
			OrdersRoyalty or = new OrdersRoyalty();
			or.setKind(1);
			or.setOrderId(ordersServe.getOrderId());
			or.setOrdersDtId(ordersServe.getId());
			or.setShopId(ordersServe.getShopId());
			or.setAmount(ordersServe.getServeRoyal());
			or.setUserId(ordersServe.getServeUser());
			or.setItemId(ordersServe.getServeId());
			or.setQuantity(1);
			ordersRoyaltyMapper.insertSelective(or);
		}

		if (ordersServe.getSellUser() != null) { // 销售人员
			OrdersRoyalty or = new OrdersRoyalty();
			or.setKind(2);
			or.setOrderId(ordersServe.getOrderId());
			or.setOrdersDtId(ordersServe.getId());
			or.setShopId(ordersServe.getShopId());
			or.setAmount(ordersServe.getSellRoyal());
			or.setUserId(ordersServe.getSellUser());
			or.setItemId(ordersServe.getServeId());
			or.setQuantity(1);
			ordersRoyaltyMapper.insertSelective(or);
		}
		
		//修改订单金额
		productRepertoryService.updateOrderAmt(ordersServe.getOrderId());
		return RetInfo.Success("");
	}



}
