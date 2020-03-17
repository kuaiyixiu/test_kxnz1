package com.kyx.biz.royalty.controller;

import java.math.BigDecimal;
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
import com.alibaba.fastjson.JSONObject;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.orders.model.OrdersRoyalty;
import com.kyx.biz.orders.service.OrdersRoyaltyService;
import com.kyx.biz.royalty.model.Royalty;
import com.kyx.biz.royalty.service.RoyaltyService;

/**
 * 施工提成
 * @author 严大灯
 * @data 2019-4-17 上午11:23:08
 * @Descripton
 */
@Controller
@RequestMapping(value = "/royalty")
public class RoyaltyController extends BaseController {
	@Resource
	private RoyaltyService royaltyService;
	
	@Resource
	private UserService userService;
	
	
	@Resource
	private OrdersRoyaltyService ordersRoyaltyService;
	
	
	@RequestMapping ("/infolist/{kind}")
	public String infoList(@PathVariable("kind") String kind,Model model){
		model.addAttribute("kind", kind);
		return "config/royaltylist";
	}
	
    @RequestMapping(value="/getList/{kind}",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(Royalty royalty,Pager pager,HttpServletRequest request,@PathVariable("kind") String kind) {
    	royalty.setShopId(getShopId(request));
    	royalty.setKind(kind);
    	GrdData result=royaltyService.getInfo(royalty,pager);
        return JSON.toJSONString(result);
    }
    @RequestMapping("/editData/{kind}")
    public String editData(Model model,Royalty ro,HttpServletRequest request,@PathVariable("kind") String kind){
    	ro.setKind(kind);
    	model.addAttribute("kind", kind);
    	ro.setShopId(getShopId(request));
    	if(ro.getRoyaltyId()!=null){
    		Royalty royalty=royaltyService.getByRoyaltyId(ro);
    		model.addAttribute("royalty", royalty);
    	}else{
    		Royalty royalty=new Royalty();
    		royalty.setRoyaltyCount(new BigDecimal(0.00));
    		model.addAttribute("royalty", royalty);
    	}
    	return "config/addroyalty";
    }
    
/**
 * 保存数据
 * @param royalty
 * @param ids
 * @param session
 * @param statue "add"新增  "modify" 修改
 * @return
 */
    @RequestMapping("/saveData")
    @ResponseBody
    public String saveData(Royalty royalty,String ids,HttpSession session,String statue) {
    	RetInfo retInfo=royaltyService.saveData(royalty,ids,session,statue);
        return AppUtils.getReturnInfo(retInfo);
    }
    
    @RequestMapping("/getByRoyaltyId")
    @ResponseBody
    public String getByRoyaltyId(Royalty queryVo,HttpServletRequest request) {
    	queryVo.setShopId(getShopId(request));
    	Royalty royalty =  royaltyService.getByRoyaltyId(queryVo);
    	return JSONObject.toJSON(royalty).toString();
    }
    
    @RequestMapping("/getByRoyaltyListId")
    @ResponseBody
    public String getByRoyaltyListId(Royalty queryVo,HttpSession session) {
    	List<Royalty> list = royaltyService.getList(queryVo);
    	return JSONObject.toJSON(list).toString();
    }
    
    
    /**
     * 导向员工提成概览页面
     * @param model
     * @param request
     * @return
     */
	@RequestMapping("/userRoyalList")
	public String userRoyalList(Model model,HttpServletRequest request) {
		Dbs.setDbName(Dbs.getMainDbName());
		Map<Integer, String> userMap  = userService.getUserMap(getShopId(request));
		model.addAttribute("userMap", JSON.toJSONString(userMap));
		return "payrecord/userRoyalList";
	}
	
	/**
	 * 查询员工提成概览
	 * @param request
	 * @param ordersRoyalty
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getUserRoyalList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getUserRoyalList(HttpServletRequest request, OrdersRoyalty ordersRoyalty, Pager pager) {
		GrdData result  =  ordersRoyaltyService.getUserRoyalList(ordersRoyalty,pager);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 导向查询指定员工的提成页面
	 * @param model
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/userRoyalDetail/{userId}")
	public String userRoyalDetail(Model model,HttpServletRequest request,@PathVariable("userId") Integer userId,OrdersRoyalty ordersRoyalty) {
		Dbs.setDbName(Dbs.getMainDbName());
		User user = userService.selectByPrimaryKey(userId);
		model.addAttribute("user", user);
		if (ordersRoyalty.getDateRangeStartTime()!=null && ordersRoyalty.getDateRangeEndTime()!=null){
			model.addAttribute("dateRangeStartTimeStr", AppUtils.date2String1(ordersRoyalty.getDateRangeStartTime()));
			model.addAttribute("dateRangeEndTimeStr",AppUtils.date2String1(ordersRoyalty.getDateRangeEndTime()));
		}
		return "payrecord/userRoyalDetail";
	}
	
	/**
	 * 查询指定员工的提成
	 * @param request
	 * @param ordersRoyalty
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getUserRoyalDetail", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getUserRoyalDetail(HttpServletRequest request, OrdersRoyalty ordersRoyalty, Pager pager) {
		ordersRoyalty.setShopId(getShopId(request));
		GrdData result  = null;
		if (ordersRoyalty.getKind() == 1 ||  ordersRoyalty.getKind() == 2){
			 result  =  ordersRoyaltyService.queryUserServeRoy(ordersRoyalty,pager);
		}else if (ordersRoyalty.getKind() == 3){
			 result  =  ordersRoyaltyService.queryUserProductRoy(ordersRoyalty,pager);
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 导向提成列表
	 * @param model
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping("/orderRoyalList")
	public String royalDetail(Model model,HttpServletRequest request,OrdersRoyalty ordersRoyalty) {
		model.addAttribute("ordersRoyalty", ordersRoyalty);
		if (ordersRoyalty.getDateRangeStartTime()!=null && ordersRoyalty.getDateRangeEndTime()!=null){
			model.addAttribute("dateRangeStartTimeStr", AppUtils.date2String1(ordersRoyalty.getDateRangeStartTime()));
			model.addAttribute("dateRangeEndTimeStr",AppUtils.date2String1(ordersRoyalty.getDateRangeEndTime()));
		}
		return "payrecord/orderRoyalList";
	}
	
	/**
	 * 查询提成列表 根据订单状态
	 * @param request
	 * @param ordersRoyalty
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/getOrderRoyalList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getOrderRoyalList(HttpServletRequest request, OrdersRoyalty ordersRoyalty, Pager pager) {
		ordersRoyalty.setShopId(getShopId(request));
		GrdData result  = null;
		if (ordersRoyalty.getKind() == 1 ||  ordersRoyalty.getKind() == 2){
			 result  =  ordersRoyaltyService.queryServeRoyList(ordersRoyalty,pager);
		}else if (ordersRoyalty.getKind() == 3){
			 result  =  ordersRoyaltyService.queryProductRoyList(ordersRoyalty,pager);
		}
		return JSON.toJSONString(result);
	}
	
}
