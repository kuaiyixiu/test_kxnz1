package com.kyx.biz.payRecord.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.orders.service.OrdersService;
import com.kyx.biz.payRecord.model.PayRecord;
import com.kyx.biz.payRecord.service.PayRecordService;
import com.kyx.biz.payRecord.vo.ProfitVo;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;

@Controller
@RequestMapping(value = "/payRecord")
public class PayRecordController extends BaseController{

	@Resource
	private PayRecordService payRecordService;
	
	@Resource
	private OrdersService ordersService;
	
	@Resource
	private PayTypeService payTypeService;
	
	@RequestMapping("/payRecordList")
	public String payRecordList(Model model) {
		PayType type = new PayType();
		List<PayType> typeList =  payTypeService.getArrayList(type);
		model.addAttribute("typeList", typeList);
		return "payrecord/payRecordList";
	}

	@RequestMapping(value = "/getList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getList(HttpServletRequest request, PayRecord payRecord, Pager pager) {
		payRecord.setShopId(getShopId(request));
		GrdData result = payRecordService.getList(payRecord, pager);
		return JSON.toJSONString(result);
	}
	
	
	@RequestMapping("/profitList")
	public String profitList(Model model) {
		return "payrecord/profitList";
	}
	
	@RequestMapping(value = "/getProfitList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getProfitList(HttpServletRequest request,ProfitVo profitVo){
		List<ProfitVo> list = ordersService.getProFitList(profitVo.getDateRangeStartTime(), profitVo.getDateRangeEndTime());
		return  JSON.toJSONString(new GrdData(Long.valueOf(list.size()),list));
	}
	/**
	 * 获取前七天数据报表统计
	 * @param request
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getEchatInfo", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getEchatInfo(HttpServletRequest request) {
		Map map=payRecordService.getEchatInfo(getShopId(request));
		return JSON.toJSONString(map);
	}
	
}
