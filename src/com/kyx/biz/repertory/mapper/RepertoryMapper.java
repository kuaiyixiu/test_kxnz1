package com.kyx.biz.repertory.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.repertory.model.Repertory;
@Repository("repertoryMapper")
public interface RepertoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Repertory record);

    int insertSelective(Repertory record);

    Repertory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Repertory record);

    int updateByPrimaryKey(Repertory record);

	List<Repertory> getInfo(Repertory repertory);
}