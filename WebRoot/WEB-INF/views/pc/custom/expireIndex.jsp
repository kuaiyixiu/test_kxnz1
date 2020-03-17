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
<script type="text/javascript" src="js/sysmanage/custom/expireIndex.js"></script>
<script type="text/javascript" src="js/sysmanage/car/carUtil.js"></script>
</head>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
				      <label class="layui-form-label">日期范围</label>
				      <div class="layui-input-inline">
				        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
				      </div>
				    </div>
					<%@ include file="../commonTime.jsp"%>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
			</div>
		</form>
		
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="expireTable" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            <div class="layui-btn-container">
            <button class="layui-btn layui-btn-sm" tabId="customInfo" data-url="custom/addCustomView.do" openw="900px;" openh="800px" id="addCustomBtn"><i class="layui-icon">&#xe608;</i>新增</button>
            <button class="layui-btn layui-btn-sm delBtn" tabId="customInfo" data-url="custom/delData.do" lay-event="delData"><i class="layui-icon">&#xe640;</i>删除</button>
            </div>
            </script>
			<script type="text/html" id="barLine">
            <a class="layui-btn layui-btn-normal layui-btn-xs" data-url="custom/detailView.do" lay-event="customDetail" tabId="customInfo" openw="700px;" openh="400px">详情</a>
			<a class="layui-btn layui-btn-xs" lay-event="recharge" tabId="customInfo" id="rechargeBtn">充值</a>
            <a class="layui-btn layui-btn-xs" lay-event="edit" data-url="custom/editCustomView.do" tabId="customInfo" openw="900px;" openh="600px">修改</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="custom/delData.do" tabId="customInfo">删除</a>
            </script>
		</div>
	</div>
</body>

<input type="hidden" id="customTypes" value='${customTypes}'>
<input type="hidden" id="shopListMap" value='${shopListMap}'>
<input type="hidden" id="nextOneMonth" value="${nextOneMonth}">
<input type="hidden" id="nextTwoMonth" value="${nextTwoMonth}">
<input type="hidden" id="nextThreeMonth" value="${nextThreeMonth}">
</html>
