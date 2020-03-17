package com.kyx.biz.userskills.service;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.RetInfo;

public interface UserSkillsService {
    @Transactional
	RetInfo saveData(Integer userId, String ids, HttpSession session);

}
