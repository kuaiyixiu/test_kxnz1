package com.kyx.biz.orders.model;

/**
 * car枚举类
 * 
 * @author daibin
 * @date 2019-4-16 上午10:00:45
 * 
 */
public enum OrdersEnum {

	// 1编辑，2待施工（已提交），3施工中，4施工完成 5入账 6挂账 7反入账 8反挂账
	EDIT("编辑", 1), WAIT_WORK("待施工", 2), Conduct_WORK("施工中", 3), FINISH_WORK(
			"施工完成", 4), ACCOUNT_ENTRY("入账", 5), TO_SETTLE_ACCOUNTS("挂账", 6), REVERSE_ENTRY(
			"反入账", 7), COUNTER_CHARGE("反挂账", 8);

	private String name;
	private int code;

	private OrdersEnum(String name, int code) {
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
		for (OrdersEnum order : OrdersEnum.values()) {
			if (order.getType() == code) {
				return order.name;
			}
		}

		return "";
	}
}