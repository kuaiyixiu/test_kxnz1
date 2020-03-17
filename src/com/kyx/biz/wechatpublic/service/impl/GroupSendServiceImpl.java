package com.kyx.biz.wechatpublic.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialNews;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.BasicContant;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.wechat.config.MainConfiguration;
import com.kyx.biz.wechat.model.WechatConfig;
import com.kyx.biz.wechatpublic.mapper.GroupSendMapper;
import com.kyx.biz.wechatpublic.mapper.ImgResourceMapper;
import com.kyx.biz.wechatpublic.mapper.MsgNewsDao;
import com.kyx.biz.wechatpublic.model.GroupSend;
import com.kyx.biz.wechatpublic.model.ImgResource;
import com.kyx.biz.wechatpublic.model.MsgNews;
import com.kyx.biz.wechatpublic.service.GroupSendService;

@Service("groupSendService")
public class GroupSendServiceImpl implements GroupSendService {
	
	@Resource
	private GroupSendMapper groupSendMapper;
	
	@Resource
	private CustomMapper customMapper;
	
	@Resource
	private ImgResourceMapper imgResourceMapper;
	
	@Resource
	private MsgNewsDao msgNewsDao;

	@Override
	public RetInfo sendMsg(HttpServletRequest request,GroupSend groupSend) throws WxErrorException {
		if(groupSend.getSendUserType() == null || groupSend.getContentType() == null){
			return RetInfo.Error("发送对象有误");
		}	
		
		if(groupSend.getSendUserType() == 1 && StringUtils.isBlank(groupSend.getChooseOpenids())){
			return RetInfo.Error("请选择发送客户");
		}
		
		
		if(groupSend.getSendUserType() == 2 && StringUtils.isBlank(groupSend.getTagId())){
			return RetInfo.Error("请选择客户标签");
		}
		
		if(groupSend.getContentType() == 1 && StringUtils.isBlank(groupSend.getSendContent())){
			return RetInfo.Error("发送内容不能为空");
		}
		
		if(groupSend.getContentType() != 1 && ( groupSend.getMediaId() == null ||  "undefined".equals(groupSend.getMediaId()) )){
			return RetInfo.Error("请选择素材");
		}
		WechatConfig wechatConfig = (WechatConfig)request.getSession().getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
		groupSend.setAppId(wechatConfig.getAppid());
		groupSend.setCreateTime(new Date());
		groupSend.setSendStatus(0);//发送中
		
		WxMpMassSendResult massResult = null;
		WxMpService wxService=getWxMapService(request);
		RetInfo retInfo = null;
		if(groupSend.getSendUserType() == 1){//发送对象：客户OPENID
			String[] openIds = groupSend.getChooseOpenids().split(",");
				if(openIds.length == 1){
					Boolean single = false;
					if("all".equals(openIds)){
						Custom custom = new Custom();
						custom.setNotNullOpenId(1);
						List<Custom> list = customMapper.queryCustoms(custom);
						if(list.size() == 1)
							single = true;
						openIds = new String[list.size()];
						for(int i = 0 ;i < list.size() ; i++){
							openIds[i] = 	list.get(i).getOpenId();
						}
					}else{
						single = true;
					}
					if(single){
						WxMpKefuMessage massMessage = new WxMpKefuMessage();
						if(groupSend.getContentType() == 1){
							massMessage.setMsgType(WxConsts.MassMsgType.TEXT);//1文本
							massMessage.setContent(groupSend.getSendContent());
						}else if(groupSend.getContentType() == 2){
							massMessage.setMsgType(WxConsts.MassMsgType.IMAGE);//2图片 
							massMessage.setMediaId(groupSend.getMediaId()); 
						}else if(groupSend.getContentType() == 3){
							massMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);//3图文
							massMessage.setMediaId(groupSend.getMediaId()); 
						}
						massMessage.setToUser(openIds[0]);
						
						Boolean result  = wxService.getKefuService().sendKefuMessage(massMessage);
						if(result){
							retInfo =  RetInfo.Success("发送成功");
							groupSend.setSendStatus(1);
						}else{
							retInfo =  RetInfo.Error("发送失败");
							groupSend.setSendStatus(2);
						}
						groupSend.setUserCount(1);
					}else{
						WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
						if(groupSend.getContentType() == 1){
							massMessage.setMsgType(WxConsts.MassMsgType.TEXT);//1文本
							massMessage.setContent(groupSend.getSendContent());
						}else if(groupSend.getContentType() == 2){
							massMessage.setMsgType(WxConsts.MassMsgType.IMAGE);//2图片 
							massMessage.setMediaId(groupSend.getMediaId()); 
						}else if(groupSend.getContentType() == 3){
							massMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);//3图文
							massMessage.setMediaId(groupSend.getMediaId()); 
						}
						for(String openId : openIds){
							massMessage.getToUsers().add(openId);
						}
						massResult = wxService.getMassMessageService().massOpenIdsMessageSend(massMessage);
						groupSend.setUserCount(openIds.length);
					}
				}else{
					WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
					if(groupSend.getContentType() == 1){
						massMessage.setMsgType(WxConsts.MassMsgType.TEXT);//1文本
						massMessage.setContent(groupSend.getSendContent());
					}else if(groupSend.getContentType() == 2){
						massMessage.setMsgType(WxConsts.MassMsgType.IMAGE);//2图片 
						massMessage.setMediaId(groupSend.getMediaId()); 
					}else if(groupSend.getContentType() == 3){
						massMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);//3图文
						massMessage.setMediaId(groupSend.getMediaId()); 
					}
					
