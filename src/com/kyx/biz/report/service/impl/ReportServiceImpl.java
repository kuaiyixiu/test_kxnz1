package com.kyx.biz.report.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.custom.mapper.CustCouponMapper;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.CustCoupon;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.orders.mapper.OrdersEvaluateMapper;
import com.kyx.biz.orders.mapper.OrdersMapper;
import com.kyx.biz.orders.mapper.OrdersRoyaltyMapper;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEvaluate;
import com.kyx.biz.orders.model.OrdersRoyalty;
import com.kyx.biz.payRecord.mapper.PayRecordMapper;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.product.mapper.ProductClassMapper;
import com.kyx.biz.product.mapper.ProductConsumeMapper;
import com.kyx.biz.product.mapper.ProductMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductConsume;
import com.kyx.biz.recharge.mapper.RechargeMapper;
import com.kyx.biz.recharge.model.Recharge;
import com.kyx.biz.report.service.ReportService;
import com.kyx.biz.report.vo.ReportQueryVo;
import com.kyx.biz.report.vo.ReportVo;
import com.kyx.biz.report.vo.UserRoyalty;
import com.kyx.biz.serve.mapper.ServeClassMapper;
@Service("reportService")
public class ReportServiceImpl implements ReportService {
	@Resource
	private CustomMapper customMapper;
	@Resource
	private ProductMapper productMapper;
	@Resource
	private RechargeMapper rechargeMapper;
	@Resource
	private OrdersMapper ordersMapper;
	@Resource
	private PayRecordMapper payRecordMapper;
	@Resource
	private ProductConsumeMapper productConsumeMapper;
	@Resource
	private OrdersEvaluateMapper ordersEvaluateMapper;
	@Resource
	private CustCouponMapper custCouponMapper;
	@Resource
	private ServeClassMapper serveClassMapper;
	@Resource
	private ProductClassMapper productClassMapper;
	@Resource
	private UserService userService;
	@Resource
	private OrdersRoyaltyMapper ordersRoyaltyMapper;
	
	
	@Override
	public Map getWechatChart() {
		List<Custom> list = customMapper.queryCustoms(null);
		int wechatBindCount = 0;
		for (Custom custom : list) {
			if (!StringUtils.isEmpty(custom.getWechatOpenId()))
				wechatBindCount++;

		}
		Map map = new HashMap();
		map.put("wechatBindCount", wechatBindCount);
		map.put("wechatNoBindCount", list.size() - wechatBindCount);
		map.put("totalCount", list.size());
		return map;
	}

	@Override
	public GrdData queryProduct(Product product, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Product> list = productMapper.queryProductRep(product);
		return new GrdData(page.getTotal(), list);
	}

