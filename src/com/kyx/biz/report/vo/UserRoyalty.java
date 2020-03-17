package com.kyx.biz.report.vo;

import java.math.BigDecimal;

/**
 * 员工产值
 * @author tyg
 *
 */
public class UserRoyalty {
	
	private Integer userId;
	
	private String userRealname; //员工姓名
	
	private BigDecimal serveRoyal; //服务施工产值
	
	private BigDecimal sellRoyal;//服务销售产值
	
	private BigDecimal productRoyal; //产品销售产值
	
	

	public UserRoyalty() {
		super();
		this.serveRoyal = BigDecimal.ZERO;
		this.sellRoyal = BigDecimal.ZERO;
		this.productRoyal = BigDecimal.ZERO;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public BigDecimal getServeRoyal() {
		return serveRoyal;
	}

	public void setServeRoyal(BigDecimal serveRoyal) {
		this.serveRoyal = serveRoyal;
	}

	public BigDecimal getSellRoyal() {
		return sellRoyal;
	}

	public void setSellRoyal(BigDecimal sellRoyal) {
		this.sellRoyal = sellRoyal;
	}

	public BigDecimal getProductRoyal() {
		return productRoyal;
	}

	public void setProductRoyal(BigDecimal productRoyal) {
		this.productRoyal = productRoyal;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
	
	
	
}
