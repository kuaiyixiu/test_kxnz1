package com.kyx.biz.wechatpublic.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.custom.service.CustomService;
import com.kyx.biz.customType.model.CustomType;
import com.kyx.biz.customType.service.CustomTypeService;
import com.kyx.biz.wechatpublic.model.CardSet;
import com.kyx.biz.wechatpublic.model.WechatCardRecord;
import com.kyx.biz.wechatpublic.service.CardSetService;
import com.kyx.biz.wechatpublic.service.WechatCardRecordService;
/**
 * 
 * @author 严大灯
 * @data 2019-7-31 上午11:11:35
 * @Descripton
 */
@Controller
@RequestMapping("cardset")
public class CardSetController extends BaseController{
	@Resource
	private CardSetService cardSetService; 
	@Resource
	private CustomTypeService customTypeService;
	@Resource
	private CustomService customService;
	@Resource
	private WechatCardRecordService wechatCardRecordService;
	
	/*
	 * 卡卷设置列表
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model) {

		return "wechatpublic/cardsetlist";
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
    public String getList(CardSet cardSet,Pager pager,HttpSession session) {
    	GrdData result=cardSetService.getInfo(cardSet,pager);
        return JSON.toJSONString(result);
    }
    /**
     * 新增修改卡券
     * @param request
     * @param model
     * @param id
     * @return
     */
	@RequestMapping("/addCardSet")
	public String addCardSet(HttpServletRequest request, Model model,Integer id) {
		CardSet cardSet=new CardSet();
		cardSet.setCardValue(new BigDecimal(0));
		cardSet.setEndDate(AppUtils.MonthAdd(new Date(), 3));
		if(id!=null)
			cardSet=cardSetService.getById(id);
		model.addAttribute("cardSet", cardSet);
		//model.addAttribute("endDate", AppUtils.getDayByMonth(3));
		return "wechatpublic/addcardset";
	}
	/**
	 * 保存卡卷
	 * @param cardSet
	 * @param httpSession
	 * @return
	 */
    @RequestMapping("/saveData")
    @ResponseBody
    @SystemControllerLog(module = "微信", description = "新增卡卷设置") 
    public String saveData(CardSet cardSet,HttpSession httpSession) {
    	RetInfo retInfo=cardSetService.saveData(cardSet,httpSession);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 卡卷分发
     * @param request
     * @param model
     * @return
     */
	@RequestMapping("/sendcard")
	public String sendcard(HttpServletRequest request, Model model,Integer id) {
        model.addAttribute("shopName", getShopName(request));
        List<CustomType> customTypes=customTypeService.selectCustomTypes(null);
        model.addAttribute("customTypes",JSON.toJSONString(customTypes));
        model.addAttribute("customTypeList", customTypes);
        model.addAttribute("cardSet", cardSetService.selectByKey(id));
        model.addAttribute("now", new Date());
		return "wechatpublic/sendcard";
	}
	/**
	 * 查询所有会员列表
	 * @param custom
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/queryCustoms")
	@ResponseBody
	public String queryCustoms(Custom custom, Pager pager) {
		GrdData result = customService.queryCustoms(custom, pager);
		return JSON.toJSONString(result);
	}
	
	
    @RequestMapping("/sendCardInfo")
    @ResponseBody
    @SystemControllerLog(module = "微信", description = "卡卷推送") 
    public String sendCardInfo(Custom custom,HttpServletRequest request) {
    	WxMpService wxMpService=getWxMapService(request);
    	RetInfo retInfo=cardSetService.sendCardInfo(wxMpService,custom);
        return AppUtils.getReturnInfo(retInfo);
    }
    
	
}
