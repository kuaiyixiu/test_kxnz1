package com.kyx.biz.remind.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.remind.model.Remind;

@Repository("remindMapper")
public interface RemindMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Remind record);

	int insertSelective(Remind record);

	Remind selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Remind record);

	int updateByPrimaryKey(Remind record);

	/**
	 * 查询提醒
	 * 
	 * @param remind
	 * @return
	 */
	List<Remind> selectReminds(Remind remind);

	/**
	 * 查询提供count
	 * 
	 * @param remind
	 * @return
	 */
	int selectRemindsCount(Remind remind);
}