package com.kyx.biz.product.model;

import java.io.Serializable;

public class ProductClass implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7685484854816382304L;

	private Integer id;

    private String className;

    private String remark;
    
    private Integer shopId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
    
}