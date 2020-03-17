package com.kyx.biz.orders.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class OrdersRoyalty implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8132089864665002064L;

	private Integer id;

    private Integer kind;
    
    private String inKind;

    private Integer ordersDtId;
    
    private Integer itemId;
    
    private String itemName;
    
    private String productType;

    private BigDecimal amount;
    
    private Integer quantity;

    private Integer shopId;

    private Integer orderId;
    
    private Integer userId;
    
    private BigDecimal kind1Amt;
    
    private BigDecimal kind2Amt;
    
    private BigDecimal kind3Amt;
    
    private Integer kind1Qty;
    
    private Integer kind2Qty;
    
    private Integer kind3Qty;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;
	
	private Date finishTime;
	
	

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getOrdersDtId() {
        return ordersDtId;
    }

    public void setOrdersDtId(Integer ordersDtId) {
        this.ordersDtId = ordersDtId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

	public String getInKind() {
		return inKind;
	}

	public void setInKind(String inKind) {
		this.inKind = inKind;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getKind1Amt() {
		return kind1Amt;
	}

	public void setKind1Amt(BigDecimal kind1Amt) {
		this.kind1Amt = kind1Amt;
	}

	public BigDecimal getKind2Amt() {
		return kind2Amt;
	}

	public void setKind2Amt(BigDecimal kind2Amt) {
		this.kind2Amt = kind2Amt;
	}

	public BigDecimal getKind3Amt() {
		return kind3Amt;
	}

	public void setKind3Amt(BigDecimal kind3Amt) {
		this.kind3Amt = kind3Amt;
	}

	public Integer getKind1Qty() {
		return kind1Qty;
	}

	public void setKind1Qty(Integer kind1Qty) {
		this.kind1Qty = kind1Qty;
	}

	public Integer getKind2Qty() {
		return kind2Qty;
	}

	public void setKind2Qty(Integer kind2Qty) {
		this.kind2Qty = kind2Qty;
	}

	public Integer getKind3Qty() {
		return kind3Qty;
	}

	public void setKind3Qty(Integer kind3Qty) {
		this.kind3Qty = kind3Qty;
	}

	public Date getDateRangeStartTime() {
		return dateRangeStartTime;
	}

	public void setDateRangeStartTime(Date dateRangeStartTime) {
		this.dateRangeStartTime = dateRangeStartTime;
	}

	public Date getDateRangeEndTime() {
		return dateRangeEndTime;
	}

	public void setDateRangeEndTime(Date dateRangeEndTime) {
		this.dateRangeEndTime = dateRangeEndTime;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
}