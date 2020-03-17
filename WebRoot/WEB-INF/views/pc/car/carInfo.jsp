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
<script type="text/javascript" src="js/sysmanage/car/car.js"></script>
</head>
<style>
.showCarDetail{
	color: #2ea9df;
}
.addBtn{
	background-color: #1E9FFF;
}
</style>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">关键字</label>
						<div class="layui-input-inline">
							<input type="text" id="keyword"
								placeholder="车牌,车主,品牌,备注" autocomplete="off"
								class="layui-input">
						</div>
					</div>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
			</div>
		</form>
		
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="carInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-xs kxnz-btn-default" lay-event="edit" data-url="car/editCarView.do" tabId="carInfo" openw="700px;" openh="700px">修改</a>
			<a class="layui-btn layui-btn-xs addBtn kxnz-btn-default" lay-event="addCarMaintain" data-url="car/addCarMaintainView.do" tabId="carInfo" openw="500px;" openh="700px">添加保养</a>
            </script>
		</div>
	</div>
</body>

</html>
