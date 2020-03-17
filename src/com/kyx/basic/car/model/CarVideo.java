package com.kyx.basic.car.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * car_video
 * 车辆视频信息
 * @author 
 */
public class CarVideo implements Serializable {
    private Integer id;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频描述
     */
    private String describe;

    /**
     * 视频存储地址
     */
    private String dirUrl;

    /**
     * 视频封面存储地址
     */
    private String coverUrl;

    /**
     * 视频类型ID
     */
    private Integer classId;
    // 视频类型名称
    private String className;

    /**
     * 车型id
     */
    private Integer modelId;

    /**
     * 浏览量
     */
    private Integer browseNum;

    /**
     * 收藏量
     */
    private Integer collectNum;

    /**
     * 作者id
     */
    private Integer author;
    private String authorName;

    /**
     * 收费类型(0:免费; 1:VIP; 2:收费)
     */
    private Integer member;

    /**
     * 费用
     */
    private BigDecimal charge;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDirUrl() {
        return dirUrl;
    }

    public void setDirUrl(String dirUrl) {
        this.dirUrl = dirUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(Integer browseNum) {
        this.browseNum = browseNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "CarVideo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", dirUrl='" + dirUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                ", modelId=" + modelId +
                ", browseNum=" + browseNum +
                ", collectNum=" + collectNum +
                ", author=" + author +
                ", authorName='" + authorName + '\'' +
                ", member=" + member +
                ", charge=" + charge +
                ", createTime=" + createTime +
                '}';
    }
}