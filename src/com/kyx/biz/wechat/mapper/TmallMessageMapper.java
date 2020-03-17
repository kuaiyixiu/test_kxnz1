package com.kyx.biz.wechat.mapper;

import com.kyx.biz.wechat.model.TmallMessage;
import com.kyx.biz.wechat.model.TmallMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TmallMessageMapper {
    long countByExample(TmallMessageExample example);

    int deleteByExample(TmallMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TmallMessage record);

    int insertSelective(TmallMessage record);

    List<TmallMessage> selectByExampleWithBLOBs(TmallMessageExample example);

    List<TmallMessage> selectByExample(TmallMessageExample example);

    TmallMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TmallMessage record, @Param("example") TmallMessageExample example);

    int updateByExampleWithBLOBs(@Param("record") TmallMessage record, @Param("example") TmallMessageExample example);

    int updateByExample(@Param("record") TmallMessage record, @Param("example") TmallMessageExample example);

    int updateByPrimaryKeySelective(TmallMessage record);

    int updateByPrimaryKeyWithBLOBs(TmallMessage record);

    int updateByPrimaryKey(TmallMessage record);
}