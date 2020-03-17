package com.kyx.biz.lock.mapper;

import com.kyx.biz.lock.model.LockInfo;
import com.kyx.biz.lock.model.LockInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LockInfoMapper {
    long countByExample(LockInfoExample example);

    int deleteByExample(LockInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LockInfo record);

    int insertSelective(LockInfo record);

    List<LockInfo> selectByExample(LockInfoExample example);

    LockInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LockInfo record, @Param("example") LockInfoExample example);

    int updateByExample(@Param("record") LockInfo record, @Param("example") LockInfoExample example);

    int updateByPrimaryKeySelective(LockInfo record);

    int updateByPrimaryKey(LockInfo record);
}