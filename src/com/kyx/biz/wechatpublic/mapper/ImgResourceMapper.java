package com.kyx.biz.wechatpublic.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kyx.biz.wechatpublic.model.ImgResource;
@Repository("imgResourceMapper")
public interface ImgResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ImgResource record);

    int insertSelective(ImgResource record);

    ImgResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImgResource record);

    int updateByPrimaryKey(ImgResource record);

	List<ImgResource> getInfo(ImgResource imgResource);
	
	ImgResource selectByMediaId(String mediaId);
}