package com.kyx.biz.lock.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.kyx.biz.lock.model.LockCommand;
import com.kyx.biz.lock.model.LockCommandExample;

public interface LockCommandMapper {
  long countByExample(LockCommandExample example);

  int deleteByExample(LockCommandExample example);

  int deleteByPrimaryKey(Integer id);

  int insert(LockCommand record);

  int insertSelective(LockCommand record);

  List<LockCommand> selectByExample(LockCommandExample example);

  LockCommand selectByPrimaryKey(Integer id);

  int updateByExampleSelective(@Param("record") LockCommand record,
      @Param("example") LockCommandExample example);

  int updateByExample(@Param("record") LockCommand record,
      @Param("example") LockCommandExample example);

  int updateByPrimaryKeySelective(LockCommand record);

  int updateByPrimaryKey(LockCommand record);

  LockCommand selectByLockNumber(@Param("lockNumber") Integer lockNumber);
}
