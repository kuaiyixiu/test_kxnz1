package com.kyx.basic.sysparam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.basic.sysparam.model.SysParam;
@Repository("sysParamMapper")
public interface SysParamMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByShopId(Integer shopId);

    int insertSelective(SysParam record);

    SysParam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysParam record);
    
    SysParam getByName(@Param("paramName")String paramName,@Param("shopId")Integer shopId);
    
    List<SysParam> getListByShopId(Integer shopId);

    int insertBatches(@Param("list") List<SysParam> pramList);
}