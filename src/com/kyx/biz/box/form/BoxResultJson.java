package com.kyx.biz.box.form;

public class BoxResultJson {
	private String msg;

	private String code;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static final String SUCCESS_MSG = "SUCCESS";

	public static final String SUCCESS_CODE = "2000";
}
