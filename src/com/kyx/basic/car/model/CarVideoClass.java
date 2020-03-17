package com.kyx.basic.car.model;

import java.io.Serializable;

/**
 * car_video_class
 * @author 
 */
public class CarVideoClass implements Serializable {
    private Integer id;

    /**
     * è§†é¢‘ç±»åž‹å��ç§°
     */
    private String name;

    /**
     * ç±»åž‹iconåœ°å�€, ä¸ºç©ºæ˜¯ä¸�æ˜¾ç¤º
     */
    private String imgUrl;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}