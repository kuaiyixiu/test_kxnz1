package com.kyx.biz.userskills.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.userskills.mapper.UserSkillsMapper;
import com.kyx.biz.userskills.model.UserSkills;
import com.kyx.biz.userskills.service.UserSkillsService;
@Service("userSkillsService")
public class UserSkillsServiceImpl implements UserSkillsService {
	@Resource
	private UserSkillsMapper userSkillsMapper; 

	@Override
	public RetInfo saveData(Integer userId, String ids, HttpSession session) {
		String[] idArr=ids.split(";");
		RetInfo retInfo;
		List<UserSkills> list=new ArrayList<UserSkills>();
		Shops shops=(Shops) session.getAttribute("shopsSession");
		for(String id:idArr){
			if(StringUtils.isEmpty(id))
				continue;
			UserSkills skills=new UserSkills();
			skills.setServeId(Integer.valueOf(id));
			skills.setUserId(userId);
			skills.setShopId(shops.getId());
			list.add(skills);
		}
		//删除之前员工的技能  
		userSkillsMapper.deleteByUserId(userId);
		int count=userSkillsMapper.insertBatches(list);
		if(count==list.size())
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

}
