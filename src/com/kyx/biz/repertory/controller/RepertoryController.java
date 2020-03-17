package com.kyx.biz.repertory.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.annotation.SystemControllerLog;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.sysparam.service.SysParamService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.paytype.model.PayType;
import com.kyx.biz.paytype.service.PayTypeService;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.service.ProductRepertoryService;
import com.kyx.biz.product.service.ProductService;
import com.kyx.biz.repertory.model.Repertory;
import com.kyx.biz.repertory.model.RepertoryDt;
import com.kyx.biz.repertory.model.RepertoryPay;
import com.kyx.biz.repertory.service.RepertoryDtService;
import com.kyx.biz.repertory.service.RepertoryPayService;
import com.kyx.biz.repertory.service.RepertoryService;
import com.kyx.biz.supply.model.Supply;
import com.kyx.biz.supply.service.SupplyService;
/**
 * 
 * @author 严大灯
 * @data 2019-4-19 下午3:46:11
 * @Descripton
 */
@Controller
@RequestMapping(value = "/repertory")
public class RepertoryController extends BaseController{
	@Resource
	private RepertoryService repertoryService;
	@Resource
	private SupplyService supplyService;
	@Resource
	private RepertoryDtService repertoryDtService;
	@Resource
	private RepertoryPayService repertoryPayService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductRepertoryService productRepertoryService;
	@Resource
	private PayTypeService payTypeService;
	@Resource
	private SysParamService sysParamService;
    
	@RequestMapping ("/infolist/{kind}")
	public String infoList(Model model,@PathVariable("kind") String kind){
		model.addAttribute("kind", kind);
		return "stock/repertorylist";
	}
	
    @RequestMapping(value="/getList/{kind}")
    @ResponseBody
    public String getList(Repertory repertory,Pager pager,HttpSession session,@PathVariable("kind") Integer kind) {
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	repertory.setShopId(shops.getId());
    	repertory.setKind(kind);
    	GrdData result=repertoryService.getInfo(repertory,pager);
        return JSON.toJSONString(result);
    }
    
    /**
     * 新建单据
     * @param model
     * @param session
     * @param kind
     * @return
     */
    @RequestMapping("/addData/{kind}")
    public String addData(Model model,HttpSession session,@PathVariable("kind") String kind){
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	Supply supply=new Supply();
    	supply.setShopId(shops.getId());
    	supply.setEnabled(1);
    	List<Supply> list=supplyService.getInfo(supply);
    	model.addAttribute("shopId", shops.getId());
    	model.addAttribute("supplyList", list);
    	model.addAttribute("kind", kind);
    	return "stock/addrepertory";
    }
    //修改单据
    @RequestMapping("/editData/{id}")
    public String editData(Model model,HttpServletRequest request,@PathVariable("id") Integer id,String kind){
    	Supply supply=new Supply();
    	supply.setShopId( getShopId(request));
    	supply.setEnabled(1);
    	List<Supply> list=supplyService.getInfo(supply);
    	model.addAttribute("shopId",  getShopId(request));
    	model.addAttribute("supplyList", list);
    	Repertory repertory=repertoryService.getById(id);
    	model.addAttribute("repertory", repertory);
    	model.addAttribute("kind", kind);
    	
    	String repository_oa = sysParamService.getValueByName(SysParamService.REPOSITORY_OA,  getShopId(request)); //入库是否需要审批
    	model.addAttribute("repository_oa", repository_oa);
    	
    	return "stock/editrepertory";
    }
    
