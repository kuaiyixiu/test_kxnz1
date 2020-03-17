package com.kyx.biz.serve.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Serve implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4433929717457064537L;

	private Integer id;

    private String serveName;

    private Integer classId;

    private BigDecimal price;
    
    private BigDecimal custPrice; //客户会员价格
    
    private Integer isCust;//是否会员价格

    private String remark;
    
    private String className;
    
    private BigDecimal sgtc;
    private Integer sz;
    private BigDecimal zq;
    private String construction;
    private String completion;
    
    private Integer userId;

    private Integer shopId;
    
    private Integer custType;
    
    private Integer custId;
    private Integer del;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServeName() {
        return serveName;
    }

    public void setServeName(String serveName) {
        this.serveName = serveName == null ? null : serveName.trim();
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public BigDecimal getSgtc() {
		return sgtc;
	}

	public void setSgtc(BigDecimal sgtc) {
		this.sgtc = sgtc;
	}

	public Integer getSz() {
		return sz;
	}

	public void setSz(Integer sz) {
		this.sz = sz;
	}

	public BigDecimal getZq() {
		return zq;
	}

	public void setZq(BigDecimal zq) {
		this.zq = zq;
	}

	public String getConstruction() {
		return construction;
	}

	public void setConstruction(String construction) {
		this.construction = construction;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public BigDecimal getCustPrice() {
		return custPrice;
	}

	public void setCustPrice(BigDecimal custPrice) {
		this.custPrice = custPrice;
	}

	public Integer getIsCust() {
		return isCust;
	}

	public void setIsCust(Integer isCust) {
		this.isCust = isCust;
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}
	
    
}