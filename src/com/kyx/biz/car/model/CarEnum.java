package com.kyx.biz.car.model;


/**
 * car枚举类
 * 
 * @author daibin
 * @date 2019-4-16 上午10:00:45
 * 
 */
public enum CarEnum {

	// 1轿车 2SUV 3MPV 4其他
	SEDAN("轿车", 1), SUV("SUV", 2), MPV("MPV", 3), OTHER("其他", 4);

	private String name;
	private int code;

	private CarEnum(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public int getType() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public static String getCarTypeName(int code) {
		for (CarEnum car : CarEnum.values()) {
			if (car.getType() == code) {
				return car.name;
			}
		}

		return "";
	}
}