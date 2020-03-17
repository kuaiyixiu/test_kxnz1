package com.kyx.biz.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Product implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7111781064510750646L;

	private Integer id;

    private String productName;

    private String productType;

    private Integer classId;

    private BigDecimal price;
    
    private BigDecimal custPrice; //客户会员价格
    
    private Integer isCust;//是否会员价格

    private String remark;

    private String carType;

    private Integer alarmQuantity;

    private String qrCode;
    
    private Integer shopId;
    private Integer quantity;
    private BigDecimal amount;//进价
    
    private String className;
    
    private Integer addQuantity; //使用数量 异动产品信息时用到 sql语句用+=
    
    private Integer subQuantity;//使用数量 异动产品信息时用到 sql语句用-=

    private Integer custType;
    
    private Integer custId;
    
    private Integer del;
    
    private Integer queryType;
    
    private Integer outQty;//出库数量
    private BigDecimal outAmt;//出库总价
    private Integer inQty;//入库数量
    private BigDecimal inAmt;//入库总价
    private String keyword;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeStartTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateRangeEndTime;
    

    public Integer getOutQty() {
		return outQty;
	}

	public void setOutQty(Integer outQty) {
		this.outQty = outQty;
	}

	public BigDecimal getOutAmt() {
		return outAmt;
	}

	public void setOutAmt(BigDecimal outAmt) {
		this.outAmt = outAmt;
	}

	public Integer getInQty() {
		return inQty;
	}

	public void setInQty(Integer inQty) {
		this.inQty = inQty;
	}

	public BigDecimal getInAmt() {
		return inAmt;
	}

	public void setInAmt(BigDecimal inAmt) {
		this.inAmt = inAmt;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }

    public Integer getAlarmQuantity() {
        return alarmQuantity;
    }

    public void setAlarmQuantity(Integer alarmQuantity) {
        this.alarmQuantity = alarmQuantity;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode == null ? null : qrCode.trim();
    }

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getAddQuantity() {
		return addQuantity;
	}

	public void setAddQuantity(Integer addQuantity) {
		this.addQuantity = addQuantity;
	}

	public Integer getSubQuantity() {
		return subQuantity;
	}

	public void setSubQuantity(Integer subQuantity) {
		this.subQuantity = subQuantity;
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public BigDecimal getCustPrice() {
		return custPrice;
	}

	public void setCustPrice(BigDecimal custPrice) {
		this.custPrice = custPrice;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getIsCust() {
		return isCust;
	}

	public void setIsCust(Integer isCust) {
		this.isCust = isCust;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	
}