package com.kyx.basic.sysparam.service;

import com.kyx.basic.sysparam.model.SysBasicInfo;

import java.util.List;

public interface SysBasicInfoService {

    boolean insertSelective(SysBasicInfo record);

    SysBasicInfo selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(SysBasicInfo record);

    boolean deleteByPrimaryKey(Integer id);

    /**
     * 根据标志位类型获取信息
     *
     * @param flag
     * @return
     */
    List<SysBasicInfo> queryBasicInfosByFlag(int flag);

    /**
     * 根据标志位类型和标志信息查询
     *
     * @param flag
     * @param sign
     * @return
     */
    SysBasicInfo queryBasicInfo(int flag, String sign);
}
