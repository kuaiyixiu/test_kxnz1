package com.kyx.biz.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductChange implements Serializable {
    private Integer id;
    @JSONField(format="yyyy-MM-dd hh:mm:ss")
    private Date changeTime;

    private Integer productId;

    private String changeReason;

    private Integer type; //异动类型 1采购入库 2采购退货3订单消耗4进货单作废5退货单作废6库存调入7库存调出8库存调出作废9订单反入账/挂账

    private Integer count;

    private Integer beforeCount;

    private Integer afterCount;

    private Integer relationId;

    private String optUser;

    private Integer changeReasonId;
    
    private BigDecimal changeAmt;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason == null ? null : changeReason.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBeforeCount() {
        return beforeCount;
    }

    public void setBeforeCount(Integer beforeCount) {
        this.beforeCount = beforeCount;
    }

    public Integer getAfterCount() {
        return afterCount;
    }

    public void setAfterCount(Integer afterCount) {
        this.afterCount = afterCount;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser == null ? null : optUser.trim();
    }

    public Integer getChangeReasonId() {
        return changeReasonId;
    }

    public void setChangeReasonId(Integer changeReasonId) {
        this.changeReasonId = changeReasonId;
    }

	public BigDecimal getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
	}
    
    
}