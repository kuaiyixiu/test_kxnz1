package com.kyx.basic.car.util;

import java.util.List;

/**
 * 汽车之家车辆配置详情数据接口
 * 数据名称与编号
 */
public class CarParamitems {
    private int id;
    private String name;
    private String pnid;
    private List<CarValueitems> valueitems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnid() {
        return pnid;
    }

    public void setPnid(String pnid) {
        this.pnid = pnid;
    }

    public List<CarValueitems> getValueitems() {
        return valueitems;
    }

    public void setValueitems(List<CarValueitems> valueitems) {
        this.valueitems = valueitems;
    }

    @Override
    public String toString() {
        return "CarParamitems{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pnid='" + pnid + '\'' +
                ", valueitems=" + valueitems +
                '}';
    }
}
