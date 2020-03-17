package com.kyx.biz.box.mapper;

import com.kyx.biz.box.model.BoxInfo;
import com.kyx.biz.box.model.BoxInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BoxInfoMapper {
    long countByExample(BoxInfoExample example);

    int deleteByExample(BoxInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BoxInfo record);

    int insertSelective(BoxInfo record);

    List<BoxInfo> selectByExample(BoxInfoExample example);

    BoxInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BoxInfo record, @Param("example") BoxInfoExample example);

    int updateByExample(@Param("record") BoxInfo record, @Param("example") BoxInfoExample example);

    int updateByPrimaryKeySelective(BoxInfo record);

    int updateByPrimaryKey(BoxInfo record);
}