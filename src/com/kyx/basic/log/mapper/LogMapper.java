package com.kyx.basic.log.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.basic.log.model.Log;

@Repository("logMapper")
public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    List<Log> getList(Log log);
}