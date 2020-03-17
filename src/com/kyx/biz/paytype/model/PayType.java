package com.kyx.biz.paytype.model;

import java.io.Serializable;

public class PayType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3449103793948266926L;

	private Integer id;

    private String payName;

    private String remark;

    private Integer shopId;
    
    private Integer shopChecked;//门店启用
    private Integer custChecked;//客户启用
    
    public static Integer CUSTOMTYPE=1;//客户支付方式
    public static Integer SHOPTYPE=2;//店铺支付

    private Integer isSystem;//是否系统默认（不可删除） 1是 0否

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
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

	public Integer getShopChecked() {
		return shopChecked;
	}

	public void setShopChecked(Integer shopChecked) {
		this.shopChecked = shopChecked;
	}

	public Integer getCustChecked() {
		return custChecked;
	}

	public void setCustChecked(Integer custChecked) {
		this.custChecked = custChecked;
	}

	public Integer getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	
}