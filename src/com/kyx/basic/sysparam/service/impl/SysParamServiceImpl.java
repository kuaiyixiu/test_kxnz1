package com.kyx.basic.sysparam.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.kyx.basic.sysparam.mapper.SysParamMapper;
import com.kyx.basic.sysparam.model.SysParam;
import com.kyx.basic.sysparam.service.SysParamService;
import com.kyx.basic.util.RetInfo;
@Service("sysParamService")
public class SysParamServiceImpl implements SysParamService {
	
	@Resource
	private SysParamMapper sysParamMapper;

	@Override
	public List<SysParam> getListByShopId(Integer shopId) {
		return sysParamMapper.getListByShopId(shopId);
	}

	@Override
	public RetInfo saveParam(SysParam sysParam) {
		if(StringUtils.isBlank(sysParam.getParamName())){
			return new RetInfo("error","参数有误");
		}
		
		if(sysParam.getId() == null){
			sysParamMapper.insertSelective(sysParam);
		}else{
			sysParamMapper.updateByPrimaryKeySelective(sysParam);
		}
		return new RetInfo("success","保存成功");
	}

	@Override
	public String getValueByName(String paramName,Integer shopId) {
		SysParam sysParam =  sysParamMapper.getByName(paramName, shopId);
		if(sysParam == null){
			if(SysParamService.REPOSITORY_OA.equals(paramName)){ //库存是否启用审批
				return "0"; //否
			}else if(SysParamService.ENABLE_SMS_PUSH.equals(paramName)){ //短信发送是否启用
				return "0";
			}else if(SysParamService.ENABLE_WECHAT_PUSH.equals(paramName)){ //微信推送是否启用
				return "0";
			}
		}
		return sysParam.getParamValue();
		
	}

	@Override
	public RetInfo saveParam(List<SysParam> paramList,Integer userId,Integer shopId) {
		sysParamMapper.deleteByShopId(shopId);
		if(paramList != null){
			for(SysParam sp : paramList){
				sp.setShopId(shopId);
				sp.setCreateTime(new Date());
				sp.setOptTime(new Date());
				sp.setOptUser(userId);
			}
			sysParamMapper.insertBatches(paramList);
		}
		return new RetInfo("success","保存成功");
	}

}
