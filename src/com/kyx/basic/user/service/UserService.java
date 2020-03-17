package com.kyx.basic.user.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.model.User;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;

public interface UserService {
	/**
	 * 根据用户名查询
	 * 
	 * @param username
	 * @return
	 */
	User querySingleUser(String username);

	/**
	 * 分页条件查询
	 * 
	 * @param user
	 * @param pager
	 * @return
	 */
	GrdData getUserInfo(User user, Pager pager);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	User selectByPrimaryKey(Integer id);

	/**
	 * 存档或修改
	 * 
	 * @param user
	 * @return
	 */
	@Transactional
	RetInfo saveData(User user);

	/**
	 * 删除
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	RetInfo delData(String ids);

	/**
	 * 验证门店员工数量
	 * 
	 * @param session
	 * @return
	 */
	RetInfo checkUserCount(HttpSession session);

	/**
	 * 根据角色查询
	 * 
	 * @param roleId
	 * @param shopId 
	 * @return
	 */
	Integer getByRoleId(Integer roleId, Integer shopId);

	/**
	 * 根据门店查询员工
	 * 
	 * @param shopId
	 * @return
	 */
	List<User> getByShopId(Integer shopId);

	Map<Integer, String> getUserMap(Integer shopId);
    /**
     * 密码重置
     * @param user
     * @return
     */
	RetInfo resetPassWord(User user);
    /**
     * 根据门店查询员工列表
     * @param shops
     * @return
     */
	List<User> getByShop(Shops shops);
    /**
     * 分页查询
     * @param user
     * @param pager
     * @return
     */
	GrdData queryBossAccount(User user, Pager pager);
    /**
     * 保存老板账户
     * @param user
     * @return
     */
	RetInfo saveBossAccountInfo(User user);


	/**
	 * 微信公总号添加用户
	 *
	 * @param user 用户信息{userPhone, userPassword, userRealname, email}
	 * @return
	 */
	RetInfo wechatRegisterUser(User user);

	/**
	 * 查询已存在的用户
	 *
	 * @param userName
	 * @return
	 */
	User queryExistUserName(String userName);
}
