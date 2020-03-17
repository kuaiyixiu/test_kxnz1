package com.kyx.basic.car.util;


/**
 * 汽车之家车辆配置详情数据接口
 * 数据具体值. 解析是去第一个
 */
public class CarValueitems {
    private int specid;
    private String value;

    public int getSpecid() {
        return specid;
    }

    public void setSpecid(int specid) {
        this.specid = specid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "CarValueitems{" +
                "specid=" + specid +
                ", value='" + value + '\'' +
                '}';
    }
}
