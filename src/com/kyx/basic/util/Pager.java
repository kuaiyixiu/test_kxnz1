package com.kyx.basic.util;


/**
 * 分页封装
 * @author 严大灯
 * @data 2019-3-13 下午3:48:06
 * @Descripton
 */
public class Pager{
	private int page;
	private int limit;
	private long total;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
}
