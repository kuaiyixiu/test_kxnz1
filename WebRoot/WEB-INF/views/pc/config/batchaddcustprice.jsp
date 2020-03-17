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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
<script src="lib/admin/layui/layui.js" type="text/javascript"
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script>
</head>
<body>
	<form class="layui-form" action="" id="form" style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
			<label class="layui-form-label">折扣百分比</label>
			<div class="layui-input-inline">
				<input type="number" name="zc" id="zc" lay-verify="required" autocomplete="off" class="layui-input" value="0" />
			</div>
			 <div class="layui-form-mid layui-word-aux">%</div>
		</div>
			<fieldset class="layui-elem-field layui-field-title">
				<legend>
					<font size="2" color="red">说明：</font>
				</legend>
			</fieldset>
			<div style="margin-left: 10px;">
				<font size="2" color="red"><i class="layui-icon layui-icon-about"></i>&nbsp;&nbsp;折扣百分比应填会员价占原价的百分比，比如"10"表示"10%"，即当原价为￥100时会员价为￥10;</font>
			</div>

	</form>
</body>
</html>

