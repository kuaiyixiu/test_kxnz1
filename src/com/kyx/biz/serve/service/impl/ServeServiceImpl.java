package com.kyx.biz.serve.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.custom.mapper.CustomMapper;
import com.kyx.biz.custom.model.Custom;
import com.kyx.biz.mealdt.service.MealDtService;
import com.kyx.biz.product.model.Product;
import com.kyx.biz.serve.mapper.ServeMapper;
import com.kyx.biz.serve.model.Serve;
import com.kyx.biz.serve.service.ServeService;

/**
 * 服务service
 * @author tyg
 *
 */
@Service("serveService")
public class ServeServiceImpl implements ServeService {

	@Resource
	private ServeMapper serveMapper;
	
	@Resource
	private CustomMapper customMapper;
	@Resource
	private MealDtService mealDtService;
	

	@Override
	public GrdData getServePage(Serve serve, Pager pager) {
		Page page=PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<Serve> list=serveMapper.getServeList(serve);
		return new GrdData(page.getTotal(),list);
	}


	@Override
	public List<Serve> getServeList(Serve serve) {
		return serveMapper.getServeList(serve);
	}


	@Override
	public int getByClassId(Integer classId) {
		
		return serveMapper.getByClassId(classId);
	}


	@Override
	public RetInfo saveData(Serve serve) {
		RetInfo retInfo;
		int count=0;
		if(serve.getId()==null){//新增
			//判断服务项目是否同名
			int c=serveMapper.getCountByServe(serve);
			if(c>0){//包含同名服务名称提示不能录入
				retInfo=new RetInfo("error","服务项目名称重复，请重新输入");
				return retInfo;
			}
			count=serveMapper.insertSelective(serve);
		}else{
			count=serveMapper.updateByPrimaryKeySelective(serve);
		}
		if(count>0)
			retInfo=new RetInfo("success","保存成功");
		else
			retInfo=new RetInfo("error","保存失败");
		return retInfo;
	}


	@Override
	public RetInfo delData(String ids) {
		RetInfo retInfo=chkCanDel(ids);
		if(retInfo.ERROR.equals(retInfo.getRetCode()))
			return retInfo;
		String[] idArr=ids.split(";");
		int count=0;
		for(int i=0;i<idArr.length;i++){
			Serve serve=new Serve();
			serve.setId(Integer.valueOf(idArr[i]));
			serve.setDel(1);
			count=count+serveMapper.updateByPrimaryKeySelective(serve);
					//.deleteByPrimaryKey(Integer.valueOf(idArr[i]));
		}
		if(count==idArr.length)
			retInfo=new RetInfo("success","删除成功");
		else
			retInfo=new RetInfo("error","删除失败");
		return retInfo;
	}

    /**
     * 校验是否可以删除
     * @param ids
     * @return
     */
	private RetInfo chkCanDel(String ids) {
		RetInfo retInfo=RetInfo.Success("");
		String[] idArr=ids.split(";");
		int count=0;
		Boolean canDel=true;
		for(int i=0;i<idArr.length;i++){
			count=mealDtService.getCountByTypeAndId(2,Integer.valueOf(idArr[i]));
			if(count>0){
				canDel=false;
				break;
			}
		}
		if(!canDel){
			retInfo=RetInfo.Error("套餐中存在该服务，不允许删除");
		}
		return retInfo;
	}


	@Override
	public Serve getById(Integer id) {
		
		return serveMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Serve> getServeListByIds(String ids) {
		List<Serve> serveList = new ArrayList<Serve>();
		if (StringUtils.isNotBlank(ids)){
			String[] idList = ids.split(",");
			for (String id : idList){ 
				serveList.add(serveMapper.selectByPrimaryKey(Integer.parseInt(id)));
			}
		}
		return serveList;
	}

	@Override
	public List<Serve> getListByClassId(Integer userId, Integer classId) {
		Serve serve=new Serve();
		serve.setUserId(userId);
		serve.setClassId(classId);
		return serveMapper.getListByClassId(serve);
	}


	@Override
	public Serve selectByName(String serveName, Integer shopId) {
		return serveMapper.selectByName(serveName, shopId);
	}


	@Override
	public List<Serve> getServeCustomPrice(Serve record) {
		List<Serve> serves =  null;
		if (record.getCustId() != null){
			Custom custom = customMapper.selectByPrimaryKey(record.getCustId() );
			if (custom != null && custom.getCustType() != null){
				record.setCustType(custom.getCustType());
			}
			serves =  serveMapper.getServeCustomPrice(record);
			for(Serve serve : serves){
				if (serve.getCustPrice() != null && !BigDecimal.ZERO.equals(serve.getCustPrice())){ //产品价格不为空切不为0
					serve.setPrice(serve.getCustPrice()); //把会员价格赋给产品价格
					serve.setIsCust(1);//是会员价格
				}
			}
		}else{
			serves = serveMapper.getServeList(record);	
		}
		return serves;
	}

}
