package com.kyx.basic.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.kyx.basic.role.mapper.RoleMapper;
import com.kyx.basic.role.model.Role;
import com.kyx.basic.util.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.shops.model.Shops;
import com.kyx.basic.user.mapper.UserMapper;
import com.kyx.basic.user.model.User;
import com.kyx.basic.user.service.UserService;
import com.kyx.biz.paytype.model.PayType;
@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
    @Resource
	private UserService userService;
	@Resource
	private RoleMapper roleMapper;

	@Override
	public User querySingleUser(String username) {
		
		return userMapper.querySingleUser(username);
	}

	@Override
	public GrdData getUserInfo(User user, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<User> list=new ArrayList<User>();
		Shops shops=user.getShops();
		if("0".equals(shops.getShopKey())){//不是主门店要特殊处理 加入老板账户
			list.add(userMapper.selectByPrimaryKey(shops.getBossId()));
		}
		list.addAll(userMapper.getUserInfo(user));
		for(User u:list){
			u.setStatusStr("1".equals(u.getStatus())?"在职":"离职");
		}
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public RetInfo saveData(User user) {
		RetInfo retInfo;
		int count=0;
		//用户名使用手机号登录
		user.setUserName(user.getUserPhone());
		user.setUserPassword(Common.getInitPwd());//设定初始密码
		user.setEnable("1");
		user.setRegTime(new Date());
		if(user.getId()==null){//新增
			count=userMapper.insertSelective(user);
		}else{
			count=userMapper.updateByPrimaryKey(user);
		}
        if (count > 0) {
			retInfo=new RetInfo("success","保存成功");
        } else {
            retInfo = new RetInfo("error", "保存失败");
        }
		return retInfo;
	}

	@Override
	public RetInfo delData(String ids) {
		RetInfo retInfo;
		String[] idArr=ids.split(";");
		int count=0;
		for(int i=0;i<idArr.length;i++){
			count=count+userMapper.deleteByPrimaryKey(Integer.valueOf(idArr[i]));
		}
        if (count == idArr.length) {
			retInfo=new RetInfo("success","删除成功");
        } else {
            retInfo = new RetInfo("error", "删除失败");
        }
		return retInfo;
	}

	@Override
	public RetInfo checkUserCount(HttpSession session) {
		RetInfo retInfo;
		Shops shops=(Shops) session.getAttribute("shopsSession");
		if(shops==null){
			retInfo=new RetInfo("error","登陆信息失效");
			return retInfo;
		}
		//最大用户量
		int maxCount=shops.getUserCount();
		int curCount=userMapper.getCountByShop(shops.getId());
		if(curCount>=maxCount){
			retInfo=new RetInfo("error","员工数量添加已达到上限，请联系管理员添加权限");
			return retInfo;
		}
		retInfo=new RetInfo("success","");
		return retInfo;
	}

	@Override
	public Integer getByRoleId(Integer roleId,Integer shopId) {
		return userMapper.getByRoleId(roleId,shopId);
	}

	@Override
	public List<User> getByShopId(Integer shopId) {
		User user=new User();
		user.setShopId(shopId);
		return userMapper.getUserInfo(user);
	}

	@Override
	public Map<Integer, String> getUserMap(Integer shopId) {
		List<User> users =  getByShopId(shopId);
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		if (!ObjectUtils.isEmpty(users)){
			for (User u : users){
				typeMap.put(u.getId(), u.getUserRealname());
			}
		}
		return typeMap;
	}

	@Override
	public RetInfo resetPassWord(User user) {
		RetInfo retInfo = null;
		//验证旧密码
		User oldUser=userMapper.selectByPrimaryKey(user.getId());
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		if(!(user.getPwd2()).equals(user.getPwd3())){
			retInfo=RetInfo.Error("新密码和确认密码不一致");
			return retInfo;
		}
		if(encoder.matches(user.getPwd1(), oldUser.getUserPassword())){//匹配成功  修改密码
			user.setUserPassword(encoder.encode(user.getPwd2()));
			userMapper.updateByPrimaryKeySelective(user);
			retInfo=RetInfo.Success("修改成功");
		}else{
			retInfo=RetInfo.Error("旧密码输入有误，请重新输入");
		}
		return retInfo;
	
	}

	@Override
	public List<User> getByShop(Shops shops) {
		List<User> list=new ArrayList<User>();
		if("0".equals(shops.getShopKey())){//不是主门店要特殊处理 加入老板账户
			list.add(userMapper.selectByPrimaryKey(shops.getBossId()));
		}
		list.addAll(getByShopId(shops.getId()));
		return list;
	}

	@Override
	public GrdData queryBossAccount(User user, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<User> list=userMapper.getUserInfo(user);
		return new GrdData(page.getTotal(),list);
	}

	@Override
	public RetInfo saveBossAccountInfo(User user) {
		RetInfo retInfo;
		int count=0;
		//用户名使用手机号登录
		user.setUserName(user.getUserPhone());
		if(user.getId()==null){//新增
			//同一个手机只能注册一次（判断用户是否已经注册）
			User users = userService.querySingleUser(user.getUserPhone());
			if(users!=null) {
				
				retInfo=new RetInfo("error","手机号已经存在");
				
				return retInfo;
			}
			user.setUserPassword(Common.getInitPwd());//设定初始密码
			user.setStatus("1");
			user.setEnable("1");
			user.setRoleId(2);//老板
			user.setBossAccount(1);
			user.setRegTime(new Date());
			count=userMapper.insertSelective(user);
		}else{
			count=userMapper.updateByPrimaryKeySelective(user);
		}
        if (count > 0) {
			retInfo=new RetInfo("success","保存成功");
        } else {
            retInfo = new RetInfo("error", "保存失败");
        }
		return retInfo;

    }

    @Override
    public RetInfo wechatRegisterUser(User user) {
        if (user == null) {
            return new RetInfo(false, "用户信息获取错误!");
        }
        User exist = userMapper.queryExistUserName(user.getUserPhone());
        if (exist != null) {
            return new RetInfo(false, "当前账号已被使用, 不可重复注册!");
        }
        //用户名使用手机号登录
        user.setUserName(user.getUserPhone());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUserPassword(encoder.encode(user.getUserPassword()));//设定初始密码
        user.setEnable("1");
        user.setRegTime(new Date());
		// 设置角色
		Role role = roleMapper.queryByRoleKeySingle(BasicContant.ROLE_KEY_WX_USER);
		if (role == null) {
			role = new Role();
			role.setName("微信社区人员");
			role.setDescription("微信社区注册人员");
			role.setRoleKey(BasicContant.ROLE_KEY_WX_USER);
			role.setEnable("1");
			roleMapper.insertSelective(role);
		}
		user.setRoleId(role.getId());
        if (userMapper.insertSelective(user) > 0) {
            return new RetInfo(true, "注册成功!", userMapper.selectByPrimaryKey(user.getId()));
        } else {
            return new RetInfo(false, "信息保存失败!");
        }
    }

    @Override
    public User queryExistUserName(String userName) {
        return userMapper.queryExistUserName(userName);
    }
}
