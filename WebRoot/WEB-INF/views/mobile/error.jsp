<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html class="ui-page-login">
<base href="<%=basePath%>">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<link rel="stylesheet" type="text/css"href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"charset="utf-8"></script>
</head>

<body>
<div align="center">
<div style="width: 250px;height: 252px;" align="center"><i class="layui-icon layui-icon-404" style="font-size: 250px; color: #1E9FFF;"></i></div>
<div style="width: 250px;height: 252px;" align="center"><font color="red">页面登录信息过期，请退出后重新进入</font></div>
</div>
</body>
</html>

