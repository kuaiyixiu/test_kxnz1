package com.kyx.biz.wechat.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.shops.service.ShopsService;
import com.kyx.basic.util.AppUtils;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.appoint.model.Appoint;
import com.kyx.biz.appoint.service.AppointService;
import com.kyx.biz.base.controller.BaseController;
import com.kyx.biz.custom.model.Custom;

/**
 * 订单管理
 * 
 * @author 童亦刚
 * @Descripton
 */
@Controller
@RequestMapping(value = "/wechat/appoint")
public class WechatAppointController extends BaseController {

	
	@Resource
	private AppointService appointService;
	@Resource
	private ShopsService shopsService;
	

	/**
	 * 预约服务
	 * @param model
	 * @param session
	 * @param state
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/serviceappointment")
	public String serviceAppointment(Model model,HttpServletRequest request) throws Exception {
		Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
		List<Map> mapList=new ArrayList<Map>();
		if(custom!=null){//查询当前客户所在的门店和连锁店
			String dbName=Dbs.getDbName();//当前门店所在数据库
			List<Shops> list=shopsService.getShops().get(dbName);
			for(Shops shops:list){
				Map map=new HashMap();
				map.put("value", shops.getId());
				map.put("text", shops.getShopName());
				mapList.add(map);
				if(custom.getShopId().intValue()==shops.getId().intValue()){
					model.addAttribute("currentShopId", shops.getId());
					model.addAttribute("currentShopName", shops.getShopName());
				}
			}
		}
		model.addAttribute("shopList", JSONArray.toJSONString(mapList));
		return "appoint/serviceappointment";
	}
	
	
	@RequestMapping(value = "/addAppoint")
	@ResponseBody
	public String addAppoint(HttpServletRequest request,Appoint appoint) {
		//Dbs.setDbName("kxnz001");
//		appoint.setShopId(2);
//		appoint.setCustId(1);
	//	appoint.setShopId(getShopId(request));
//		appoint.setCustId(getCustomId(request));
		Custom custom = (Custom) request.getSession().getAttribute(BasicContant.CUSTOMER_SESSION);
		appoint.setShopId(custom.getShopId());
		appoint.setCustId(custom.getId());
		
		RetInfo retInfo =  appointService.saveAppoint(appoint);
		return AppUtils.getReturnInfo(retInfo);
	}
	
	@RequestMapping("/appointlist")
	public String appointlist(Model model,HttpServletRequest request) throws Exception {
		return "appoint/appointlist";
	}
	
	@RequestMapping(value = "/getList", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getList(HttpServletRequest request, Pager pager,Appoint appoint) {
		//Dbs.setDbName("kxnz001");
		//appoint.setCustId(1);
		appoint.setCustId(getCustomId(request));
		GrdData result = appointService.getList(appoint, pager);
		return JSON.toJSONString(result);
	}

}
