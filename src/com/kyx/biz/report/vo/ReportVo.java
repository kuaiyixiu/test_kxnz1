package com.kyx.biz.report.vo;

import java.math.BigDecimal;

public class ReportVo {
	
	private String creatTime;
	
	private Integer y1Count;
	
	private Integer y2Count;
	
	private Integer y3Count;
	
	private Integer orderCount;
	
	private BigDecimal y1Float;
	
	private BigDecimal y2Float;
	
	private BigDecimal y3Float;

	public Integer getY1Count() {
		return y1Count;
	}

	public void setY1Count(Integer y1Count) {
		this.y1Count = y1Count;
	}

	public Integer getY2Count() {
		return y2Count;
	}

	public void setY2Count(Integer y2Count) {
		this.y2Count = y2Count;
	}

	public Integer getY3Count() {
		return y3Count;
	}

	public void setY3Count(Integer y3Count) {
		this.y3Count = y3Count;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public BigDecimal getY1Float() {
		return y1Float;
	}

	public void setY1Float(BigDecimal y1Float) {
		this.y1Float = y1Float;
	}

	public BigDecimal getY2Float() {
		return y2Float;
	}

	public void setY2Float(BigDecimal y2Float) {
		this.y2Float = y2Float;
	}

	public BigDecimal getY3Float() {
		return y3Float;
	}

	public void setY3Float(BigDecimal y3Float) {
		this.y3Float = y3Float;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	
}
