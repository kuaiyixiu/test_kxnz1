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
</head>
<body>
	<form class="layui-form" action="" id="editMenuform"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font> 门店名</label>
			<div class="layui-input-block">
				<input type="text" name="shopName" id="shopName" lay-verify="required"
					placeholder="请输入门店名" autocomplete="off" class="layui-input"
					value="${shops.shopName }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font> 门店电话</label>
			<div class="layui-input-block">
				<input type="text" name="shopTel" id="shopTel" lay-verify="required" placeholder="请输入门店电话"
					autocomplete="off" class="layui-input" value="${shops.shopTel }">
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label"><font color="red">*</font> 门店地址</label>
			<div class="layui-input-block">
				<textarea name="shopAddress" id="shopAddress" placeholder="请输入门店地址"
					class="layui-textarea">${shops.shopAddress }</textarea>
			</div>
		</div>
	  	<div class="layui-form-item">
	    	<label class="layui-form-label">夜间服务</label>
	    	<div class="layui-input-block">
	      		<input id="openNightServeFlag" type="checkbox" name="openNightServeFlag" value="0" lay-skin="switch" lay-filfter="openFlag" lay-text="开启|关闭">
	    	</div>
	  	</div>		

	</form>
</body>
</html>


