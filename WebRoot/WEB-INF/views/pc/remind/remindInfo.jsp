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
<script type="text/javascript" src="js/sysmanage/remind/remind.js"></script>
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
			<h3>今日提醒</h3>
			<table class="layui-table" id="remindInfo" lay-filter="tableInfo"></table>
            </script>
		</div>
		<div class="layui-form" id="table-lists">
			<h3 style="margin-bottom: 0px;margin-top: 5px;">待办提醒</h3>
			<table class="layui-table" id="remindInfos" lay-filter="tableInfo"></table>
            </script>
		</div>
	</div>
</body>
<input type="hidden" id="userMap" value='${userMap}'>

</html>

<script type="text/html" id="toolbarTop">
	<div class="layui-btn-container">
		<button class="layui-btn layui-btn-sm kxnz-btn-default" tabId="remindInfo" data-url="remind/addRemindView.do" openw="900px;" openh="800px" lay-event="addRemind"><i class="layui-icon">&#xe608;</i>添加提醒</button>
	</div>
</script>
<script type="text/html" id="barLine">
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="carryOutRemind" data-url="remind/carryOutRemind.do" tabId="remindInfo">完成</a>
</script>

<script type="text/html" id="barLines">
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="carryOutRemind" data-url="remind/carryOutRemind.do" tabId="remindInfos">完成</a>
</script>