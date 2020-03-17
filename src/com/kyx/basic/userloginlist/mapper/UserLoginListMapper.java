package com.kyx.basic.userloginlist.mapper;

import org.springframework.stereotype.Repository;

import com.kyx.basic.userloginlist.model.UserLoginList;
@Repository("userLoginListMapper")
public interface UserLoginListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginList record);

    int insertSelective(UserLoginList record);

    UserLoginList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginList record);

    int updateByPrimaryKey(UserLoginList record);
}