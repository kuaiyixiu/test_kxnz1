package com.kyx.basic.car.model;
import	java.util.ArrayList;

import java.io.Serializable;
import java.util.List;

/**
 * car_brand 品牌
 * @author 
 */
public class CarBrand implements Serializable {
    /**
     * 品牌ID
     */
    private Integer id;

    /**
     * 品牌首字母
     */
    private String firstChar;
    private Integer categoryId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 品牌logo地址
     */
    private String logoUrl;

    private List<CarSeries> carSeries;

    public CarBrand() {
        this.brandName = "";
        this.carSeries = new ArrayList<CarSeries> ();
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public List<CarSeries> getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(List<CarSeries> carSeries) {
        this.carSeries = carSeries;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}