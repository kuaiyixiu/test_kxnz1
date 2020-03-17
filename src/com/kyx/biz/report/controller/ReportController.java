package com.kyx.biz.report.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.orders.model.Orders;
import com.kyx.biz.orders.model.OrdersEvaluate;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.report.service.ReportService;
import com.kyx.biz.report.vo.ReportQueryVo;
import com.kyx.biz.report.vo.UserRoyalty;

/**
 * 报表
 * @author 严大灯
 * @data 2019-8-20 上午9:06:07
 * @Descripton
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController {
	
	@Resource
	private ReportService reportService; 
	
	
	@RequestMapping ("/{index}")
	public String infoList(@PathVariable("index") String index,Model model){
		if("orderReport".indexOf(index) > -1  || "carConsumeReport".indexOf(index) > -1  || "userRoyaltyReport".indexOf(index) > -1 ){
			model.addAttribute("nextOneMonth", AppUtils.getDayByMonth(-1));
			model.addAttribute("nextTwoMonth", AppUtils.getDayByMonth(-2));
			model.addAttribute("nextThreeMonth", AppUtils.getDayByMonth(-3));
		}
		return "report/"+index;
	}
	/**
	 * 微信关注统计图表
	 * @return
	 */
    @RequestMapping(value="/getWechatChart",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getWechatChart() {
    	Map map=reportService.getWechatChart();
    	return JSON.toJSONString(map);
    }
    /**
     * 库存消耗统计
     * @param product
     * @param pager
     * @return
     */
    @RequestMapping(value="/queryProduct",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String queryProduct(Product product,Pager pager) {
    	GrdData grdData=reportService.queryProduct(product,pager);
    	return JSON.toJSONString(grdData);
    }
    
    /**
     * 充值统计图表
     * @param request
     * @return
     */
	@RequestMapping(value = "/getRechargeRep", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getRechargeRep(HttpServletRequest request,ReportQueryVo reportQueryVo) {
		reportQueryVo.setShopId(getShopId(request));
		Map map=reportService.getRechargeRep(reportQueryVo);
		return JSON.toJSONString(map);
	}
	
    
    
    /**
     * 订单数量
     * @param request
     * @param reportQueryVo
     * @return
     */
    @RequestMapping(value="/getOrderCountChart",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getOrderCountChart(HttpServletRequest request,ReportQueryVo reportQueryVo) {
    	Map map=reportService.getEchartOrderCountInfo(getShopId(request), reportQueryVo.getStartDate(), reportQueryVo.getEndDate());
    	return JSON.toJSONString(map);
    }
    
    /**
     * 订单利润统计
     * @param request
     * @param reportQueryVo
     * @return
     */
    @RequestMapping(value="/getEchartOrderProfitInfo",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getEchartOrderProfitInfo(HttpServletRequest request,ReportQueryVo reportQueryVo) {
    	Map map=reportService.getEchartOrderProfitInfo(getShopId(request), reportQueryVo.getStartDate(), reportQueryVo.getEndDate());
    	return JSON.toJSONString(map);
    }
    
    /**
     * 订单利润统计
     * @param request
     * @param reportQueryVo
     * @return
     */
    @RequestMapping(value="/getEchartOrderEvaluateInfo",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getEchartOrderEvaluateInfo(HttpServletRequest request,ReportQueryVo reportQueryVo) {
    	Map map=reportService.getEchartOrderEvaluateInfo(getShopId(request), reportQueryVo.getStartDate(), reportQueryVo.getEndDate());
    	return JSON.toJSONString(map);
    }
    
    /**
     * 充值明细
     * @param request
     * @param reportQueryVo
     * @return
     */
    @RequestMapping(value="/queryRechargeDetail",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String queryRechargeDetail(HttpServletRequest request,ReportQueryVo reportQueryVo) {
    	reportQueryVo.setShopId(getShopId(request));
    	GrdData data=reportService.queryRechargeDetail(reportQueryVo);
    	return JSON.toJSONString(data);
    }
    
    /**
     * 显示订单汇总详情
     * @param index
     * @param model
     * @return
     */
	@RequestMapping ("/showReportOrderDetail")
	public String showReportOrderDetail(@RequestParam String createTime,Model model){
		model.addAttribute("createTime", createTime);
		return "report/showReportOrderDetail";
	}
	
    
    @RequestMapping(value="/getOrderDetailInfo",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getOrderDetailInfo(HttpServletRequest request,@RequestParam String createTime) throws ParseException {
    	Map map=reportService.getOrderDetailInfo(getShopId(request),createTime);
    	return JSON.toJSONString(map);
    }
    
    /**
     * 服务分类产值
     * @param request
     * @param reportQueryVo
     * @return
     */
    @RequestMapping(value="/queryServeRep",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String queryServeRep(HttpServletRequest request,ReportQueryVo reportQueryVo) {
    	reportQueryVo.setShopId(getShopId(request));
    	GrdData data=reportService.queryServeRep(reportQueryVo);
    	return JSON.toJSONString(data);
    }
    
    /**
     * 产品分类产值
     * @param request
     * @param reportQueryVo
     * @return
     */
    @RequestMapping(value="/queryProductRep",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String queryProductRep(HttpServletRequest request,ReportQueryVo reportQueryVo) {
    	reportQueryVo.setShopId(getShopId(request));
    	GrdData data=reportService.queryProductRep(reportQueryVo);
    	return JSON.toJSONString(data);
    }
    
    
    /**
     * 显示订单汇评价明细
     * @param index
     * @param model
     * @return
     */
	@RequestMapping ("/showEvaluateDetail")
	public String showEvaluateDetail(@RequestParam String dateRangeStartTime,@RequestParam String dateRangeEndTime,Integer stars, Model model){
		model.addAttribute("dateRangeStartTime", dateRangeStartTime);
		model.addAttribute("dateRangeEndTime", dateRangeEndTime);
		model.addAttribute("stars", stars);
		return "report/showEvaluateDetail";
	}
	
    @RequestMapping(value="/getOrderEvaluateInfo",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getOrderEvaluateInfo(HttpServletRequest request,@RequestParam String startDate,@RequestParam String endDate,@RequestParam Integer stars) throws ParseException {
    	SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	List<OrdersEvaluate>  list = reportService.getOrderEvaluateInfo(getShopId(request),sDateFormat.parse(startDate+" 00:00:00"),	sDateFormat.parse(endDate+"  23:59:59"),stars);
    	return JSON.toJSONString(list);
    	
    }
    /**
     * 查询会员流失统计
     * @param request
     * @return
     */
    @RequestMapping(value="/getLossRep",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getLossRep(HttpServletRequest request) {
    	ReportQueryVo reportQueryVo=new ReportQueryVo();
    	reportQueryVo.setShopId(getShopId(request));
    	Map map=reportService.getLossRep(reportQueryVo);
    	return JSON.toJSONString(map);
    }
    
    
    
    @RequestMapping(value="/getPayRecordReport",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getPayRecordReport(HttpServletRequest request,@RequestParam String startDate,@RequestParam String endDate) throws ParseException {
    	SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	List<PayRecord>  list = reportService.getPayRecordReport(getShopId(request),sDateFormat.parse(startDate+" 00:00:00"),	sDateFormat.parse(endDate+"  23:59:59"));
    	return JSON.toJSONString(list);
    	
    }
    
    @RequestMapping(value="/getCarConsumeReport",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getCarConsumeReport(HttpServletRequest request,@RequestParam String startDate,@RequestParam String endDate) throws ParseException {
    	SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	List<Orders>  list = reportService.getCarConsumeReport(getShopId(request),sDateFormat.parse(startDate+" 00:00:00"),	sDateFormat.parse(endDate+"  23:59:59"));
    	return JSON.toJSONString(list);
    	
    }
    @RequestMapping(value="/getUserRoyaltyReport",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getUserRoyaltyReport(HttpServletRequest request,@RequestParam String startDate,@RequestParam String endDate) throws ParseException {
    	SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	List<UserRoyalty>  list = reportService.getUserRoyaltyReport(request,getShopId(request),sDateFormat.parse(startDate+" 00:00:00"),	sDateFormat.parse(endDate+"  23:59:59"));
    	return JSON.toJSONString(list);
    	
    }

}
