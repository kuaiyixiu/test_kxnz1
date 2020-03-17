package com.kyx.basic.car.model;

import java.io.Serializable;

/**
 * car_model 车型
 * @author 
 */
public class CarModel implements Serializable {
    /**
     * 车型ID
     */
    private Integer id;

    /**
     * 车型名称
     */
    private String modelName;

    /**
     * 车系ID
     */
    private Integer seriesId;

    /**
     * 品牌ID
     */
    private Integer brandId;

    /**
     * 年代
     */
    private String year;

    /**
     * 发动机描述
     */
    private String engine;

    /**
     * 发动机型号
     */
    private String engineModel;

    /**
     * 排量
     */
    private String displacement;

    /**
     * 档位数
     */
    private Integer gearsNum;

    /**
     * 变速箱
     */
    private String gearbox;

    /**
     * 变速箱简称
     */
    private String gear;

    public CarModel() {
        this.modelName = "";
        this.year = "";
        this.engine ="";
        this.engineModel ="";
        this.engine ="";
        this.gearsNum = 0;
        this.gearbox ="";
        this.gear = "";
        this.displacement = "";
        this.seriesId = 0;
        this.brandId = 0;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public Integer getGearsNum() {
        return gearsNum;
    }

    public void setGearsNum(Integer gearsNum) {
        this.gearsNum = gearsNum;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }
}