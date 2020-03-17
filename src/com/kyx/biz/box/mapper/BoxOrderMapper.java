package com.kyx.biz.box.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.kyx.biz.box.model.BoxOrder;
import com.kyx.biz.box.model.BoxOrderExample;

public interface BoxOrderMapper {
  long countByExample(BoxOrderExample example);

  int deleteByExample(BoxOrderExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(BoxOrder record);

  int insertSelective(BoxOrder record);

  List<BoxOrder> selectByExample(BoxOrderExample example);

  BoxOrder selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") BoxOrder record,
      @Param("example") BoxOrderExample example);

  int updateByExample(@Param("record") BoxOrder record, @Param("example") BoxOrderExample example);

  int updateByPrimaryKeySelective(BoxOrder record);

  int updateByPrimaryKey(BoxOrder record);

  List<BoxOrder> selectBoxOrder(BoxOrder boxOrder);
}
