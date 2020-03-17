package com.kyx.biz.product.service;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.biz.product.model.ProductChange;

public interface ProductChangeService {
    
	GrdData getInfo(ProductChange productChange, Pager pager);

}
