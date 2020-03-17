package com.kyx.biz.report.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEvaluate;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.report.vo.ReportQueryVo;
import com.kyx.biz.report.vo.UserRoyalty;

public interface ReportService {
    /**
     * 获取绑定微信的客户数量和未绑定微信的客户数量
     * @return
     */
	Map getWechatChart();
    /**
     * 查询产品消耗列表
     * @param product
     * @param pager
     * @return
     */
	GrdData queryProduct(Product product, Pager pager);
	/**
	 * 充值消费查询图表
	 * @param reportQueryVo
	 * @return
	 */
	Map getRechargeRep(ReportQueryVo reportQueryVo);
	
	/**
	 * 获取日期区间数组
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Date> getDateList(Date startDate,Date endDate);
	

	/**
	 * 查询充值消费统计列表
	 * @param reportQueryVo
	 * @return
	 */
	GrdData queryRechargeDetail(ReportQueryVo reportQueryVo);
	
	
	/**
	 * 订单数量统计报表
	 * @param shopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map getEchartOrderCountInfo(Integer shopId,Date startDate,Date endDate);
	
	/**
	 * 订单利润统计报表
	 * @param shopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map getEchartOrderProfitInfo(Integer shopId,Date startDate,Date endDate);
	
	/**
	 * 订单评价统计报表
	 * @param shopId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map getEchartOrderEvaluateInfo(Integer shopId,Date startDate,Date endDate);
	
	/**
	 * 获取订单详情
	 * @param shopId
	 * @param startDate
	 * @return
	 * @throws ParseException 
	 */
	Map getOrderDetailInfo(Integer shopId,String date) throws ParseException;
	/**
	 * 服务分类产值
	 * @param reportQueryVo
	 * @return
	 */
	GrdData queryServeRep(ReportQueryVo reportQueryVo);
	/**
	 * 产品分类产值
	 * @param reportQueryVo
	 * @return
	 */
	GrdData queryProductRep(ReportQueryVo reportQueryVo);
	
	/**
	 * 获取评价统计详情
	 * @param shopId
	 * @param startDate
	 * @param endDate
	 * @param stars
	 * @return
	 */
	List<OrdersEvaluate>  getOrderEvaluateInfo(Integer shopId,Date startDate,Date endDate,Integer stars);
	
	
	/**
	 * 会员流失统计
	 * @param reportQueryVo
	 * @return
	 */
	Map getLossRep(ReportQueryVo reportQueryVo);
	/**
	 * 获取支付项报表
	 * @param shopId
	 * @param dateRangeStartTime
	 * @param dateRangeEndTime
	 * @return
	 */
	List<PayRecord> getPayRecordReport(Integer shopId,Date dateRangeStartTime,Date dateRangeEndTime);

	
	
	/**
	 * 获取汽车消费记录报表
	 * @param shopId
	 * @param dateRangeStartTime
	 * @param dateRangeEndTime
	 * @return
	 */
	List<Orders> getCarConsumeReport(Integer shopId,Date dateRangeStartTime,Date dateRangeEndTime);
	
	/**
	 * 查询产品提成报表
	 * @return
	 */
	List<UserRoyalty> getUserRoyaltyReport(HttpServletRequest request, Integer shopId,Date dateRangeStartTime,Date dateRangeEndTime);
}
