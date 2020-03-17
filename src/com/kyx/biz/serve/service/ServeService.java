package com.kyx.biz.serve.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.serve.model.Serve;

public interface ServeService {

	GrdData getServePage(Serve serve, Pager pager);
	
	List<Serve> getServeList(Serve serve);
	
	
	/**
	 * 获取会员服务
	 * @param record
	 * @return
	 */
	 List<Serve> getServeCustomPrice(Serve serve);
	
    /**
     * 根据classId查询
     * @param classId
     * @return
     */
	int getByClassId(Integer classId);
    @Transactional
	RetInfo saveData(Serve serve);
    @Transactional
	RetInfo delData(String ids);
    /**
     * 查新
     * @param id
     * @return
     */
	Serve getById(Integer id);
    /**
     * 根据class主键查询
     * @param id
     * @param integer 
     * @return
     */
	List<Serve> getListByClassId(Integer userId, Integer classId);
	
	
	/**
	 * 通过ids（都逗号隔开），获取服务列表 
	 * @param ids
	 * @return
	 */
	List<Serve> getServeListByIds(String ids);
	
	/**
	 * 根据shopId和名称用equal查询是否有该服务
	 * @param serveName
	 * @param shopId
	 * @return
	 */
	Serve selectByName(String serveName,Integer shopId);
}
