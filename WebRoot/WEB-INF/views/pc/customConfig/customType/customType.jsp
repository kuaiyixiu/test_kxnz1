<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>连途</title>
<%@ include file="../../base.jsp"%>
<script type="text/javascript" src="js/sysmanage/customType/customType.js"></script> 
</head>
<body>
	<div class="page-content-wrap">
		<table class="layui-table" id="customTypeInfo" lay-filter="tableInfo"></table>
	</div>
</body>
</html>
<input type="hidden" id="shopId" value='${ shopId }'>

<script type="text/html" id="barLine">
	<a class="layui-btn layui-btn-xs kxnz-btn-default" lay-event="edit" data-url="customType/editCustomTypeView.do" tabId="customTypeInfo" openw="700px;" openh="400px">修改</a>
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="customType/delData.do" tabId="customTypeInfo">删除</a>
</script>

<script type="text/html" id="toolbarTop">
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm kxnz-btn-default" tabId="customTypeInfo" data-url="customType/addCustomTypeView.do" openw="800px;" openh="600px" lay-event="addData" data-title="新增会员配置"><i class="layui-icon">&#xe608;</i>新增</button>
	</div>
</script>