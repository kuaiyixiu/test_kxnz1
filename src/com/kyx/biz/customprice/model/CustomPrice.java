package com.kyx.biz.customprice.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomPrice implements Serializable {
    private Integer id;

    private Integer custType;

    private Integer kind;

    private Integer itemId;

    private BigDecimal price;

    private static final long serialVersionUID = 1L;
    
    private Integer shopId;
    
    private String name;
    private BigDecimal oldprice;
    
    private String itemName;
    
    private Integer classId;
    
    private BigDecimal zc;//折扣
    private String ids;//产品或服务id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getOldprice() {
		return oldprice;
	}

	public void setOldprice(BigDecimal oldprice) {
		this.oldprice = oldprice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public BigDecimal getZc() {
		return zc;
	}

	public void setZc(BigDecimal zc) {
		this.zc = zc;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
    
}