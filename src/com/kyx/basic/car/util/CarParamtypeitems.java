package com.kyx.basic.car.util;

import java.util.List;

/**
 * 汽车之家车辆配置详情数据接口
 * 数据类别
 */
public class CarParamtypeitems {
    private String name;
    private List<CarParamitems> paramitems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarParamitems> getParamitems() {
        return paramitems;
    }

    public void setParamitems(List<CarParamitems> paramitems) {
        this.paramitems = paramitems;
    }

    @Override
    public String toString() {
        return "CarParamtypeitems{" +
                "name='" + name + '\'' +
                ", paramitems=" + paramitems +
                '}';
    }

}
