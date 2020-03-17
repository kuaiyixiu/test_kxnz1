package com.kyx.biz.serve.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.kyx.biz.serve.model.Serve;

@Repository("serveMapper")
public interface ServeMapper {
  int deleteByPrimaryKey(Integer id);

  int insert(Serve record);

  int insertSelective(Serve record);

  Serve selectByPrimaryKey(Integer id);

  Serve selectByName(@Param("serveName") String serveName, @Param("shopId") Integer shopId);

  int updateByPrimaryKeySelective(Serve record);

  int updateByPrimaryKey(Serve record);

  List<Serve> getServeList(Serve record);

  List<Serve> getServeCustomPrice(Serve record);

  int getByClassId(Integer classId);

  List<Serve> getListByClassId(Serve serve);

  int getCountByServe(Serve serve);

  List<Serve> getServesByClassId(Serve serve);
}
