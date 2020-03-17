package com.kyx.basic.resourcesrole.service;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.RetInfo;

public interface ResourcesRoleService {
    /**
     * 保存关系表
     * @param roleId
     * @param resIds
     * @return
     */
	@Transactional
	RetInfo savePermission(Integer roleId, String resIds);
	/**
	 * 根据 角色id删除
	 * @param Integer
	 * @return
	 */
	@Transactional
	int deleteByRoleId(Integer roleId);

}
