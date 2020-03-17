package com.kyx.biz.wechatpublic.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.wechatpublic.model.GroupSend;
@Repository("groupSendMapper")
public interface GroupSendMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(GroupSend record);

    GroupSend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupSend record);

    List<GroupSend> getList(GroupSend groupSend);
}