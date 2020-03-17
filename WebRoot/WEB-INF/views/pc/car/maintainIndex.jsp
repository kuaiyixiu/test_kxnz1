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
<script type="text/javascript" src="js/sysmanage/car/maintainIndex.js"></script>
<script type="text/javascript" src="js/sysmanage/car/carUtil.js"></script>
</head>
<style type="text/css">
        .layui-table-cell {
            height: auto;
            line-height: 28px;
        }
</style>
<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
						<label class="layui-form-label">车牌号</label>
						<div class="layui-input-inline">
							<input type="text" id="keyword"
								placeholder="请输入车牌号" autocomplete="off"
								class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
				      <label class="layui-form-label">日期范围</label>
				      <div class="layui-input-inline">
				        <input type="text" class="layui-input" id="dateInput" placeholder=" - " lay-key="7">
				      </div>
				      <div class="layui-input-inline timeOptions">
				       <a class="layui-btn layui-btn-primary">近1个月</a>
				        <a class="layui-btn layui-btn-primary">近2个月</a>
				         <a class="layui-btn layui-btn-primary">近3个月</a>
				      </div>
				    </div>
				    
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
			</div>
		</form>
		
		<div class="layui-form" id="table-list">
			<table class="layui-table" id="maintainCarInfo" lay-filter="tableInfo"></table>
			<script type="text/html" id="toolbarTop">
            </script>
		</div>
	</div>
</body>
<input type="hidden" id="nextOneMonth" value="${nextOneMonth}">
<input type="hidden" id="nextTwoMonth" value="${nextTwoMonth}">
<input type="hidden" id="nextThreeMonth" value="${nextThreeMonth}">
<script type="text/html" id="typeTpl">
	<span >交强险</span><br/>
	<span >商业险</span>
 </script>

<script type="text/html" id="dateTpl">
	<span >{{d.compulsoryDate}}</span><br/>
	<span >{{d.commercialDate}}</span>
 </script>
</html>