    /**
     * 保存数据
     * @param role
     * @return
     */
    @RequestMapping("/saveData")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "新增入库/退货单据") 
    public String saveData(Repertory repertory,HttpSession httpSession) {
    	RetInfo retInfo=repertoryService.saveData(repertory,httpSession);
        return AppUtils.getReturnInfo(retInfo);
    }
    /**
     * 采购产品信息
     * @param repertoryDt
     * @param httpSession
     * @return
     */
    @RequestMapping("/saveDtData")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "新增入库/退货产品明细") 
    public String saveDtData(RepertoryDt repertoryDt,HttpSession httpSession) {
    	RetInfo retInfo=repertoryDtService.saveData(repertoryDt);
        return AppUtils.getReturnInfo(retInfo);
    }
    /**
     * 删除采购信息
     * @param id
     * @return
     */
    @RequestMapping("/delcgData")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "删除入库/退货产品明细") 
    public String delcgData(Integer ids) {
    	RetInfo retInfo=repertoryDtService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
    /**
     * 采购入库数据
     * @param model
     * @param pager
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/getCGList/{id}")
    @ResponseBody
    public String getCGList(Model model,Pager pager,HttpSession session,@PathVariable("id") String id){
    	RepertoryDt dt=new RepertoryDt();
    	dt.setRepertory(Integer.valueOf(id));
    	GrdData result=repertoryDtService.getInfo(dt,pager);
        return JSON.toJSONString(result);
    }
    
    /**
     * 采购入库历史数据
     * @param model
     * @param pager
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/getCGHistoryList/{id}")
    @ResponseBody
    public String getCGHistoryList(Model model,Pager pager,HttpSession session,@PathVariable("id") String id){
    	RepertoryDt dt=new RepertoryDt();
    	dt.setRepertory(Integer.valueOf(id));
    	GrdData result=repertoryDtService.getRepertoryInfo(dt);
        return JSON.toJSONString(result);
    }
    
    /**
     * 退货历史数据
     * @param model
     * @param pager
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/getTHHistoryList/{id}")
    @ResponseBody
    public String getTHHistoryList(Model model,Pager pager,HttpSession session,@PathVariable("id") String id){
    	RepertoryDt dt=new RepertoryDt();
    	dt.setRepertory(Integer.valueOf(id));
    	GrdData result=repertoryDtService.getInfo(dt,pager);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/getPayList/{id}")
    @ResponseBody
    public String getPayList(Model model,Pager pager,HttpSession session,@PathVariable("id") String id){
    	RepertoryPay dt=new RepertoryPay();
    	dt.setRepertoryId(Integer.valueOf(id));
    	GrdData result=repertoryPayService.getInfo(dt,pager);
        return JSON.toJSONString(result);
    }
    
    /**
     * 采购列表
     * @param model
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/addcginfo/{id}")
    public String addcginfo(Model model,HttpSession session,@PathVariable("id") String id,String kind){
    	Product product=new Product();
    	Shops shops=(Shops) session.getAttribute("shopsSession");
    	product.setShopId(shops.getId());
    	List<Product> list=productService.getProductList(product);
    	model.addAttribute("proList", list);
    	model.addAttribute("repertoryId", id);
    	model.addAttribute("kind", kind);
    	return "stock/addcginfo";
    }
    /**
     * 支付列表
     * @param model
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/addpayinfo/{id}")
    public String addpayinfo(Model model,HttpSession session,@PathVariable("id") String id,HttpServletRequest request){
    	model.addAttribute("repertoryId", id);
    	List<PayType> payTypes = payTypeService.getPayType(PayType.SHOPTYPE);
    			//.getByShopId(getShopId(request));
    	model.addAttribute("payTypes", payTypes);
    	return "stock/addpayinfo";
    }
    
    @RequestMapping("/savePayData")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "新增入库/退货支付明细")
    public String savePayData(RepertoryPay repertoryPay,HttpSession httpSession) {
    	Shops shops=(Shops) httpSession.getAttribute("shopsSession");
    	repertoryPay.setShopId(shops.getId());
    	repertoryPay.setCanEdit(1);
    	RetInfo retInfo=repertoryPayService.saveData(repertoryPay);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    @RequestMapping("/delPayData")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "删除入库/退货支付明细")
    public String delPayData(Integer ids) {
    	RetInfo retInfo=repertoryPayService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
	
    /**
     * 入库
     * @param repertoryPay
     * @param httpSession
     * @return
     */
    @RequestMapping("/saveRepertoryin")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "采购入库/采购挂账/退货")
    public String saveRepertoryin(Repertory repertory,HttpServletRequest request,HttpSession httpSession) {
    	String repository_oa = sysParamService.getValueByName(SysParamService.REPOSITORY_OA,  getShopId(request)); //入库是否需要审批
    	repertory.setOptUserName(AppUtils.getOptUserName(httpSession));
    	Repertory rty = repertoryService.getById(repertory.getId());
    	if(rty.getRepertoryStatus() == 0){
    		if("1".equals(repository_oa)){ //需要审核
    			RetInfo retInfo= repertoryService.applyRepertoryin(request, repertory);
    			return AppUtils.getReturnInfo(retInfo);
    		}else{
    			RetInfo retInfo=repertoryService.saveRepertoryin(repertory);
    			return AppUtils.getReturnInfo(retInfo);
    		}
    	}else if(rty.getRepertoryStatus() == 5){
    		RetInfo retInfo=repertoryService.saveRepertoryin(repertory);
			return AppUtils.getReturnInfo(retInfo);
    	}
    	return AppUtils.getReturnInfo(new RetInfo("error","操作失败！"));
    	
    }
    /**
     * 删除入库单
     * @param ids
     * @return
     */
    @RequestMapping("/delData")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "删除库存单据")
    public String delData(String ids) {
    	RetInfo retInfo=repertoryService.delData(ids);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    /**
     * 入库历史记录
     * @return
     */
	@RequestMapping ("/historylist/{kind}")
	public String historylist(Model model,@PathVariable("kind") String kind){
		model.addAttribute("kind", kind);
		return "stock/repertoryinhistroylist";
	}
	
	/**
	 * 退货单明细
	 * @param model
	 * @param session
	 * @param id
	 * @param kind
	 * @return
	 */
    @RequestMapping("/editHistoryData/{id}")
    public String editHistoryData(Model model,HttpSession session,@PathVariable("id") Integer id,String kind){
    	Repertory repertory=repertoryService.getById(id);
    	model.addAttribute("repertory", repertory);
    	model.addAttribute("kind", kind);
    	return "stock/repertorydetail";
    }
    /**
     * 挂账改价
     * @param model
     * @param session
     * @param id
     * @return
     */
    @RequestMapping("/edtcgPrice/{id}")
    public String edtcgPrice(Model model,HttpSession session,@PathVariable("id") Integer id){
    	RepertoryDt repertoryDt=repertoryDtService.getById(id);
    	model.addAttribute("repertoryDt", repertoryDt);
    	return "stock/repertorydtdetail";
    }
    
    /**
     * 单据作废 入库单和退换单作废
     * @param repertory
     * @param httpSession
     * @return
     */
    @RequestMapping("/saveDestoryData")
    @ResponseBody
    @SystemControllerLog(module = "库存管理", description = "库存单据作废")
    public String saveDestoryData(Repertory repertory,HttpSession httpSession) {
    	RetInfo retInfo=repertoryService.saveDestoryData(repertory,httpSession);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    
    @RequestMapping("/chkDtData")
    @ResponseBody
    public String chkDtData(Integer id,Integer count) {
    	RetInfo retInfo=productRepertoryService.chkDtData(id,count);
        return AppUtils.getReturnInfo(retInfo);
    }

}
