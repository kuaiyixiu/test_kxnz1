package com.kyx.basic.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.basic.user.model.User;
@Repository("userMapper")
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User querySingleUser(String username);
	List<User> getUserInfo(User user);
    /**
     * 根据门店查询当前门店用户数量
     * @param id
     * @return
     */
	int getCountByShop(Integer id);

	int getByRoleId(@Param("roleId") Integer roleId, @Param("shopId") Integer shopId);

    User queryExistUserName(@Param("username") String username);
}