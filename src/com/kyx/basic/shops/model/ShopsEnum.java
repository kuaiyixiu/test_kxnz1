package com.kyx.basic.shops.model;

/**
 * car枚举类
 * 
 * @author daibin
 * @date 2019-4-16 上午10:00:45
 * 
 */
public enum ShopsEnum {

	// 1编辑，2待施工（已提交），3施工中，4施工完成 5入账 6挂账 7反入账 8反挂账
	RUNNING("运行中", 0), TRY_OUT("试用期", 1), ARREARS("已欠费", 2), EXPIRED("已到期", 3), DISABLE(
			"禁用", -1);

	private String name;
	private int code;

	private ShopsEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public int getType() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public static String getOrdersName(int code) {
		for (ShopsEnum order : ShopsEnum.values()) {
			if (order.getType() == code) {
				return order.name;
			}
		}

		return "";
	}
}