					for(String openId : openIds){
						massMessage.getToUsers().add(openId);
					}
					massResult = wxService.getMassMessageService().massOpenIdsMessageSend(massMessage);
					groupSend.setUserCount(openIds.length);
				}
			
		}else {//微信粉丝
			WxMpMassTagMessage massMessage = new WxMpMassTagMessage();
			if(groupSend.getContentType() == 1){
				massMessage.setMsgType(WxConsts.MassMsgType.TEXT);//1文本
				massMessage.setContent(groupSend.getSendContent());
			}else if(groupSend.getContentType() == 2){
				massMessage.setMsgType(WxConsts.MassMsgType.IMAGE);//2图片 
				massMessage.setMediaId(groupSend.getMediaId()); 
			}else if(groupSend.getContentType() == 3){
				massMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);//3图文
				massMessage.setMediaId(groupSend.getMediaId()); 
			}
			if("all".equals(groupSend.getTagId())){
				massMessage.setSendAll(true);	
			}else{
				massMessage.setTagId(Long.valueOf(groupSend.getTagId()));	
			}
			massResult = wxService.getMassMessageService() .massGroupMessageSend(massMessage);
		}
		if("0".equals(massResult.getErrorCode())){
			retInfo =  RetInfo.Success("发送成功");
		}else{
			retInfo =  RetInfo.Error("发送失败"+massResult.getErrorMsg());
		}
		if (RetInfo.SUCCESS.equals(retInfo.getRetCode())){
			groupSend.setSendStatus(1);
		}else{
			groupSend.setSendStatus(2);
		}
		groupSend.setErrorMsg(massResult.getErrorMsg());
		
		if(StringUtils.isNoneBlank(groupSend.getMediaId())){
			if(groupSend.getContentType() == 2){
				ImgResource imgResource = imgResourceMapper.selectByMediaId(groupSend.getMediaId());
				if(imgResource != null){
					groupSend.setSendContent(imgResource.getUrl());
				}
			}else if(groupSend.getContentType() == 3){
				List<MsgNews> News =  msgNewsDao.getByMediaId(groupSend.getMediaId());
				if(News != null && News.size() > 0){
					groupSend.setSendContent(News.get(0).getUrl());
				}
			}
			WxMpMaterialNews wxMpMaterialNewsMultiple = wxService.getMaterialService().materialNewsInfo(groupSend.getMediaId());
			String content = wxMpMaterialNewsMultiple.getArticles().get(0).getContent();
			groupSend.setSendContent(content);
		}
		
		
		groupSendMapper.insertSelective(groupSend);
		
		return retInfo;
	}
	
	private WxMpService getWxMapService(HttpServletRequest request) {
		WechatConfig wechatConfig = (WechatConfig)request.getSession().getAttribute(BasicContant.SHOP_WECHAT_CONFIG_SESSION);
		MainConfiguration mainConfig = new MainConfiguration(wechatConfig.getAppid(), wechatConfig.getAppsecret(),wechatConfig.getToken(), wechatConfig.getEncodingaeskey());
		WxMpService wxMpService = mainConfig.wxMpService();
		return wxMpService;
	}

	@Override
	public int getAllFansCount(HttpServletRequest request) throws WxErrorException {
		WxMpService wxService=getWxMapService(request);
		int count = 0;
		WxMpUserList list = wxService.getUserService().userList(null);
		count += list.getCount();
		String nextOpenId =list.getNextOpenid();
		while (StringUtils.isNotBlank(nextOpenId)) {
			list = wxService.getUserService().userList(nextOpenId);
			count += list.getCount();
			nextOpenId =list.getNextOpenid();
		}
		return count;
	}

	@Override
	public GrdData getList(GroupSend groupSend, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<GroupSend> list=groupSendMapper.getList(groupSend);
		return new GrdData(page.getTotal(),list);
	}

}
