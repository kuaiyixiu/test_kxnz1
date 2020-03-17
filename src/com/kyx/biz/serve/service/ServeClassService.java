package com.kyx.biz.serve.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.user.model.User;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.serve.model.ServeClass;

public interface ServeClassService {
    /**
     * 查询列表
     * @param serveClass
     * @param pager
     * @return
     */
	GrdData getInfo(ServeClass serveClass, Pager pager);
    /**
     * 保存数据
     * @param serveClass
     * @return
     */
	@Transactional
	RetInfo saveData(ServeClass serveClass);
	/**
	 * 删除数据
	 * @param ids
	 * @return
	 */
	@Transactional
	RetInfo delData(String ids);
	/**
	 * 根據主鍵查詢
	 * @param id
	 * @return
	 */
	ServeClass getById(Integer id);
	
	List<ServeClass> getAll();

	/**
	 * 根据用户查询所有服务
	 * @param userId
	 * @param session 
	 * @return
	 */
	List<Map> getAllInfo(Integer userId, HttpSession session);
	/**
	 * 查询
	 * @param serveClass
	 * @return
	 */
	List<ServeClass> getByServeClass(ServeClass serveClass);


}
