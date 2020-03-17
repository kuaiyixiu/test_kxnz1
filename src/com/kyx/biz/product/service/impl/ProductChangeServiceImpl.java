package com.kyx.biz.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.product.mapper.ProductChangeMapper;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.product.model.ProductChange;
import com.kyx.biz.product.service.ProductChangeService;
@Service("productChangeService")
public class ProductChangeServiceImpl implements ProductChangeService {
	@Resource
	private ProductChangeMapper productChangeMapper;

	@Override
	public GrdData getInfo(ProductChange productChange, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Product> list=productChangeMapper.getInfo(productChange);
		return new GrdData(page.getTotal(),list);
	}

}
