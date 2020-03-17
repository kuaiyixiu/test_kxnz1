<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer','laytpl','element' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			var flag = true;
			var nodeList = new Array();
			$(".nodeform").each(function(index,item){
				var name = layui.$(item).find("[name='name']").val();
				var type = layui.$(item).find("[name='type']").val();
				var remark = layui.$(item).find("[name='remark']").val();
				if (!name){
					layer.msg("第"+(++index)+"个节点名称为空", {icon: 2});
					flag = false;
					return false;
				}
				var data = {};
				data.name = name;
				data.type = type;
				data.remark = remark;
				nodeList.push(data);
			});
			
			if (!flag){
				return false;
			}
			/*
			var id = $("#id").val();
			if (!!id){
				if(id == data.field.preNode || id == data.field.nextNode ){
					layer.msg("当前节点不能作为前后节点", {icon: 2});
					return false;
				}
			}*/
			/*
			if(data.field.preNode == '' && data.field.nextNode == ''){
				layer.msg("前节点和后节点不能都为空", {icon: 2});
				return false;
			}
			
			if(data.field.preNode == data.field.nextNode){
				layer.msg("前节点和后节点不能相同", {icon: 2});
				return false;
			}*/
			var templateId = $("#templateId").val();
			$.ajax({
				type : "POST",
				url : "workflowTemplate/saveNodeList.do?templateId="+templateId,
				data : JSON.stringify(nodeList),
				dataType : "json",
				contentType:"application/json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('ordersTable');
						parent.layer.msg(result.retMsg, {icon : 1});
					} else {
						layer.msg(result.retMsg, {icon : 2});
					}
				}
			});
			return false;
		});

	});
</script>
</head>
<body>
	<form class="layui-form" action="" id="form"  style="width: 95%;height: 98%;margin-top: 1%;">
		<input type="hidden" name="templateId" id="templateId" value="${templateId }" />
		<div id="nodeList">
			  <c:if test="${fn:length(nodeList) == 0}">
					<div class="nodeform">
						<div class="layui-form-item">
							<label class="layui-form-label">节点名称</label>
							<div class="layui-input-block">
								<input type="text" name="name" required lay-verify="required" placeholder="请输入节点名称" autocomplete="off" class="layui-input" value="">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">审核类型</label>
							<div class="layui-input-block">
								<select name="type" lay-search>
									<option value='1' >串签（单人审核通过）</option>
									<option value='2' >并签（多人全部审核通过）</option>
									<option value='3' >汇签（多人其中一人审核通过）</option>
								</select>
							</div>
						</div>
	
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label">备注</label>
							<div class="layui-input-block">
								<textarea name="remark" placeholder="请输入备注" class="layui-textarea"></textarea>
							</div>
						</div>
						<a style="float:right; margin :5px 50px 5px 0px;" title="添加" class="layui-btn layui-btn-sm addCarbutton" onclick="addNodepl(this);"> <i class="layui-icon">&#xe654;</i>
						</a>
					</div>
				</c:if>
			  <c:if test="${fn:length(nodeList) > 0}">
			  	 <c:forEach items="${nodeList }" var="node" varStatus="status">
			  		<div class="nodeform">
						<div class="layui-form-item">
							<label class="layui-form-label">节点名称</label>
							<div class="layui-input-block">
								<input type="text" name="name" required lay-verify="required" placeholder="请输入节点名称" autocomplete="off" class="layui-input" value="${node.name}">
							</div>
						</div>
						<div class="layui-form-item">
							<label class="layui-form-label">审核类型</label>
							<div class="layui-input-block">
								<select name="type" lay-search>
									<option value='1' <c:if test="${node.type == 1 }">selected</c:if>>串签（单人审核通过）</option>
									<option value='2' <c:if test="${node.type == 2 }">selected</c:if>>并签（多人全部审核通过）</option>
									<option value='3' <c:if test="${node.type == 3 }">selected</c:if>>汇签（多人其中一人审核通过）</option>
								</select>
							</div>
						</div>
	
						<div class="layui-form-item layui-form-text">
							<label class="layui-form-label">备注</label>
							<div class="layui-input-block">
								<textarea name="remark" placeholder="请输入备注" class="layui-textarea">${node.remark}</textarea>
							</div>
						</div>
						<c:if test="${status.count > 0}">
							<a style="float:right;margin :5px 50px 5px 0px;" title="删除" class="layui-btn layui-btn-sm addCarbutton" onclick="delNodepl(this);" > <i class="layui-icon">&#xe640;</i></a>
						</c:if>
							<a style="float:right;margin :5px 50px 5px 0px;" title="添加" class="layui-btn layui-btn-sm addCarbutton" onclick="addNodepl(this);" > <i class="layui-icon">&#xe654;</i></a>	
							
					</div>
			  	 </c:forEach>
			  </c:if>
		

			</div>
			<div class="layui-form-item">
				<div class="layui-input-block" align="center">
					<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</button>
					<button class="layui-btn closeBtn" id="closeBtn" type="button">关闭</button>
				</div>
			</div>
	</form>
</body>
<script type="text/javascript">
function addNodepl(obj) {
	var nodeHtml = nodeTpl.innerHTML;
	layui.$(obj).parent().after(nodeHtml);
	var index = parent.layer.getFrameIndex(window.name); 
	parent.layer.iframeAuto(index);
	layui.form.render();
	return false;
}

function delNodepl(obj){
	layui.$(obj).parent().remove();
	var index = parent.layer.getFrameIndex(window.name); 
	parent.layer.iframeAuto(index);
	layui.form.render();
	return false;
}
</script>

<script id="nodeTpl" type="text/html">
<div  class="nodeform">
<div class="layui-form-item">
	<label class="layui-form-label">节点名称</label>
	<div class="layui-input-block">
		<input type="text" name="name" required lay-verify="required" placeholder="请输入节点名称" autocomplete="off" class="layui-input" value="${node.name}">
	</div>
</div>

<div class="layui-form-item">
	<label class="layui-form-label">审核类型</label>
	<div class="layui-input-block">
		<select name="type" lay-search>
			<option value='1' <c:if test="${node.type == 1 }">selected</c:if>>串签（单人审核通过）</option>
			<option value='2' <c:if test="${node.type == 2 }">selected</c:if>>并签（多人全部审核通过）</option>
			<option value='3' <c:if test="${node.type == 3 }">selected</c:if>>汇签（多人其中一人审核通过）</option>
		</select>
	</div>
</div>

<div class="layui-form-item layui-form-text">
	<label class="layui-form-label">备注</label>
	<div class="layui-input-block">
		<textarea name="remark" placeholder="请输入备注" class="layui-textarea">${node.remark}</textarea>
	</div>
</div>
	<a style="float:right;margin :5px 50px 5px 0px;" title="删除" class="layui-btn layui-btn-sm addCarbutton" onclick="delNodepl(this);" > <i class="layui-icon">&#xe640;</i></a>
	<a style="float:right;margin :5px 50px 5px 0px;" title="添加" class="layui-btn layui-btn-sm addCarbutton" onclick="addNodepl(this);" > <i class="layui-icon">&#xe654;</i></a>
</div>
</script>
</html>

