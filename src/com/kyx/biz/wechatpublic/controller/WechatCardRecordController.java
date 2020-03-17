package com.kyx.biz.wechatpublic.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;
import com.kyx.biz.wechatpublic.service.WechatCardRecordService;
/**
 * 
 * @author 严大灯
 * @data 2019-7-31 上午11:11:35
 * @Descripton
 */
@Controller
@RequestMapping("wechatcardrecord")
public class WechatCardRecordController extends BaseController{
	@Resource
	private WechatCardRecordService wechatCardRecordService;
	/*
	 * 卡卷设置列表
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {

		return "wechatpublic/cardrecordlist";
	}
	/**
	 * 列表数据
	 * @param cardSet
	 * @param pager
	 * @param session
	 * @return
	 */
    @RequestMapping(value="/getList")
    @ResponseBody
    public String getList(WechatCardRecord wechatCardRecord,Pager pager,HttpSession session) {
    	GrdData result=wechatCardRecordService.getInfo(wechatCardRecord,pager);
        return JSON.toJSONString(result);
    }
    
    /**
     * 作废
     * @param ids
     * @param voidRemark
     * @param session
     * @return
     */
    @RequestMapping(value="/destroyInfo")
    @ResponseBody
    public String destroyInfo(String ids,String voidRemark,HttpSession session) {
    	RetInfo retInfo=wechatCardRecordService.updateData(ids,voidRemark);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 使用记录
     * @param request
     * @param model
     * @return
     */
	@RequestMapping("/userrecord")
	public String userrecord(HttpServletRequest request, Model model,Integer id) {
		WechatCardRecord wechatCardRecord=wechatCardRecordService.selectById(id);
		model.addAttribute("wechatCardRecord", wechatCardRecord);
		return "wechatpublic/userrecord";
	}
	/**
	 * 作废明细
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/voiddetail")
	public String voiddetail(HttpServletRequest request, Model model,Integer id) {
		WechatCardRecord wechatCardRecord=wechatCardRecordService.selectById(id);
		model.addAttribute("wechatCardRecord", wechatCardRecord);
		return "wechatpublic/voiddetail";
	}
    
    @RequestMapping(value="/chooseCardset/{custId}/{orderId}")
	 public String chooseCardset(HttpServletRequest request,Model model,@PathVariable("custId") Integer custId,@PathVariable("orderId") Integer orderId) {
		 List<WechatCardRecord> wechatCardRecords  = wechatCardRecordService.selectByCustId(custId);
		 model.addAttribute("wechatCardRecords", wechatCardRecords);
		 model.addAttribute("customId", custId);
		 model.addAttribute("orderId", orderId);
		 model.addAttribute("payId", BasicContant.payTypeContant.CARDSET);
		 model.addAttribute("shopId", getShopId(request));
		 return "orders/chooseCardset";
	 }
	 
	 
	 /**
	  * 提交选择的优惠券
	 * @throws Exception 
	  */
	 @RequestMapping(value="/submitCardset")
	 @ResponseBody
	 public String submitCardset(HttpServletRequest request,String cardsetIds, Integer customId,Integer orderId) throws Exception {
		return  AppUtils.getReturnInfo(wechatCardRecordService.submitCardset(cardsetIds, customId, orderId));
	 }

}
