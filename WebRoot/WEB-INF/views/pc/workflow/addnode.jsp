<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	layui.use([ 'jquery', 'form', 'layer' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			
			var id = $("#id").val();
			if (!!id){
				if(id == data.field.preNode || id == data.field.nextNode ){
					layer.msg("当前节点不能作为前后节点", {icon: 2});
					return false;
				}
			}
			/*
			if(data.field.preNode == '' && data.field.nextNode == ''){
				layer.msg("前节点和后节点不能都为空", {icon: 2});
				return false;
			}
			
			if(data.field.preNode == data.field.nextNode){
				layer.msg("前节点和后节点不能相同", {icon: 2});
				return false;
			}*/
			
			$.ajax({
				type : "POST",
				url : "workflowTemplate/saveNode.do",
				data : data.field,
				dataType : "json",
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
		<input type="hidden" name="id" id="id" value="${node.id }" />
		<input type="hidden" name="templateId" id="templateId" value="${node.templateId }" />
		<div class="layui-form-item">
			<label class="layui-form-label">节点名称</label>
			<div class="layui-input-block">
				<input type="text" name="name" required lay-verify="required"
					placeholder="请输入节点名称" autocomplete="off" class="layui-input"
					value="${node.name}">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">序号</label>
			<div class="layui-input-block">
				<input type="text" name="iidno" required lay-verify="required|number"
					placeholder="请输入序号" autocomplete="off" class="layui-input"
					value="${node.iidno}">
			</div>
		</div>
	  	  <div class="layui-form-item">
		    <label class="layui-form-label">审核类型</label>
		    <div class="layui-input-block">
		      <select name="type" lay-search >
		       	<option value='1'  <c:if test="${node.type == 1 }">selected</c:if>>串签（单人审核通过）</option>
		       	<option value='2'  <c:if test="${node.type == 2 }">selected</c:if>>并签（多人全部审核通过）</option>
		       	<option value='3'  <c:if test="${node.type == 3 }">selected</c:if>>汇签（多人其中一人审核通过）</option>
		      </select>
		    </div>
	  	</div>
	  	
	  	<div class="layui-form-item">
		    <label class="layui-form-label">前节点</label>
		    <div class="layui-input-block">
		      <select lay-search name="preNode" id="preNode">
		       	<option value=''></option>
		       	<c:forEach items="${nodelist}" var="ns" >
		       		<option value='${ns.id }'  <c:if test="${node.preNode == ns.id }">selected</c:if>>${ns.name }</option>
		       	</c:forEach>
		      </select>
		    </div>
	  	</div>
	  	
	  	<div class="layui-form-item">
		    <label class="layui-form-label">后节点</label>
		    <div class="layui-input-block">
		      <select id="nextNode" lay-search name="nextNode" id="nextNode">
		       	<option value=''></option>
		       	<c:forEach items="${nodelist}" var="ns">
		       		<option value='${ns.id }' <c:if test="${node.nextNode == ns.id }">selected</c:if>>${ns.name }</option>
		       	</c:forEach>
		      </select>
		    </div>
	  	</div>
	  <div class="layui-form-item layui-form-text">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<textarea name="remark" placeholder="请输入备注"
					class="layui-textarea">${node.remark}</textarea>
			</div>
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


</script>
</html>

