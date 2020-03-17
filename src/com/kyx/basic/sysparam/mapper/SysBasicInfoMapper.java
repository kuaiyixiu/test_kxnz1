package com.kyx.basic.sysparam.mapper;

import com.kyx.basic.sysparam.model.SysBasicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysBasicInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysBasicInfo record);

    int insertSelective(SysBasicInfo record);

    SysBasicInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysBasicInfo record);

    int updateByPrimaryKey(SysBasicInfo record);

    List<SysBasicInfo> queryBasicInfosByFlag(@Param("flag") int flag);

    SysBasicInfo queryBasicInfo(@Param("flag") int flag, @Param("sign") String sign);
}