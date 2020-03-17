package com.kyx.biz.serve.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kyx.biz.report.vo.ReportQueryVo;
import com.kyx.biz.serve.model.ServeClass;

@Repository("serveClassMapper")
public interface ServeClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServeClass record);

    int insertSelective(ServeClass record);

    ServeClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServeClass record);

    int updateByPrimaryKey(ServeClass record);
    /**
     * 获取信息
     * @param serveClass
     * @return
     */
	List<ServeClass> getInfo(ServeClass serveClass);

	int getCountByName(ServeClass serveClass);
    /**
     * 根据名称和门店查询
     * @param serveClass
     * @return
     */
	ServeClass selectByName(ServeClass serveClass);
    /**
     * 查询服务分类产值
     * @param reportQueryVo
     * @return
     */
	List<Map> queryServeRep(ReportQueryVo reportQueryVo);
}