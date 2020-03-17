package com.kyx.biz.repertorycheck.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.repertorycheck.model.RepertoryCheck;
import com.kyx.biz.repertorycheck.service.RepertoryCheckService;


@Controller
@RequestMapping(value = "/repertoryCheck")
public class RepertoryCheckController {
	@Resource
	private RepertoryCheckService repertoryCheckService;
	@RequestMapping ("/infolist")
	public String infoList(){
		return "stock/repertorycheck";
	}
	
    @RequestMapping(value="/getList",produces="text/plain; charset=utf-8")
    @ResponseBody
    public String getList(RepertoryCheck repertoryCheck,Pager pager,HttpSession session) {
    	Shops shops = (Shops) session.getAttribute("shopsSession");
    	repertoryCheck.setShopId(shops.getId());
    	GrdData result=repertoryCheckService.getInfo(repertoryCheck,pager);
        return JSON.toJSONString(result);
    }

    
    

}
