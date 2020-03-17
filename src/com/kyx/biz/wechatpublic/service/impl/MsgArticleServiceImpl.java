package com.kyx.biz.wechatpublic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.biz.wechatpublic.mapper.MsgArticleDao;
import com.kyx.biz.wechatpublic.model.MsgArticle;
import com.kyx.biz.wechatpublic.service.MsgArticleService;

@Service("msgArticleService")
public class MsgArticleServiceImpl implements MsgArticleService {
	
	@Resource
	private MsgArticleDao msgArticleDao;
	

	@Override
	public void update(MsgArticle article) {
		msgArticleDao.update(article);
	}

}
