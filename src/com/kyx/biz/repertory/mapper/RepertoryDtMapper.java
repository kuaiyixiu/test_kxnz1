package com.kyx.biz.repertory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kyx.basic.resourcesrole.model.ResourcesRole;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertory.model.RepertoryDt;
@Repository("repertoryDtMapper")
public interface RepertoryDtMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RepertoryDt record);

    int insertSelective(RepertoryDt record);

    RepertoryDt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RepertoryDt record);

    int updateByPrimaryKey(RepertoryDt record);

	List<RepertoryDt> getInfo(RepertoryDt dt);

	int deleteByRepertoryId(Integer repertoryId);
    /**
     * 查询进货单历史 
     * @param dt
     * @return
     */
	List<RepertoryDt> getRepertoryInfo(RepertoryDt dt);
	
	/**
	 * 查询退货单
	 * @param dt
	 * @return
	 */
	List<RepertoryDt> getRepertoryBack(RepertoryDt dt);
    /**
     * 批量存储
     * @param dtList
     * @return
     */
	int insertBatches(@Param("list") List<RepertoryDt> list);
}