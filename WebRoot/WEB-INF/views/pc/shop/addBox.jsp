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
<%@ include file="../base.jsp"%>
</head>
<body>
	<form class="layui-form" id="optForm" style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="boxModule">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label"><font color="red">*</font>名称</label>
					<div class="layui-input-block">
						<input type="text" data-id="boxName" lay-verify="required" placeholder="请输入盒子名称" autocomplete="off" class="layui-input box">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label"><font color="red">*</font>标识码</label>
					<div class="layui-input-block">
						<input type="text" data-id="identifier" placeholder="请输入盒子标识码" autocomplete="off" class="layui-input box">
					</div>
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label"> 备注</label>
				<div class="layui-input-block">
					<input type="text" data-id="remark" placeholder="请输入备注" autocomplete="off" class="layui-input box">
				</div>
			</div>
		</div>
		
		<div class="boxModule">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label"><font color="red">*</font>名称</label>
					<div class="layui-input-block">
						<input type="text" data-id="boxName" lay-verify="required" placeholder="请输入盒子名称" autocomplete="off" class="layui-input box">
					</div>
				</div>
				
				<div class="layui-inline">
					<label class="layui-form-label"><font color="red">*</font>标识码</label>
					<div class="layui-input-block">
						<input type="text" data-id="identifier" placeholder="请输入盒子标识码" autocomplete="off" class="layui-input box">
					</div>
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label"> 备注</label>
				<div class="layui-input-block">
					<input type="text" data-id="remark" placeholder="请输入备注" autocomplete="off" class="layui-input box">
				</div>
			</div>
		</div>
		<input type="hidden" id="shopId" value="${id}">
	</form>
</body>
</html>


