package com.kyx.biz.orders.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.orders.mapper.OrdersRoyaltyMapper;
import com.kyx.biz.orders.model.OrdersRoyalty;
import com.kyx.biz.orders.service.OrdersRoyaltyService;

@Service("ordersRoyaltyService")
public class OrdersRoyaltyServiceImpl implements OrdersRoyaltyService {
	
	@Resource
	private OrdersRoyaltyMapper ordersRoyaltyMapper;
	
	@Resource
	private UserService userService;
	
	@Override
	public GrdData getRoyalList(OrdersRoyalty ordersRoyalty, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<OrdersRoyalty> list = ordersRoyaltyMapper.getRoyalList(ordersRoyalty);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public GrdData getUserRoyalList(OrdersRoyalty ordersRoyalty, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<OrdersRoyalty> list = ordersRoyaltyMapper.getUserRoyalList(ordersRoyalty);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public GrdData queryUserServeRoy(OrdersRoyalty ordersRoyalty, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<OrdersRoyalty> list  = ordersRoyaltyMapper.queryUserServeRoy(ordersRoyalty);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public GrdData queryUserProductRoy(OrdersRoyalty ordersRoyalty, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<OrdersRoyalty> list  =  ordersRoyaltyMapper.queryUserProductRoy(ordersRoyalty);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public GrdData queryServeRoyList(OrdersRoyalty ordersRoyalty, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<OrdersRoyalty> list  =  ordersRoyaltyMapper.queryServeRoyList(ordersRoyalty);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public GrdData queryProductRoyList(OrdersRoyalty ordersRoyalty, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<OrdersRoyalty> list  =  ordersRoyaltyMapper.queryProductRoyList(ordersRoyalty);
		return new GrdData(page.getTotal(),list);
	}

	
}
