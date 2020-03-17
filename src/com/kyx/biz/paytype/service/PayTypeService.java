package com.kyx.biz.paytype.service;

import java.util.List;
import java.util.Map;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.dailypay.model.DailyPayRecord;
import com.kyx.biz.paytype.model.PayType;


public interface PayTypeService {
	
	
//	List<PayType> getByShopId(Integer shopId);
	
	GrdData getList(PayType payType, Pager pager);
	
	Map<Integer, String> getPayTypeMap();
	
	List<PayType> getArrayList(PayType payType);

	RetInfo saveData(PayType payType);
	
	/**
	 * 查询
	 * @param request
	 * @param id
	 * @return
	 */
	PayType queryById(Integer id);

	RetInfo deleteType(Integer id);
    /**
     * 根据支付名称校验名称是否重复
     * @param payName
     * @return
     */
	RetInfo chkName(String payName);
    /**
     * 查询支付方式
     * @param type 1客户支付2店铺支付
     * @return
     */
	List<PayType> getPayType(Integer type);
	
	   /**
     * 查询支付方式
     * @param type 1客户支付2店铺支付
     * @return
     */
	List<PayType> getPayTypes(Integer type);
}
