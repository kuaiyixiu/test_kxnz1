package com.kyx.biz.workflow.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kyx.basic.db.Dbs;
import com.kyx.basic.user.service.UserService;
import com.kyx.basic.util.GrdData;
import com.kyx.basic.util.Pager;
import com.kyx.basic.util.RetInfo;
import com.kyx.biz.repertory.mapper.RepertoryMapper;
import com.kyx.biz.repertory.model.Repertory;
import com.kyx.biz.workflow.mapper.WorkflowCheckUserMapper;
import com.kyx.biz.workflow.mapper.WorkflowInstanceMapper;
import com.kyx.biz.workflow.mapper.WorkflowInstanceNodeMapper;
import com.kyx.biz.workflow.mapper.WorkflowNodeMapper;
import com.kyx.biz.workflow.mapper.WorkflowNodeUserMapper;
import com.kyx.biz.workflow.model.WorkflowCheckUser;
import com.kyx.biz.workflow.model.WorkflowInstance;
import com.kyx.biz.workflow.model.WorkflowInstanceNode;
import com.kyx.biz.workflow.model.WorkflowNode;
import com.kyx.biz.workflow.service.WorkflowInstanceService;

@Service("workflowInstanceService")
public class WorkflowInstanceServiceImpl implements WorkflowInstanceService {
	
	@Resource
	private WorkflowInstanceMapper workflowInstanceMapper;
	
	@Resource
	private WorkflowInstanceNodeMapper workflowInstanceNodeMapper;
	
	@Resource
	private WorkflowNodeMapper workflowNodeMapper;
	
	@Resource
	private WorkflowNodeUserMapper workflowNodeUserMapper;
	
	@Resource
	private WorkflowCheckUserMapper workflowCheckUserMapper;

	@Resource
	private UserService userService;
	
	@Resource
	private RepertoryMapper repertoryMapper;
	
