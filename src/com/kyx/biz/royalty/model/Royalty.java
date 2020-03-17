package com.kyx.biz.royalty.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Royalty implements Serializable {
    private Integer id;

    private String kind;

    private Integer royaltyId;

    private String royaltyType;

    private BigDecimal royaltyCount;

    private static final long serialVersionUID = 1L;
    
    private String royaltyName;
    
    private Integer shopId;
    private int[] rid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public Integer getRoyaltyId() {
        return royaltyId;
    }

    public void setRoyaltyId(Integer royaltyId) {
        this.royaltyId = royaltyId;
    }

    public String getRoyaltyType() {
        return royaltyType;
    }

    public void setRoyaltyType(String royaltyType) {
        this.royaltyType = royaltyType == null ? null : royaltyType.trim();
    }

    public BigDecimal getRoyaltyCount() {
        return royaltyCount;
    }

    public void setRoyaltyCount(BigDecimal royaltyCount) {
        this.royaltyCount = royaltyCount;
    }

	public String getRoyaltyName() {
		return royaltyName;
	}

	public void setRoyaltyName(String royaltyName) {
		this.royaltyName = royaltyName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public int[] getRid() {
		return rid;
	}

	public void setRid(int[] rid) {
		this.rid = rid;
	}
    
    
}