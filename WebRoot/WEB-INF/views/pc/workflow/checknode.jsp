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
			var $ = layui.jquery;
			
			if(!data.field.result){
				layer.msg("请选择审批结果", {icon: 2});
				return false;
			}
			
			if(data.field.result == '2' || data.field.result == '3'){
				if($.trim(data.field.remark) == ''){
					layer.msg("请输入审批意见", {icon: 2});
					return false;
				}
			}
			
			$.ajax({
				type : "POST",
				url : "workflowInstance/updateCheckNode.do",
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
		<input type="hidden" name="instanceNodeId" id="instanceNodeId" value="${node.id }" />
		<div class="layui-form-item">
			<label class="layui-form-label">审批名称</label>
			<div class="layui-input-block">
				<input type="text" readonly="readonly"
					 class="layui-input"
					value="${node.instanceName}">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">流程名称</label>
			<div class="layui-input-block">
				<input type="text" readonly="readonly"
					 class="layui-input"
					value="${node.name}">
			</div>
		</div>
	<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">流程备注</label>
			<div class="layui-input-block">
				<textarea  placeholder=""  readonly="readonly"
					class="layui-textarea">${node.remark}</textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">审批类型</label>
			<div class="layui-input-block">
				<input type="text"  readonly="readonly"
					 class="layui-input"
					value="${node.typeName}">
			</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">审批结果</label>
				<div class="layui-input-block">
					<input type="radio" name="result"  value="1" title="通过" checked="checked" /> 
					<input type="radio" name="result" value="2" title="不通过" />
					<input type="radio" name="result" value="3" title="退回上一步" />
				</div>
		</div>
	  <div class="layui-form-item layui-form-text">
			<label class="layui-form-label">审批意见</label>
			<div class="layui-input-block">
				<textarea name="remark" placeholder="请输入审批意见"
					class="layui-textarea"></textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block" align="center">
				<a class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn closeBtn" id="closeBtn">关闭</a>
			</div>
		</div>
	</form>
</body>
<script type="text/javascript">


</script>
</html>

