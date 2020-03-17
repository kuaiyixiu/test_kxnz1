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
<title>快易修系统</title>
<%@ include file="../base.jsp"%>
<script type="text/javascript">
	layui.use([ 'jquery', 'form', 'layer', 'laydate' ], function() {
		var form = layui.form;
		var layer = layui.layer;
		var $ = layui.jquery;
		form.render();
		//监听提交
		form.on('submit(formSubmitBtn)', function(data) {
			$.ajax({
				type : "POST",
				url : "shop/updateShops.do",
				data : data.field,
				dataType : "json",
				success : function(result) {
					if (result.retCode == 'success') {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);//关闭当前页  
						parent.layui.table.reload('shopInfo');
						
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
	<form class="layui-form" action="" id="editMenuform"
		style="width: 95%;height: 98%;margin-top: 1%;">
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">门店名</label>
				<div class="layui-input-block">
					<input type="text" name="shopName" lay-verify="required"
						placeholder="请输入门店名" autocomplete="off" class="layui-input"
						value="${shop.shopName}">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">门店地址</label>
				<div class="layui-input-block">
					<input type="text" name="shopAddress" 
						placeholder="请输入门店地址" autocomplete="off" class="layui-input"
						value="${shop.shopAddress}">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">联系方式</label>
				<div class="layui-input-block">
					<input type="text" name="shopTel" 
						placeholder="请输入联系方式" autocomplete="off" class="layui-input"
						 lay-verify="phone" value="${shop.shopTel}">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">人员数量</label>
				<div class="layui-input-block">
					<input type="text" name="userCount" 
						placeholder="请输入人员数量" autocomplete="off" class="layui-input"
						 lay-verify="required" value="${shop.userCount}">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div align="center">
				<button class="layui-btn kxnz-btn-default" lay-submit lay-filter="formSubmitBtn">提交</button>
				<button class="layui-btn closeBtn kxnz-btn-default" id="closeBtn" type="button">关闭</button>
			</div>
		</div>
		
		<input type="hidden" name="id" value="${shop.id}">
	</form>
</body>
</html>

