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
<script type="text/javascript" src="js/sysmanage/appoint/appoint.js"></script>
</head>
<style>
.layui-table-cell {
    height: auto;
    line-height: 28px;
}
</style>
<body>
	<div class="page-content-wrap">
		<div class="layui-form" id="table-list">
			
			<table class="layui-table" id="appointInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>

</html>

<script type="text/html" id="barLine">                     
	<a class="layui-btn layui-btn-normal layui-btn-xs" data-url="remind/replyRemindView.do" lay-event="replyRemind" tabId="appointInfo" openw="700px" openh="800px">回复</a>
	<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="finish" data-url="appoint/carryOutAppoint.do" tabId="appointInfo" id="rechargeBtn">完成</a>
</script>

<script type="text/html" id="contentTpl">
	<span>联系方式：{{ d.cellphone }}</span><br/>
	<span>预约项目：{{ d.itemName }}</span><br/>
	<span>车辆信息：{{ d.carNumber }}</span>
</script>