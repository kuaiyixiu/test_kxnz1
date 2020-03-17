package com.kyx.biz.wechatpublic.service;

import org.springframework.transaction.annotation.Transactional;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.model.ImgResource;

public interface ImgResourceService {
    /**
     * 分页查询列表
     * @param imgResource
     * @param pager
     * @return
     */
	GrdData getImgListByPage(ImgResource imgResource, Pager pager);
    /**
     * 新增图片
     * @param img
     * @return
     */
	@Transactional
	RetInfo addImg(ImgResource img);
	
	ImgResource getImg(Integer id);
	@Transactional
	int delImg(Integer id);

}
