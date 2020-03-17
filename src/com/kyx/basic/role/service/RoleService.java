package com.kyx.basic.role.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.role.model.Role;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;

public interface RoleService {
    /**
     * 分页查询
     * @param role
     * @param pager
     * @return
     */
	GrdData getRolesInfo(Role role, Pager pager);

	Role selectByPrimaryKey(Integer id);
	@Transactional
	RetInfo saveData(Role role);

	//RetInfo delData(String ids);
    /**
     * 获取所有可用的角色
     * @return
     */
	List<Role> getAll();
    /**
     * 根据角色查询菜单信息返回json数据组树形结构
     * @param id
     * @return
     */
	String getMenuInfoByRoleId(Integer id);
//	@Transactional
	RetInfo delData(String ids, Integer shopId);

}
