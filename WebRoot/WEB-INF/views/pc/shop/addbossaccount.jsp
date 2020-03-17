<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
</head>
<body>
	<form class="layui-form" action="" id="form"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font> 手机号</label>
			<div class="layui-input-block">
				<input type="text" name="userPhone" id="userPhone" lay-verify="phone"
					placeholder="请输入手机号" autocomplete="off" class="layui-input"
					value="${user.userPhone }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><font color="red">*</font> 姓名</label>
			<div class="layui-input-block">
				<input type="text" name="userRealname" id="userRealname" lay-verify="required"
					placeholder="请输入姓名" autocomplete="off" class="layui-input"
					value="${user.userRealname }">
			</div>
		</div>
	</form>
</body>
</html>

