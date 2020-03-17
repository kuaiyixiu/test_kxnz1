package com.kyx.basic.role.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.basic.role.model.Role;
@Repository("roleMapper")
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    /**
     * 排除系统管理员角色和老板角色 这是内置角色
     * @param role
     * @return
     */
    List<Role> getRolesInfo(Role role);

	List<Role> getAll();

    /**
     * 根据role_key查询符合条件的第一个角色
     *
     * @param roleKey
     * @return
     */
    Role queryByRoleKeySingle(@Param("roleKey") String roleKey);
}