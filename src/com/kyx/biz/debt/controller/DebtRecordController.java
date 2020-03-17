package com.kyx.biz.debt.controller;

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
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.debt.model.DebtRecord;
import com.kyx.biz.debt.service.DebtRecordService;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;
import com.kyx.biz.repertory.model.Repertory;
import com.kyx.biz.repertory.service.RepertoryService;
/**
 * 
 * @author 严大灯
 * @data 2019-4-26 下午4:40:24
 * @Descripton
 */
@Controller
@RequestMapping(value = "/debtRecord")
public class DebtRecordController extends BaseController{
	@Resource
	private DebtRecordService debtRecordService;
	@Resource
	private RepertoryService repertoryService;
	@Resource
	private PayTypeService payTypeService;
    
	@RequestMapping ("/infolist/{kind}")
	public String infoList(Model model,@PathVariable("kind") String kind){
		model.addAttribute("kind", kind);
		return "stock/repertoryindebtist";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(DebtRecord debtRecord,Pager pager,HttpSession session) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	debtRecord.setShopId(shops.getId());
    	GrdData result=debtRecordService.getInfo(debtRecord,pager);
        return JSON.toJSONString(result);
    }
    
    /**
     * 挂账编辑
     * @param model
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/editDebtRecordData/{id}")
    public String editData(Model model,HttpSession session,@PathVariable("id") Integer id){
    	DebtRecord debtRecord=debtRecordService.getById(id);
    	model.addAttribute("debtRecord", debtRecord);
    	Repertory repertory=repertoryService.getById(debtRecord.getSourceId());
    	model.addAttribute("repertory", repertory);
    	return "stock/repertorydebtdetail";
    }
    
    /**
     * 批量还款跳转
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/batchReturnDebt")
    public String batchReturnDebt(Model model,HttpSession session){
    	List<PayType> payTypes = payTypeService.getPayType(PayType.SHOPTYPE);
		//.getByShopId(getShopId(request));
        model.addAttribute("payTypes", payTypes);  
    	return "stock/batchreturndebt";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveReturnDebt")
    @ResponseBody
    public String saveReturnDebt(DebtRecord debtRecord) {
    	RetInfo retInfo=debtRecordService.saveReturnDebt(debtRecord);
        return AppUtils.getReturnInfo(retInfo);
    }
    /**
     * 批量还款
     * @param payId
     * @param ids
     * @return
     */
    @RequestMapping("/saveReturnDebtInfo")
    @ResponseBody
    public String saveReturnDebtInfo(Integer payId,String ids,HttpSession session,HttpServletRequest request) {
    	RetInfo retInfo=debtRecordService.saveReturnDebt(payId,ids,getShopId(request));
        return AppUtils.getReturnInfo(retInfo);
    }
	 

}
