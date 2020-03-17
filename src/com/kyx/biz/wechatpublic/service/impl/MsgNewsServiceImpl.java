package com.kyx.biz.wechatpublic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.wechatpublic.mapper.MsgArticleDao;
import com.kyx.biz.wechatpublic.mapper.MsgNewsDao;
import com.kyx.biz.wechatpublic.model.ImgResource;
import com.kyx.biz.wechatpublic.model.MsgArticle;
import com.kyx.biz.wechatpublic.model.MsgNews;
import com.kyx.biz.wechatpublic.service.MsgNewsService;

@Service("msgNewsService")
public class MsgNewsServiceImpl implements MsgNewsService {
	
	@Resource
	private MsgNewsDao msgNewsDao;
	
	@Resource
	private MsgArticleDao msgArticleDao;

	@Override
	public RetInfo addMoreNews(MsgNews news) {
		List<MsgArticle> articles = news.getArticles();
    	List<MsgArticle> list = new ArrayList<MsgArticle>();
		news.setCreateTime(new Date());
    	//保存图文信息
    	this.msgNewsDao.addNews(news);
    	if(articles != null && articles.size() > 0){
    		for (int i = 0; i < articles.size(); i++) {
    			MsgArticle article=articles.get(i);
    			article.setNewsId(news.getId().intValue());
    			list.add(article);
    		}
    		msgArticleDao.insertByBatch(list);
    	}
    	
    	 return RetInfo.Success("");
	}

	@Override
	public GrdData getWebNewsListByPage(MsgNews searchEntity, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<MsgNews> list=  msgNewsDao.getWebNewsListByPage(searchEntity);
		GrdData grdData=new GrdData(page.getTotal(),list);
		pager.setTotal(page.getTotal());
		grdData.setPager(pager);
		return grdData;
	}

	@Override
	public MsgNews getById(String id) {
		return msgNewsDao.getById(id);
	}

	@Override
	public int updateMediaId(MsgNews msgNews) {
		return msgNewsDao.updateMediaId(msgNews);
	}

	@Override
	public int delete(Integer id) {
		msgArticleDao.deleteByBatch(id);
		return msgNewsDao.delete(id);
	}

	@Override
	public int updateTitle(MsgNews msgNews) {
		return msgNewsDao.updateTitle(msgNews);
	}

}