	@Override
	public GrdData getInstanceList(WorkflowInstance workflowInstance, Pager pager,Integer shopId) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<WorkflowInstance> list = workflowInstanceMapper.getList(workflowInstance);
		Dbs.setDbName(Dbs.getMainDbName());
		Map<Integer, String> userMap = userService.getUserMap(shopId);
		for(WorkflowInstance instance: list){
			instance.setCreateUserName(userMap.get(instance.getCreateUser()));
		}
		return new GrdData(page.getTotal(), list);
	}

	@Override
	public RetInfo saveInstance(WorkflowInstance workflowInstance) {
		int count = workflowInstanceMapper.insertSelective(workflowInstance);
		if (count > 0)
			return new RetInfo("success", "保存成功");
		else
			return new RetInfo("error", "保存失败");
	}

	@Override
	public RetInfo updateInstance(WorkflowInstance workflowInstance) {
		int count = workflowInstanceMapper.updateByPrimaryKeySelective(workflowInstance);
		if (count > 0)
			return new RetInfo("success", "修改成功");
		else
			return new RetInfo("error", "修改失败");
	}

	@Override
	public GrdData getCheckList(WorkflowInstanceNode workflowInstanceNode, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<WorkflowInstanceNode> list =  workflowInstanceNodeMapper.getCheckList(workflowInstanceNode);
		return new GrdData(page.getTotal(), list);
	}

	@Override
	public WorkflowInstanceNode selectNodeById(Integer id) {
		WorkflowInstanceNode node =  workflowInstanceNodeMapper.selectById(id);
		if(node.getType() == 1){
			node.setTypeName("串签（单人审核）");
		}else if(node.getType() == 2){
			node.setTypeName("并签（多人且审核）");
		}else if(node.getType() == 3){
			node.setTypeName("汇签（多人或审核）");
		}
		
		return node;
	}

	
	@Override
	public RetInfo updateCheckNode(WorkflowCheckUser workflowCheckUser) {
		if(workflowCheckUser.getInstanceNodeId()==null || workflowCheckUser.getOptUser() == null || workflowCheckUser.getResult() == null){
			return new RetInfo("error", "审核信息不全");
		}
		
		Integer count = workflowCheckUserMapper.selectCountByUserAndNode(workflowCheckUser.getInstanceNodeId(), workflowCheckUser.getOptUser());
		if(count > 0){
			return new RetInfo("error", "已经审核过，不能重复审核");
		}
		
		WorkflowInstanceNode instanceNode = workflowInstanceNodeMapper.selectById(workflowCheckUser.getInstanceNodeId()); //获取当前审批节点的信息
		

		count = workflowNodeUserMapper.selectCountByUserAndNode(instanceNode.getNodeId(), workflowCheckUser.getOptUser());
		if(count < 1){
			return new RetInfo("error", "该用户没有审核权限");
		}
		
		workflowCheckUser.setOptTime(new Date()); //操作时间
		workflowCheckUser.setInstanceId(instanceNode.getInstanceId()); //写入主流程ID
		workflowCheckUserMapper.insertSelective(workflowCheckUser);
		
		//更新当前节点信息
		WorkflowInstanceNode currNode = new WorkflowInstanceNode();
		currNode.setId(instanceNode.getId());
		currNode.setStatus(transResult2Status(workflowCheckUser.getResult()));
		workflowInstanceNodeMapper.updateByPrimaryKeySelective(currNode);
		
		if(workflowCheckUser.getResult() == 1){ //审核通过
			if(instanceNode.getType() == 1 | instanceNode.getType() == 3){ //顺签或者汇签
				 handleNextNode(instanceNode);
			}else if (instanceNode.getType() == 2){//并签  （必须全部同意）
				 List<Integer> nodeUserList =  workflowNodeUserMapper.selectUserIdByNodeId(instanceNode.getNodeId());
				 List<WorkflowCheckUser> checkUserList =  workflowCheckUserMapper.selectUserListByInstanceNodeId(instanceNode.getId());
				 Boolean allChecked = true;
				 for(Integer userId :  nodeUserList){
					 WorkflowCheckUser checkUser = findCheckUserById(checkUserList, userId);
					 if(checkUser == null || checkUser.getResult() != 1){//判断当前节点其他用户是否全部审核通过
						 allChecked = false;
						 break;
					 }
				 }
				 
				 if(allChecked){ //节点全部用户审核通过
					 handleNextNode(instanceNode);
				 }
			}
		}else if(workflowCheckUser.getResult() == 2){ //审核不通过
			//更新主流程信息
			WorkflowInstance workflowInstance = new WorkflowInstance();
			workflowInstance.setId(instanceNode.getInstanceId());
			workflowInstance.setStatus(3);//3终止
			workflowInstance.setEndTime(new Date());
			workflowInstanceMapper.updateByPrimaryKeySelective(workflowInstance);
			
			//拒绝订单
			refuseSlip(instanceNode.getInstanceId());
		}else if (workflowCheckUser.getResult() == 3){ //退回上一步
			handlePreNode(instanceNode);
		}
		return new RetInfo("success", "修改成功");
	}
	
	
	/**
	 * 通过ID找checkuser
	 * @param checkUserList
	 * @param userId
	 * @return
	 */
	private WorkflowCheckUser findCheckUserById(List<WorkflowCheckUser> checkUserList,Integer userId){
		for(WorkflowCheckUser user : checkUserList){
			if(user.getOptUser().equals(userId)){
				return user;
			}
		}
		return null;
	}
	
	/**
	 * 当前节点已审核通过，处理下一个节点
	 * @param instanceNode
	 */
	private void handleNextNode(WorkflowInstanceNode instanceNode){
		WorkflowNode workflowNode =  workflowNodeMapper.selectByPrimaryKey(instanceNode.getNodeId());
		//查询是否有下一个节点
		if(workflowNode.getNextNode() != null){ //有下一个节点
			//生成下一个节点审批
			Integer maxIidno = workflowInstanceNodeMapper.getMaxIidnoByInstanceId(instanceNode.getInstanceId());
			WorkflowNode nextNode = workflowNodeMapper.selectByPrimaryKey(workflowNode.getNextNode());
			WorkflowInstanceNode nextInstanceNode = new WorkflowInstanceNode();
			nextInstanceNode.setInstanceId(instanceNode.getInstanceId());
			nextInstanceNode.setNodeId(nextNode.getId());
			nextInstanceNode.setName(nextNode.getName());
			nextInstanceNode.setStatus(1);//进行中
			nextInstanceNode.setRemark(nextNode.getRemark());
			nextInstanceNode.setIidno(++maxIidno);
			workflowInstanceNodeMapper.insertSelective(nextInstanceNode);
			
			//更新主流程信息
			WorkflowInstance workflowInstance = new WorkflowInstance();
			workflowInstance.setId(instanceNode.getInstanceId());
			workflowInstance.setCurrentInstanceNodeId(nextInstanceNode.getId());
			workflowInstanceMapper.updateByPrimaryKeySelective(workflowInstance);
			
		}else{//没有下一个节点了 审批全部完成
			WorkflowInstance workflowInstance = new WorkflowInstance();
			workflowInstance.setId(instanceNode.getInstanceId());
			workflowInstance.setStatus(2); //已完结
			workflowInstance.setEndTime(new Date());
			workflowInstanceMapper.updateByPrimaryKeySelective(workflowInstance);
			//拒绝订单
			submitSlip(instanceNode.getInstanceId());

		}
	 }
	
	/**
	 * 当前节点已退回，处理上一个节点
	 * @param instanceNode
	 */
	private void handlePreNode(WorkflowInstanceNode instanceNode){
		WorkflowNode workflowNode =  workflowNodeMapper.selectByPrimaryKey(instanceNode.getNodeId());
		//查询是否有下一个节点
		if(workflowNode.getPreNode() != null){ //有下一个节点
			//生成下一个节点审批
			Integer maxIidno = workflowInstanceNodeMapper.getMaxIidnoByInstanceId(instanceNode.getInstanceId());
			WorkflowNode preNode = workflowNodeMapper.selectByPrimaryKey(workflowNode.getPreNode());
			WorkflowInstanceNode preInstanceNode = new WorkflowInstanceNode();
			preInstanceNode.setInstanceId(instanceNode.getInstanceId());
			preInstanceNode.setNodeId(preNode.getId());
			preInstanceNode.setName(preNode.getName());
			preInstanceNode.setStatus(1);//进行中
			preInstanceNode.setRemark(preNode.getRemark());
			preInstanceNode.setIidno(++maxIidno);
			workflowInstanceNodeMapper.insertSelective(preInstanceNode);
			
			//更新主流程信息
			WorkflowInstance workflowInstance = new WorkflowInstance();
			workflowInstance.setId(instanceNode.getInstanceId());
			workflowInstance.setCurrentInstanceNodeId(preInstanceNode.getId());
			workflowInstanceMapper.updateByPrimaryKeySelective(workflowInstance);
			
		}else{ //没有下一个节点 就把状态改成终止
			WorkflowInstance workflowInstance = new WorkflowInstance();
			workflowInstance.setId(instanceNode.getInstanceId());
			workflowInstance.setStatus(3);//3终止
			workflowInstance.setEndTime(new Date());
			workflowInstanceMapper.updateByPrimaryKeySelective(workflowInstance);
			//拒绝订单
			refuseSlip(instanceNode.getInstanceId());
		}
	 }
	
	/**
	 * 把用户审核result字段值转成审核状态status
	 * @param result
	 * @return
	 */
	private Integer transResult2Status(Integer result){
		switch (result) {
		case 1: //通过
			return 2;
		case 2://未通过
			return 3;
		case 3://退回上一步
			return 4;
		default:
			return -1;
		}
	}

	@Override
	public GrdData getInstanceNodeList(WorkflowInstanceNode workflowInstanceNode, Pager pager) {
		Page page = PageHelper.startPage(pager.getPage(), pager.getLimit());
		List<WorkflowInstanceNode> list = workflowInstanceNodeMapper.getList(workflowInstanceNode);
		return new GrdData(page.getTotal(), list);
	}

	@Override
	public List<WorkflowCheckUser> selectUserListByInstanceNodeId(Integer shopId,Integer instanceNodeId) {
		List<WorkflowCheckUser> userList = workflowCheckUserMapper.selectUserListByInstanceNodeId(instanceNodeId);
		Dbs.setDbName(Dbs.getMainDbName());
		Map<Integer, String> userMap = userService.getUserMap(shopId);
		for(WorkflowCheckUser user : userList){
			if(user.getResult() == 1){
				user.setResultStr("审核通过");
			}else if(user.getResult() == 2){
				user.setResultStr("审核不通过");
			}else if(user.getResult() == 3){
				user.setResultStr("退回上一步");
			}
			user.setOptUserName(userMap.get(user.getOptUser()));
		}
		return userList;
	}

	@Override
	public RetInfo createInstance(WorkflowInstance workflowInstance) {
		if(workflowInstance.getTemplateId() == null || workflowInstance.getCreateUser() == null){
			return new RetInfo("error", "审批信息不全");
		}
		workflowInstance.setStatus(1);//进行中
		saveInstance(workflowInstance);
		WorkflowNode firstNode =  workflowNodeMapper.selectFirstNodeByTemplateId(workflowInstance.getTemplateId());
		WorkflowInstanceNode preInstanceNode = new WorkflowInstanceNode();
		preInstanceNode.setInstanceId(workflowInstance.getId());
		preInstanceNode.setNodeId(firstNode.getId());
		preInstanceNode.setName(firstNode.getName());
		preInstanceNode.setStatus(1);//进行中
		preInstanceNode.setRemark(firstNode.getRemark());
		preInstanceNode.setIidno(1);
		workflowInstanceNodeMapper.insertSelective(preInstanceNode);
		
		workflowInstance.setCurrentInstanceNodeId(preInstanceNode.getId()); //更新当前节点的ID
		workflowInstanceMapper.updateByPrimaryKeySelective(workflowInstance);
		return new RetInfo("success", "修改成功");
	}

	@Override
	public RetInfo backInstance(Integer ids) {
		WorkflowInstance workflowInstance =  workflowInstanceMapper.selectByPrimaryKey(ids);
		WorkflowInstanceNode instanceNode =  workflowInstanceNodeMapper.selectByPrimaryKey(workflowInstance.getCurrentInstanceNodeId());
		WorkflowNode node =  workflowNodeMapper.selectByPrimaryKey(instanceNode.getNodeId());
		if (node.getPreNode() == null){ //此节点是第一个节点才允许撤回
			instanceNode.setStatus(4);
			workflowInstanceNodeMapper.updateByPrimaryKeySelective(instanceNode);
		}else{ 
			return new RetInfo("error", "该审批正在进行中，无法撤回");
		}
		return new RetInfo("success", "撤回成功");
	}

	@Override
	public void submitSlip(Integer instanceId) {
		//更新订单相关信息（已审批）
		WorkflowInstance instance = workflowInstanceMapper.selectByPrimaryKey(instanceId);
		if(instance.getSlipId() != null){
			if(instance.getSlipType() == 1 || instance.getSlipType() == 2){//入库 退货
				Repertory repertory = new Repertory();
				repertory.setId(instance.getSlipId());
				repertory.setRepertoryStatus(0);// 0临时单
				repertoryMapper.updateByPrimaryKeySelective(repertory);
				
			}
		}
	}

	@Override
	public void refuseSlip(Integer instanceId) {
		//更新订单相关信息（已审批）
		WorkflowInstance instance = workflowInstanceMapper.selectByPrimaryKey(instanceId);
		if(instance.getSlipId() != null){
			if(instance.getSlipType() == 1 || instance.getSlipType() == 2){//入库 退货
				Repertory repertory = new Repertory();
				repertory.setId(instance.getSlipId());
				repertory.setRepertoryStatus(5);// 5审批完成
				repertoryMapper.updateByPrimaryKeySelective(repertory);
			}
		}
	}

	@Override
	public WorkflowInstance selectById(Integer id,int shopId) {
		WorkflowInstance workflowInstance =  workflowInstanceMapper.selectByPrimaryKey(id);
		Dbs.setDbName(Dbs.getMainDbName());
		Map<Integer, String> userMap = userService.getUserMap(shopId);
		workflowInstance.setCreateUserName(userMap.get(workflowInstance.getCreateUser()));
		return workflowInstance;
	}



}
