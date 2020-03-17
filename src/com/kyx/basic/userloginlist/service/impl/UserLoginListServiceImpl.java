package com.kyx.basic.userloginlist.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.basic.userloginlist.mapper.UserLoginListMapper;
import com.kyx.basic.userloginlist.model.UserLoginList;
import com.kyx.basic.userloginlist.service.UserLoginListService;
@Service("userLoginListService")
public class UserLoginListServiceImpl implements UserLoginListService {
	@Resource
    private UserLoginListMapper userLoginListMapper;
	@Override
	public void add(UserLoginList userLoginList) {
		userLoginListMapper.insertSelective(userLoginList);
		
	}

}
