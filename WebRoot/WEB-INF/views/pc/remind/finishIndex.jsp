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
<%@ include file="../base.jsp"%>
<script type="text/javascript" src="js/sysmanage/remind/finishRemind.js"></script>
</head>
<style>
h3 {
  margin-bottom: 5px;
  font-size: 18px;
  padding-left: 10px;
  border-left: 4px solid #0DA0FE; }
</style> 
<body>
	<div class="page-content-wrap">
		<div class="layui-form" id="table-list">
			<h3>日常提醒</h3>
			<table class="layui-table" id="finishRemindInfo" lay-filter="tableInfo"></table>
            </script>
		</div>
		<div class="layui-form" id="table-lists">
			<h3 style="margin-bottom: 0px;margin-top: 5px;">预约提醒</h3>
			<table class="layui-table" id="finishRemindInfos" lay-filter="tableInfo"></table>
            </script>
		</div>
	</div>
</body>
<input type="hidden" id="nextOneMonth" value="${nextOneMonth}">
<input type="hidden" id="nextTwoMonth" value="${nextTwoMonth}">
<input type="hidden" id="nextThreeMonth" value="${nextThreeMonth}">
<input type="hidden" id="userMap" value='${userMap}'>
</html>
