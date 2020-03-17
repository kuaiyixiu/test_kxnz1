package com.kyx.basic.car.model;
import	java.util.ArrayList;

import java.io.Serializable;
import java.util.List;

/**
 * car_series 车系
 * @author 
 */
public class CarSeries implements Serializable {
    /**
     * 车系ID
     */
    private Integer id;

    /**
     * 车系名称
     */
    private String seriesName;

    /**
     * 品牌ID
     */
    private Integer brandId;
    private List<CarModel> carModels;

    public CarSeries() {
        this.seriesName = "";
        this.brandId = 0;
        this.carModels = new ArrayList<CarModel> ();
    }

    public void addCarModel(CarModel model){
        this.carModels.add(model);
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public List<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(List<CarModel> carModels) {
        this.carModels = carModels;
    }
}