package com.kyx.basic.car.model;

import java.io.Serializable;

/**
 * car_category 车辆类别
 * @author 
 */
public class CarCategory implements Serializable {
    /**
     * 类别ID
     */
    private Integer id;

    /**
     * 类别名称
     */
    private String category;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}