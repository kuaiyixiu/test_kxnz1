package com.kyx.biz.repertory.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertory.mapper.RepertoryDtMapper;
import com.kyx.biz.repertory.model.RepertoryDt;
import com.kyx.biz.repertory.service.RepertoryDtService;
@Service("repertoryDtService")
public class RepertoryDtServiceImpl implements RepertoryDtService {
	@Resource
	private RepertoryDtMapper repertoryDtMapper;

	@Override
	public GrdData getInfo(RepertoryDt dt, Pager pager) {
		//Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<RepertoryDt> list=repertoryDtMapper.getInfo(dt);
				//.getRepertoryInfo(dt);
		return new GrdData(Long.valueOf(list.size()),list);
	}

	@Override
	public List<RepertoryDt> getList(RepertoryDt dt) {
		return repertoryDtMapper.getInfo(dt);
	}

	@Override
	public RetInfo saveData(RepertoryDt repertoryDt) {
		RetInfo retInfo;
		int count=0;
		if(repertoryDt.getId()!=null)
			count=repertoryDtMapper.updateByPrimaryKeySelective(repertoryDt);
		else{
			if(repertoryDt.getIidno()!=null){//退货添加产品
				RepertoryDt dt=new RepertoryDt();
				dt.setIidno(repertoryDt.getIidno());
				dt.setRepertory(repertoryDt.getRepertory());
				List<RepertoryDt> list=repertoryDtMapper.getInfo(dt);
				if(list.size()>0){
					retInfo=new RetInfo(RetInfo.ERROR,"不可重复添加退货产品");
					return retInfo;
				}
			}
			//首先查询当前入库单是否包含同类商品 如果包含累加 不包含在添加
			RepertoryDt qryVo=new RepertoryDt();
			qryVo.setRepertory(repertoryDt.getRepertory());
			qryVo.setProductId(repertoryDt.getProductId());
			List<RepertoryDt> list=repertoryDtMapper.getInfo(qryVo);
			if(list.size()>0){//存在同类商品 价格暂时忽略 如果同一批次进货价格不一样的话 认为是两个产品
				if(list.get(0).getInprice().doubleValue()==repertoryDt.getInprice().doubleValue()){
					RepertoryDt dt=new RepertoryDt();
					dt.setId(list.get(0).getId());
					dt.setInQuantity(list.get(0).getInQuantity()+repertoryDt.getInQuantity());//更改入库数量
					dt.setSum(new BigDecimal(dt.getInQuantity().intValue()*repertoryDt.getInprice().doubleValue()));
					repertoryDtMapper.updateByPrimaryKeySelective(dt);
					retInfo=new RetInfo("success","保存成功");
					return retInfo;
				}
			}
			count=repertoryDtMapper.insertSelective(repertoryDt);
		}
			
		if(count>0){
			retInfo=new RetInfo("success","保存成功");
		}else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

	@Override
	public RetInfo delData(Integer id) {
		RetInfo retInfo;
		int count=repertoryDtMapper.deleteByPrimaryKey(id);
		if(count>0)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}

	@Override
	public RepertoryDt getById(Integer id) {
		return repertoryDtMapper.selectByPrimaryKey(id);
	}

	@Override
	public GrdData getRepertoryInfo(RepertoryDt dt) {
		//Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<RepertoryDt> list=repertoryDtMapper.getRepertoryInfo(dt);
		return new GrdData(Long.valueOf(list.size()),list);
	}

	@Override
	public RetInfo saveBatch(List<RepertoryDt> dtList) {
		RetInfo retInfo=RetInfo.Success("保存成功");
		for(RepertoryDt dt:dtList){
			retInfo=this.saveData(dt);
			if(RetInfo.ERROR.equals(retInfo.getRetCode())){
				break;
			}
		}
//		int count=repertoryDtMapper.insertBatches(dtList);
//		if(count>0)
//			retInfo=new RetInfo("success","保存成功");
//		else
//			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}

}
