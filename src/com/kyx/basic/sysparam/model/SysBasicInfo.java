package com.kyx.basic.sysparam.model;

import java.io.Serializable;

/**
 * sys_basic_info
 * 信息标志位表, 记录标志位及标志位相关信息
 *
 * @author
 */
public class SysBasicInfo implements Serializable {
    public static final int BANNERS = 1;
    private Integer id;

    /**
     * 标志位类型(不可重复)
     */
    private Integer flag;

    /**
     * 标志位(每个类型下值唯一)
     */
    private String sign;

    /**
     * 标记值
     */
    private String value;

    /**
     * 链接
     */
    private String href;

    /**
     * 备注
     */
    private String remarks;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}