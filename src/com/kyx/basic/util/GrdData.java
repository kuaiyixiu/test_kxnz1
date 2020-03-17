package com.kyx.basic.util;

import java.util.List;

import com.github.pagehelper.Page;

public class GrdData {
	private String code;
	private String msg;
	private Integer count;
	private List data;
	
	private Pager pager;
	
	public GrdData(String code, String msg, Long count, List data) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = Long.valueOf(count).intValue();
		this.data = data;
	}
	
	
	public GrdData(Long count, List data) {
		super();
		this.code = "0";
		this.msg = "";
		this.count = Long.valueOf(count).intValue();
		this.data = data;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}


	public Pager getPager() {
		return pager;
	}


	public void setPager(Pager pager) {
		this.pager = pager;
	}

	
	

}
