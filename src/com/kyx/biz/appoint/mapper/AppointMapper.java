package com.kyx.biz.appoint.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.appoint.model.Appoint;

@Repository("appointMapper")
public interface AppointMapper {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(Appoint record);

	Appoint selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Appoint record);

	List<Appoint> selectList(Appoint appoint);

	List<Appoint> selectCustomAppoints(
			@Param("dateRangeStartTime") Date dateRangeStartTime,
			@Param("dateRangeEndTime") Date dateRangeEndTime);
}