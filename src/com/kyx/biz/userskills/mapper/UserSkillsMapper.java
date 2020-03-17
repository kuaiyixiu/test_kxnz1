package com.kyx.biz.userskills.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.biz.userskills.model.UserSkills;
@Repository("userSkillsMapper")
public interface UserSkillsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserSkills record);

    int insertSelective(UserSkills record);

    UserSkills selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserSkills record);

    int updateByPrimaryKey(UserSkills record);
    
    int insertBatches(@Param("list") List<UserSkills> userSkills);

	int deleteByUserId(Integer userId);
}