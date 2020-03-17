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
<script type="text/javascript" src="js/sysmanage/car/ordersDetail.js"></script>
</head>
<style>
h3 {
  margin-bottom: 20px;
  font-size: 18px;
  padding-left: 10px;
  border-left: 4px solid #0DA0FE; 
}
</style>
<body>
	<form class="layui-form">
		<h3>服务记录</h3>
		<table class="layui-table" id="serversInfo"></table>
		<h3>产品记录</h3>
		<table class="layui-table" id="productInfo"></table>
		
		<div class="layui-form-item">
			<div align="center">
				<button class="layui-btn closeBtn" id="closeBtn" type="button">关闭</button>
			</div>
		</div>
	</form>
</body>

<input type="hidden" id="orderId" value='${ orderId }'>
</html>