	@Override
	public Map getRechargeRep(ReportQueryVo reportQueryVo) {
		//x坐标
		List<String> xlist=new ArrayList<String>();
		List<BigDecimal> ylist2=new ArrayList<BigDecimal>();//充值
		List<BigDecimal> ylist3=new ArrayList<BigDecimal>();//消费
		Date beginDate=reportQueryVo.getStartDate();
		Date endDate=reportQueryVo.getEndDate();
		//充值统计
		Recharge recharge=new Recharge();
		recharge.setDateRangeStartTime(reportQueryVo.getStartDate());
		recharge.setDateRangeEndTime(reportQueryVo.getEndDate());
		recharge.setShopId(reportQueryVo.getShopId());
		List<Recharge> dataList=rechargeMapper.getList(recharge);
		//消费统计
		PayRecord payRecord=new PayRecord();
		payRecord.setDateRangeEndTime(endDate);
		payRecord.setDateRangeStartTime(beginDate);
		payRecord.setInKind(3);
		payRecord.setShopId(reportQueryVo.getShopId());
		List<PayRecord> payRecords=payRecordMapper.getList(payRecord);
		Collections.reverse(payRecords);
		Collections.reverse(dataList); //按照时间正序
		List<Date> dateList=getDateList(beginDate, endDate);
		Integer idx = 0;
		Integer idx1 = 0;
		for(Date date : dateList){
			 double amt = 0;//充值金额
			 double amt1=0;
			 if(idx < dataList.size()){
				 Recharge r =  dataList.get(idx);
				 while (DateUtils.truncatedCompareTo(r.getRechargeTime(), date, Calendar.DATE) == 0) {
					 amt=amt+r.getRechargeAmount().doubleValue();
					 idx ++ ;
					 if(idx < dataList.size()){
						 r = dataList.get(idx);
					 }else{
						 break;
					 }
				 }
			 }
			 ylist2.add(new BigDecimal(amt));
			 
			 if(idx1 < payRecords.size()){
				 PayRecord p =  payRecords.get(idx1);
				 while (DateUtils.truncatedCompareTo(p.getAddTime(), date, Calendar.DATE) == 0) {
					 amt1=amt1+p.getAmount().doubleValue();
					 idx1 ++ ;
					 if(idx1 < payRecords.size()){
						 p = payRecords.get(idx1);
					 }else{
						 break;
					 }
				 }
			 }
			 ylist3.add(new BigDecimal(amt1));
		 }
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		for(Date date : dateList){
			xlist.add(format.format(date));	
		}
		 
		Map resultMap=new HashMap();
		resultMap.put("x", xlist);
		resultMap.put("y2",ylist2 );
		resultMap.put("y3",ylist3 );
		return resultMap;
	
	}
	@Override
	public Map getEchartOrderCountInfo(Integer shopId,Date startDate,Date endDate) {
		List<String> xlist=new ArrayList<String>();
		List<Integer> ylist1=new ArrayList<Integer>();//订单数
		List<Integer> ylist2=new ArrayList<Integer>();//收入
		List<Integer> ylist3=new ArrayList<Integer>();//支出
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Orders orders = new Orders();
		orders.setShopId(shopId);
		orders.setDateRangeStartTime(startDate);
		orders.setDateRangeEndTime(endDate);
		List<Orders> ordersList =   ordersMapper.getOrdersList(orders); //取得时间段内所有订单
		Collections.reverse(ordersList); //按照时间正序
		 List<Date> dateList = getDateList(startDate, endDate);//获取每段的时间间隔
		 Integer idx = 0;
		 List<ReportVo> detailList = new ArrayList<ReportVo>();
		 for(Date date : dateList){
			 Integer y1Count = 0;//进场数
			 Integer y2Count = 0;//挂账数
			 Integer y3Count = 0;//入账数
			 if(idx < ordersList.size()){
				 Orders o =  ordersList.get(idx);
				 while (DateUtils.truncatedCompareTo(o.getCreatTime(), date, Calendar.DATE) == 0) {
					 y1Count ++;
					 //5入账 6挂账
					 if(o.getOrderStatus() == 6){//挂账
						 y2Count ++;
					 }else if(o.getOrderStatus() == 5){//入账
						 y3Count ++;
					 }
					 idx ++ ;
					 if(idx < ordersList.size()){
						 o = ordersList.get(idx);
					 }else{
						 break;
					 }
				 }
			 }
			 ylist1.add(y1Count);
			 ylist2.add(y2Count);
			 ylist3.add(y3Count);
			 
			 ReportVo reportVo = new ReportVo();
			 reportVo.setCreatTime(format.format(date));
			 reportVo.setY1Count(y1Count);
			 reportVo.setY2Count(y2Count);
			 reportVo.setY3Count(y3Count);
			 detailList.add(reportVo);
		 }
		
		
		for(Date date : dateList){
			xlist.add(format.format(date));	
		}
		
		 Integer y1Total = 0;//进场数
		 Integer y2Total = 0;//挂账数
		 Integer y3Total = 0;//入账数
		 
		 for(Integer y1 : ylist1){
			 y1Total += y1;
		 }
		 for(Integer y2 : ylist2){
			 y2Total += y2;
		 }
		 for(Integer y3 : ylist3){
			 y3Total += y3;
		 }
		 
		 
		 
		Map resultMap=new HashMap();
		resultMap.put("x", xlist);
		resultMap.put("y1",ylist1 );
		resultMap.put("y2",ylist2 );
		resultMap.put("y3",ylist3 );

		//计算汇总数量
		ReportVo reportVo = new ReportVo();
		reportVo.setY1Count(y1Total);
		reportVo.setY2Count(y2Total);
		reportVo.setY3Count(y3Total);
		
		List<ReportVo> reportList = new ArrayList<ReportVo>();
		reportList.add(reportVo);
		resultMap.put("reportList", reportList);
		resultMap.put("detailList",detailList);
		return resultMap;
	}
	
	
	/**
	 * 获取日期区间数组
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Date> getDateList(Date startDate, Date endDate) {
		//int days = differentDays(startDate, endDate);
		int interval = 0;
		// if(days > 330){ //最大365天
		// interval = 12;
		// }else if (days > 300){
		// interval = 11;
		// }else if (days > 270){
		// interval = 10;
		// }else if (days > 240){
		// interval = 9;
		// }else if (days > 210){
		// interval = 8;
		// }else if (days > 180){
		// interval = 7;
		// }else if (days > 150){
		// interval = 6;
		// }else if (days > 120){
		// interval = 5;
		// }else if (days > 90){
		// interval = 4;
		// }else if (days > 60){
		// interval = 3;
		// }else if (days > 30){
		// interval = 2;
		// }else{
		// interval = 1;
		// }
		interval = 1;

		List<Date> dateList = new ArrayList<Date>();
		Boolean flag = true;
		Date date = startDate;
		while (flag) {
			if (DateUtils.truncatedCompareTo(date, endDate, Calendar.DATE) == -1) {
				// if (date.compareTo(endDate) < 0){
				dateList.add(date);
				date = DateUtils.addDays(date, interval);
			} else {
				dateList.add(endDate);
				flag = false;
			}
		}
		return dateList;
	}

	/**
	 * date2比date1多的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int differentDays(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);
		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}

	@Override
	public Map getEchartOrderProfitInfo(Integer shopId, Date startDate, Date endDate) {
		List<String> xlist = new ArrayList<String>();
		List<BigDecimal> ylist1 = new ArrayList<BigDecimal>();// 订单利润
		List<BigDecimal> ylist2 = new ArrayList<BigDecimal>();// 成本
		List<BigDecimal> ylist3 = new ArrayList<BigDecimal>();// 毛利润

		Orders orders = new Orders();
		orders.setDateRangeStartTime(startDate);
		orders.setDateRangeEndTime(endDate);
		List<Date> dateList = getDateList(startDate, endDate);// 获取每段的时间间隔

		//订单利润
		PayRecord record = new PayRecord();
		record.setDateRangeStartTime(startDate);
		record.setDateRangeEndTime(endDate);
		record.setInKind(3);  //3:(1,3,4)
		record.setShopId(shopId);
		List<PayRecord> payRecords = payRecordMapper.getList(record);
		Collections.reverse(payRecords); //按照时间正序
		//订单消耗
		ProductConsume productConsume = new  ProductConsume();
		productConsume.setDateRangeStartTime(startDate);
		productConsume.setDateRangeEndTime(endDate);
		productConsume.setKind(1); //订单消耗
		productConsume.setShopId(shopId);
		List<ProductConsume> productConsumes =  productConsumeMapper.getPriceList(productConsume);
		Collections.reverse(productConsumes); //按照时间正序
		//计算订单利润
		Integer idx = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		List<ReportVo>  reportList = new ArrayList<ReportVo>();
		 for(Date date : dateList){
			 ReportVo rv = new ReportVo();
			 rv.setCreatTime(format.format(date));
			 BigDecimal y1Count = BigDecimal.ZERO;
			 if(idx < payRecords.size()){
				 PayRecord p =  payRecords.get(idx);
				 while (DateUtils.truncatedCompareTo(p.getAddTime(), date, Calendar.DATE) == 0) {
					 y1Count = y1Count.add(p.getAmount());
					 idx ++ ;
					 if(idx < payRecords.size()){
						 p = payRecords.get(idx);
					 }else{
						 break;
					 }
				 }
			 }
			 rv.setY1Float(y1Count);
			 reportList.add(rv);
			 ylist1.add(y1Count);
		 }
		
		//利润订单消耗
		 idx = 0;
		 for(int i = 0 ;i < dateList.size();i++){
			 Date date = dateList.get(i);
			 ReportVo rv = reportList.get(i);
			 BigDecimal y2Count = BigDecimal.ZERO;
			 if(idx < productConsumes.size()){
				 ProductConsume pc =  productConsumes.get(idx);
				 while (DateUtils.truncatedCompareTo(pc.getConsumeDate(), date, Calendar.DATE) == 0) {
					 y2Count = y2Count.add(pc.getPrice().multiply(new BigDecimal(pc.getQuantity())));
					 idx ++ ;
					 if(idx < productConsumes.size()){
						 pc = productConsumes.get(idx);
					 }else{
						 break;
					 }
				 }
			 }
			 ylist2.add(y2Count);
			 rv.setY2Float(y2Count);
			 rv.setY3Float(rv.getY1Float().subtract(rv.getY2Float()));
		 }
		 
		 //计算毛利润
		 idx = 0;
		 for(int i=0;i<ylist1.size();i++){
			 BigDecimal y1Count = ylist1.get(i);
			 BigDecimal y2Count = ylist2.get(i);
			 BigDecimal y3Count = y1Count.subtract(y2Count);
			 ylist3.add(y3Count);
		 }

		for (Date date : dateList) {
			xlist.add(format.format(date));
		}
		
		
		BigDecimal y1Total = BigDecimal.ZERO;//进场数
		BigDecimal y2Total = BigDecimal.ZERO;//挂账数
		BigDecimal y3Total = BigDecimal.ZERO;//入账数
		 
		 for(BigDecimal y1 : ylist1){
			 y1Total = y1Total.add(y1);
		 }
		 for(BigDecimal y2 : ylist2){
			 y2Total = y2Total.add(y2);
		 }
		 for(BigDecimal y3 : ylist3){
			 y3Total = y3Total.add(y3);
		 }

		
		Map resultMap = new HashMap();
		resultMap.put("x", xlist);
		resultMap.put("y1", ylist1);
		resultMap.put("y2", ylist2);
		resultMap.put("y3", ylist3);
		
		//计算汇总数量
		ReportVo reportVo = new ReportVo();
		reportVo.setY1Float(y1Total);
		reportVo.setY2Float(y2Total);
		reportVo.setY3Float(y3Total);
		
		List<ReportVo> reportList2 = new ArrayList<ReportVo>();
		reportList2.add(reportVo);
		resultMap.put("reportList2", reportList2);
		resultMap.put("reportList", reportList);
		return resultMap;
	}

	@Override
	public Map getEchartOrderEvaluateInfo(Integer shopId, Date startDate, Date endDate) {
		List<String> xlist = new ArrayList<String>();
		List<Integer> ylist1 = new ArrayList<Integer>();// 订单利润
		
		OrdersEvaluate ordersEvaluate = new OrdersEvaluate();
		ordersEvaluate.setDateRangeStartTime(startDate);
		ordersEvaluate.setDateRangeEndTime(endDate);
		ordersEvaluate.setShopId(shopId);
		List<OrdersEvaluate> evaluateList = ordersEvaluateMapper.getList(ordersEvaluate);
		
		Integer one = 0;
		Integer two = 0;
		Integer three = 0;
		Integer four = 0;
		Integer five = 0;
		for(OrdersEvaluate oe : evaluateList){
			if(oe.getStars() == 1)
				one ++;
			else if(oe.getStars() == 2)
				two ++;
			else if (oe.getStars() == 3)
				three ++;
			else if (oe.getStars() == 4)
				four ++;
			else if (oe.getStars() == 5)
				five ++;
		}
		
		xlist.add("1分");
		xlist.add("2分");
		xlist.add("3分");
		xlist.add("4分");
		xlist.add("5分");
		
		ylist1.add(one);
		ylist1.add(two);
		ylist1.add(three);
		ylist1.add(four);
		ylist1.add(five);
		
		
		List<Map> evalList = new ArrayList<Map>();
		for(int i = 0 ; i<xlist.size() ; i++){
			String title = xlist.get(i);
			Integer stars = ylist1.get(i);
			Map<String,String> map = new HashMap<String,String>();
			map.put("stars",  new Integer(i+1).toString());
			map.put("title", title);
			map.put("count",stars.toString());
			evalList.add(map);
		}
		

		Map resultMap = new HashMap();
		resultMap.put("x", xlist);
		resultMap.put("y1", ylist1);
		resultMap.put("evalList", evalList);
		return resultMap;
	}

	@Override
	public Map getOrderDetailInfo(Integer shopId, String date) throws ParseException {
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Orders orders = new Orders();
		orders.setShopId(shopId);
		orders.setDateRangeStartTime(sDateFormat.parse(date+" 00:00:00"));
		orders.setDateRangeEndTime(sDateFormat.parse(date+"  23:59:59"));
		List<Orders> reportList1 =   ordersMapper.getOrdersCustList(orders); //取得时间段内所有订单
		List<Orders> reportList2 = new ArrayList<Orders>();
		List<Orders> reportList3 = new ArrayList<Orders>();
		
		for(Orders o : reportList1){
			 if(o.getOrderStatus() == 6){//挂账
				 reportList2.add(o);
			 }else if(o.getOrderStatus() == 5){//入账
				 reportList3.add(o);
			 }
		}
		Map resultMap = new HashMap();
		
		ReportVo reportVo = new ReportVo();
		reportVo.setY1Count(reportList1.size());
		reportVo.setY2Count(reportList2.size());
		reportVo.setY3Count(reportList3.size());
		List<ReportVo> reportList = new ArrayList<ReportVo>();
		reportList.add(reportVo);
		
		resultMap.put("reportList1", reportList);
		resultMap.put("reportList2", reportList2);
		resultMap.put("reportList3", reportList3);
		return resultMap;
	}

	@Override
	public GrdData queryRechargeDetail(ReportQueryVo reportQueryVo) {
		List<Date> dateList = getDateList(reportQueryVo.getStartDate(),reportQueryVo.getEndDate());
		Custom custom = new Custom();
		custom.setDateRangeEndTime(reportQueryVo.getEndDate());
		custom.setDateRangeStartTime(reportQueryVo.getStartDate());
		custom.setShopId(reportQueryVo.getShopId());
		List<Custom> customs = customMapper.getInfo(custom);	
		Recharge recharge=new Recharge();
		recharge.setShopId(reportQueryVo.getShopId());
		recharge.setDateRangeStartTime(reportQueryVo.getStartDate());
		recharge.setDateRangeEndTime(reportQueryVo.getEndDate());
		List<Recharge> recharges=rechargeMapper.getList(recharge);
		PayRecord payRecord=new PayRecord();
		payRecord.setDateRangeStartTime(reportQueryVo.getStartDate());
		payRecord.setDateRangeEndTime(reportQueryVo.getEndDate());
		payRecord.setInKind(3);
		payRecord.setShopId(reportQueryVo.getShopId());
		List<PayRecord> payRecords=payRecordMapper.getList(payRecord);
		CustCoupon custCoupon=new CustCoupon();
		custCoupon.setDateRangeStartTime(reportQueryVo.getStartDate());
		custCoupon.setDateRangeEndTime(reportQueryVo.getEndDate());
		custCoupon.setShopId(reportQueryVo.getShopId());
		List<CustCoupon> custCoupons=custCouponMapper.getRepList(custCoupon);
		PayRecord pr=new PayRecord();
		pr.setDateRangeStartTime(reportQueryVo.getStartDate());
		pr.setDateRangeEndTime(reportQueryVo.getEndDate());
		pr.setTypeId(BasicContant.payTypeContant.COUPON);
		pr.setShopId(reportQueryVo.getShopId());
		List<PayRecord> costCoupon=payRecordMapper.getList(pr);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int idx=0;
		int idx1=0;
		int idx2=0;
		int idx3=0;
		int idx4=0;
		List<Map> maps=new ArrayList<Map>();
		Collections.reverse(dateList);
		for (Date date : dateList) {
			Map map = new HashMap();
			map.put("dateStr", format.format(date));
			int openCardCount=0;
			if (idx < customs.size()) {
				Custom o = customs.get(idx);
				while (DateUtils.truncatedCompareTo(o.getAddTime(), date,Calendar.DATE) == 0) {
					openCardCount++;
					idx++;
					if (idx < customs.size()) {
						o = customs.get(idx);
					}else{
						break;
					}
				}
			}
			map.put("openCardCount", openCardCount);		
			int rechargeCount=0;
			double rechargeAmt=0;
			if (idx1 < recharges.size()) {
				Recharge o = recharges.get(idx1);
				while (DateUtils.truncatedCompareTo(o.getRechargeTime(), date,Calendar.DATE) == 0) {
					rechargeCount++;
					rechargeAmt=rechargeAmt+o.getRechargeAmount().doubleValue();
					idx1++;
					if (idx1 < recharges.size()) {
						o = recharges.get(idx1);
					}else{
						break;
					}
				}
			}
			map.put("rechargeCount", rechargeCount);
			map.put("rechargeAmt", rechargeAmt);		
			int costCount=0;
			double costAmt=0;
			if (idx2 < payRecords.size()) {
				PayRecord o = payRecords.get(idx2);
				while (DateUtils.truncatedCompareTo(o.getAddTime(), date,Calendar.DATE) == 0) {
					costCount++;
					costAmt=costAmt+o.getAmount().doubleValue();
					idx2++;
					if (idx2 < payRecords.size()) {
						o = payRecords.get(idx2);
					}else{
						break;
					}
				}
			}
			map.put("costCount", costCount);
			map.put("costAmt", costAmt);
			
			int couponCount=0;
			double couponAmt=0;
			if (idx3 < custCoupons.size()) {
				CustCoupon o = custCoupons.get(idx3);
				while (DateUtils.truncatedCompareTo(o.getCreateTime(), date,Calendar.DATE) == 0) {
					couponCount++;
					couponAmt=couponAmt+o.getTotalAmount().doubleValue();
					idx3++;
					if (idx3 < custCoupons.size()) {
						o = custCoupons.get(idx3);
					}else{
						break;
					}
				}
			}
			map.put("couponCount", couponCount);
			map.put("couponAmt", couponAmt);
			
			
			int costCouponCount=0;
			double costCouponAmt=0;
			if (idx4 < costCoupon.size()) {
				PayRecord o = costCoupon.get(idx4);
				while (DateUtils.truncatedCompareTo(o.getAddTime(), date,Calendar.DATE) == 0) {
					costCouponCount++;
					costCouponAmt=costCouponAmt+o.getAmount().doubleValue();
					idx4++;
					if (idx4 < costCoupon.size()) {
						o = costCoupon.get(idx4);
					}else{
						break;
					}
				}
			}
			map.put("costCouponCount", costCouponCount);
			map.put("costCouponAmt", costCouponAmt);
			maps.add(map);
		}
		return new GrdData(Long.valueOf(maps.size()), maps);
	}

	@Override
	public GrdData queryServeRep(ReportQueryVo reportQueryVo) {
		List<Map> maps=serveClassMapper.queryServeRep(reportQueryVo);
		GrdData grdData=new GrdData(Long.valueOf(maps.size()), maps);
		return grdData;
	}

	@Override
	public GrdData queryProductRep(ReportQueryVo reportQueryVo) {
		List<Map> maps=productClassMapper.queryProductRep(reportQueryVo);
		GrdData grdData=new GrdData(Long.valueOf(maps.size()), maps);
		return grdData;
	}

	@Override
	public List<OrdersEvaluate> getOrderEvaluateInfo(Integer shopId, Date startDate, Date endDate, Integer stars) {
		OrdersEvaluate ordersEvaluate = new OrdersEvaluate();
		ordersEvaluate.setDateRangeStartTime(startDate);
		ordersEvaluate.setDateRangeEndTime(endDate);
		ordersEvaluate.setShopId(shopId);
		ordersEvaluate.setStars(stars);
		List<OrdersEvaluate> evaluateList = ordersEvaluateMapper.getCustList(ordersEvaluate);
		return evaluateList;
	}

	@Override
	public Map getLossRep(ReportQueryVo reportQueryVo) {
		//45天
		Date now=new Date();
		now=AppUtils.getEndTime(now);
		Date date1=AppUtils.dateAdd(now, -45);
		reportQueryVo.setEndDate(date1);
		Integer count1=customMapper.getLossRep(reportQueryVo);
		Date date2=AppUtils.MonthAdd(now, -3);
		reportQueryVo.setEndDate(date2);
		Integer count2=customMapper.getLossRep(reportQueryVo);
		Date date3=AppUtils.MonthAdd(now, -6);
		reportQueryVo.setEndDate(date3);
		Integer count3=customMapper.getLossRep(reportQueryVo);
		Date date4=AppUtils.MonthAdd(now, -12);
		reportQueryVo.setEndDate(date4);
		Integer count4=customMapper.getLossRep(reportQueryVo);
		Integer count=customMapper.getCount();
		Map map=new HashMap();
		map.put("allCount", count);
		map.put("count1", count1);
		map.put("count2", count2);
		map.put("count3", count3);
		map.put("count4", count4);
		return map;
	}

	@Override
	public List<PayRecord> getPayRecordReport(Integer shopId, Date dateRangeStartTime, Date dateRangeEndTime) {
		return payRecordMapper.getPayRecordReport(shopId, dateRangeStartTime, dateRangeEndTime);
	}

	@Override
	public List<Orders> getCarConsumeReport(Integer shopId, Date dateRangeStartTime, Date dateRangeEndTime) {
		return ordersMapper.getCarConsumeReport(shopId, dateRangeStartTime, dateRangeEndTime);
	}
	
	@Override
	public List<UserRoyalty> getUserRoyaltyReport(HttpServletRequest request,Integer shopId, Date dateRangeStartTime, Date dateRangeEndTime) {
		List<OrdersRoyalty> royaltyList = ordersRoyaltyMapper.getOrdersRoyaltyReport(shopId, dateRangeStartTime, dateRangeEndTime);
		
		Shops shops = (Shops) request.getSession().getAttribute("shopsSession");
		Dbs.setDbName(Dbs.getMainDbName());
		List<User> users = userService.getByShopId(shops.getId());

		List<UserRoyalty> userRoyaltyList = new ArrayList<UserRoyalty>();
		for(User user : users){
			UserRoyalty ur = new UserRoyalty();
			ur.setUserId(user.getId()); //设置userID值
			ur.setUserRealname(user.getUserRealname()); //设置用户名
			Boolean hasRoy = false;//是否有产值
			for(OrdersRoyalty or : royaltyList){
				if(or.getUserId().equals(ur.getUserId())){
					if(or.getKind() == 1){//施工服务提成
						ur.setServeRoyal(or.getAmount());
						hasRoy = true;
					}
					else if(or.getKind() == 2){ //销售服务提成
						ur.setSellRoyal(or.getAmount());
						hasRoy = true;
					}
					else if(or.getKind() == 3){//产品销售提成
						ur.setProductRoyal(or.getAmount());
						hasRoy =true;
					}
				}
			}
			if(hasRoy){
				userRoyaltyList.add(ur);
			}
		}
		
		return userRoyaltyList;
	}
}
