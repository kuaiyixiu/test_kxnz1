package com.kyx.biz.wechatpublic.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.wechatpublic.model.CardSet;
@Repository
public interface CardSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CardSet record);

    int insertSelective(CardSet record);

    CardSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CardSet record);

    int updateByPrimaryKey(CardSet record);

	List<CardSet> getInfo(CardSet cardSet);
}