package com.kyx.biz.wechatpublic.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.wechatpublic.model.AccountMenu;
@Repository("accountMenuMapper")
public interface AccountMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountMenu record);

    int insertSelective(AccountMenu record);

    AccountMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccountMenu record);

    int updateByPrimaryKey(AccountMenu record);

	List<AccountMenu> listWxMenus();

	int delete();
}