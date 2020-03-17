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
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		var laydate = layui.laydate;
		laydate.render({
			elem : '#birthday'
		});
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "menu/saveMenu.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('menuInfo');


						parent.layer.msg(result.retMsg, {
							icon : 1
						});
					} else {
						layer.msg(result.retMsg, {
							icon : 2
						});
					}
				}
			});
			return false;
		});

	});
</script>
</head>
<body>
	<form class="layui-form" action="" id="addMenuform"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">菜单名</label>
				<div class="layui-input-block">
					<input type="text" name="menuName" lay-verify="required"
						placeholder="请输入菜单名" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">标志符</label>
				<div class="layui-input-block">
					<input type="text" name="resKey" lay-verify="required"
						placeholder="请输入标志符" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">url地址</label>
				<div class="layui-input-block">
					<input type="text" name="resUrl" lay-verify="required"
						placeholder="请输入url地址" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<input type="text" name="description" lay-verify="required"
						placeholder="请输入备注" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">等级</label>
				<div class="layui-input-block">
					<input type="text" name="level" lay-verify="required"
						placeholder="请输入等级" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">图标</label>
				<div class="layui-input-block">
					<input type="text" name="icon"
						placeholder="请输入图标" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		<input type="hidden" name="parentId" value="${parentId}">
		<div class="layui-form-item">
			<div align="center">
				<button class="layui-btn" lay-submit lay-filter="formSubmitBtn">提交</button>
				<button class="layui-btn closeBtn" id="closeBtn" type="button">关闭</button>
			</div>
		</div>
	</form>
</body>
</html>

