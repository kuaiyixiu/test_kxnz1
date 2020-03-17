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
	<div style="width: 90%;height: 98%;margin-top: 1%;margin-left: 2px;">
		<div class="layui-fluid">
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">作废说明</label>
				<div class="layui-input-block">
					<textarea class="layui-textarea layui-disabled" disabled="disabled">${wechatCardRecord.voidRemark }</textarea>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

