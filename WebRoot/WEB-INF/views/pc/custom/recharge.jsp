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
<%@ include file="../base.jsp"%>
<script type="text/javascript" src="js/sysmanage/custom/recharge.js"></script>
<script type="text/javascript" src="js/sysmanage/custom/customUtil.js"></script>
</head>
<style>
h3 {
  margin-bottom: 20px;
  font-size: 18px;
  padding-left: 10px;
  border-left: 4px solid #0DA0FE; 
}
  
.layui-table-cell {
    height: auto;
    line-height: 28px;
}
</style>
<body>
	<div class="layui-btn-container" id="tools" style=" margin-top: 20px;margin-left: 20px;">
       <button class="layui-btn layui-btn-normal layui-btn-sm" data-id="toolBtn"   data-title="储值充值" data-url="custom/addRecharge.do?cardNo=${custom.cardNo}" openw="500px" openh="500px">储值充值</button> 
<%--       <button class="layui-btn layui-btn-normal layui-btn-sm" data-id="toolBtn"   data-title="购买服务" data-url="custom/addCustomView.do" openw="500px" openh="500px">购买服务</button>--%>
       <button class="layui-btn layui-btn-normal layui-btn-sm" data-id="toolBtn" data-type="auto"  data-title="购买套餐" data-url="custom/buyMealView.do" openw="500px" openh="400px">购买套餐</button>
	</div>
	<h3>基本信息</h3>
	<table class="layui-table" style="max-width: 250px; margin-left: 20px;">
	  <colgroup>
	    <col width="80">
	    <col width="80">
	    <col>
	  </colgroup>
	  <tbody>
	    <tr>
	      <td class="table-title">开卡门店</td>
	      <td id="shopTd"></td>
	    </tr>
	    <tr>
	      <td class="table-title">姓名</td>
	      <td>${custom.custName}</td>
	    </tr>
	    <tr>
		  <td class="table-title">卡号</td>
	      <td>${custom.cardNo}</td>
	    </tr>
	    <tr>
		  <td class="table-title">类型</td>
	      <td id="custTypeTd"></td>
	    </tr>
	    <tr>
		  <td class="table-title">余额</td>
	      <td>${custom.balance}</td>
	    </tr>
	  </tbody>
	</table>
	<h3>已购套餐</h3>
	<span id="total" style="margin-left: 20px;">总价格为：</span>
	<table class="layui-table" id="customMealInfo" lay-filter="tableInfo"></table>
</body>

<input type="hidden" id="shopListMap" value='${shopListMap}'>
<input type="hidden" id="customTypes" value='${customTypes}'>
<input type="hidden" id="shopId" value='${custom.shopId}'>
<input type="hidden" id="customId" value='${custom.id}'>
<input type="hidden" id="custType" value='${custom.custType}'>
<script type="text/html" id="barLine">
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" data-url="custom/delCustomMeal.do" tabId="customMealInfo">删除</a>
</script>

<script type="text/html" id="contentTpl">
	<dd>{{ d.mealDtName }} x {{ d.quantity}}</dd>
</script>
</html>




