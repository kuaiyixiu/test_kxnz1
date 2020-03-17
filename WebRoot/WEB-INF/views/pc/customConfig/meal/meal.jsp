<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<%@ include file="../../base.jsp"%>
<link rel="stylesheet" type="text/css" href="lib/admin/otherPlugin/layui-formSelects/formSelects-v4.css" />
<script type="text/javascript" src="js/sysmanage/meal/meal.js"></script>
</head>
<body>
<form class="layui-form" >
  <div class="layui-form-item searchDiv">
    <label class="layui-form-label">选择门店</label>
    <div class="layui-inline" style="width:600px;">
    <select name="shop" xm-select="select1" id="shopsDiv" xm-select-skin="normal">
    <c:forEach items="${shopList}" var="item">
    	<option value="${ item.key }" selected="selected">${ item.value }</option> 
    </c:forEach>
    </select>
    </div>
   	<div class="layui-inline">
   		<input type="checkbox" id="obtained" title="查看下架" lay-skin="primary">
   	</div>
	<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
  </div>
</form>

	<div class="page-content-wrap">
		<table class="layui-table" id="mealInfo" lay-filter="tableInfo"></table>
	</div>
</body>
</html>
<input type="hidden" id="shopListMap" value='${ shopListMap }'>
<input type="hidden" id="shopId" value='${ shopId }'>


<script type="text/html" id="barLine">
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="meal/obtainedMeal.do" tabId="mealInfo">下架</a>
</script>


<script type="text/html" id="toolbarTop">
	<div class="layui-btn-container">
		<a class="layui-btn layui-btn-sm kxnz-btn-default" id="addMealBtn" data-title="新增会员配置"><i class="layui-icon">&#xe608;</i>新增</a>
	</div>
</script>

<script id="shopsTpl" type="text/html">
	{{#  layui.each(d, function(index, item){ }}
		<option value="{{ item.id }}" selected="selected">{{ item.name }}</option> 
  	{{#  }); }}
</script>