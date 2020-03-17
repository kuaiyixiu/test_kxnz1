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
<script type="text/javascript" src="js/sysmanage/remind/invitationIndex.js"></script>
</head>
<style>
h3 {
  margin-bottom: 5px;
  font-size: 18px;
  padding-left: 10px;
  border-left: 4px solid #0DA0FE; }
  
.layui-table-cell {
    height: auto;
    line-height: 28px;
}
</style>
<body>
	<div class="page-content-wrap">
		<div class="layui-form" id="table-list">
			<h3>服务邀约</h3>
			<table class="layui-table" id="serverInfo" lay-filter="tableInfo"></table>
		</div>
		<div class="layui-form" id="table-list">
			<h3>车辆邀约</h3>
			<table class="layui-table" id="carInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>
</html>
<script type="text/html" id="serverBarLine">                     
	<a class="layui-btn layui-btn-normal layui-btn-xs" data-url="send/server.do" lay-event="serverInvitation" tabId="serverInfo" openw="700px" openh="800px">回复</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="carryOutServer" data-url="remind/carryOutServer.do" tabId="serverInfo" id="rechargeBtn">完成</a>
</script>

<script type="text/html" id="carBarLine">
	<a class="layui-btn layui-btn-normal layui-btn-xs" data-url="send/carInvitation.do" lay-event="carInvitation" tabId="carInfo" openw="700px" openh="800px">回复</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="carryOutCar" data-url="remind/carryOutCar.do" tabId="carInfo" id="rechargeBtn">完成</a>
</script>