package com.kyx.biz.dailypay.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.dailypay.model.DailyPayRecord;
import com.kyx.biz.dailypay.model.DailyPayType;
import com.kyx.biz.dailypay.service.DailyPayRecordService;
import com.kyx.biz.dailypay.service.DailyPayTypeService;

/**
 * 日常收支类型管理
 * 
 * @author 童亦刚
 * @Descripton
 */
@Controller
@RequestMapping(value = "/dailyPay")
public class DailyPayController extends BaseController {
	
	@Resource
	private DailyPayTypeService dailyPayTypeService;
	
	@Resource
	private DailyPayRecordService dailyPayRecordService;
	
	
	@RequestMapping("/payTypeList")
	public String payTypeList(Model model) {
		return "dailypay/payTypeList";
	}
	
	@RequestMapping("/payRecordList")
	public String payRecordList(Model model,HttpServletRequest request) {
		Map<Integer, String> payMap  = dailyPayTypeService.getPayTypeMap(getShopId(request));
		model.addAttribute("payMap", JSON.toJSONString(payMap));
		
		return "dailypay/payRecordList";
	}
	
	@RequestMapping(value = "/getPayTypeList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getPayTypeList(HttpServletRequest request,DailyPayType dailyPayType, Pager pager) {
		dailyPayType.setShopId(getShopId(request));
		GrdData result = dailyPayTypeService.getList(dailyPayType, pager);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping(value = "/getPayRecordList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getPayRecordList(HttpServletRequest request,DailyPayRecord dailyPayRecord, Pager pager) {
		dailyPayRecord.setShopId(getShopId(request));
		GrdData result = dailyPayRecordService.getList(dailyPayRecord, pager);
		return JSON.toJSONString(result);
	}
	
	@RequestMapping("/toAddRecord")
	public String toAddRecord(Model model, HttpServletRequest request) {
		DailyPayType type = new DailyPayType();
		type.setShopId(getShopId(request));
		List<DailyPayType> typeList = dailyPayTypeService.getTypeList(type);
		model.addAttribute("typeList", typeList);
		return "dailypay/updatePayRecord";
	}
	
	
	@RequestMapping("/toAddType")
	public String toAddType(Model model, HttpServletRequest request) {
		return "dailypay/updatePayType";
	}
	
	@RequestMapping("/toUpdateType")
	public String toUpdateType(Model model, HttpServletRequest request, Integer id) {
		DailyPayType dailyPayType =  dailyPayTypeService.queryById(id);
		model.addAttribute("dailyPayType",dailyPayType);
 		return "dailypay/updatePayType";
	}
	
	@RequestMapping("/toUpdateRecord")
	public String toUpdateRecord(Model model, HttpServletRequest request, Integer id) {
		DailyPayType type = new DailyPayType();
		type.setShopId(getShopId(request));
		List<DailyPayType> typeList = dailyPayTypeService.getTypeList(type);
		model.addAttribute("typeList", typeList);
		DailyPayRecord dailyPayRecord =  dailyPayRecordService.queryById(id);
		model.addAttribute("dailyPayRecord",dailyPayRecord);
		return "dailypay/updatePayRecord";
	}
	
	@RequestMapping(value = "/updateType", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String updateType( HttpServletRequest request,DailyPayType dailyPayType) {
		RetInfo retInfo = null;
		if (dailyPayType.getId() == null){
			dailyPayType.setShopId(getShopId(request));
			retInfo =  dailyPayTypeService.saveType(dailyPayType);
		}else{
			retInfo = dailyPayTypeService.updateType(dailyPayType);
		}
		return AppUtils.getReturnInfo(retInfo);
	}
	
	
	@RequestMapping("/delType")
	@ResponseBody
	public String delType(Integer ids) {
		RetInfo retInfo = dailyPayTypeService.deleteType(ids);
		return AppUtils.getReturnInfo(retInfo);
	}
	
	@RequestMapping("/delRecord")
	@ResponseBody
	public String delRecord(Integer ids) {
		RetInfo retInfo = dailyPayRecordService.deleteRecord(ids);
		return AppUtils.getReturnInfo(retInfo);
	}
	
	
	@RequestMapping(value = "/updateRecord", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String updateRecord( HttpServletRequest request,DailyPayRecord dailyPayRecord) {
		RetInfo retInfo = null;
		if (dailyPayRecord.getId() == null){
			dailyPayRecord.setShopId(getShopId(request));
			retInfo =  dailyPayRecordService.saveRecord(dailyPayRecord);
		}else{
			retInfo = dailyPayRecordService.updateRecord(dailyPayRecord);
		}
		return AppUtils.getReturnInfo(retInfo);
	}
	


	 
}
