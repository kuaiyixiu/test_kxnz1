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
<script type="text/javascript" src="js/sysmanage/car/carUtil.js"></script>
<script type="text/javascript" src="js/sysmanage/car/carDetail.js"></script>
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

.tableUl {
    margin-left: 20px;
}
</style>
<body>
	<h3>基本信息</h3>
	<table class="layui-table" id="carDetailTab" style="max-width: 300px; margin-left: 20px;">
	  <colgroup>
	    <col width="80">
	    <col width="80">
	    <col>
	  </colgroup>
	  <tbody>
	    <tr>
	      <td class="table-title">车主姓名</td>
	      <td data-id="ownerName"></td>
	    </tr>
	    <tr>
	      <td class="table-title">联系方式</td>
	      <td data-id="ownerCellphone"></td>
	    </tr>
	    <tr>
		  <td class="table-title">车牌号</td>
	      <td data-id="carNumber"></td>
	    </tr>
	    <tr>
		  <td class="table-title">交强险到期时间</td>
	      <td data-id="compulsoryDateStr"></td>
	    </tr>
	    <tr>
		  <td class="table-title">商业险到期时间</td>
	      <td data-id="commercialDateStr"></td>
	    </tr>
	    <tr>
		  <td class="table-title">年检到期时间</td>
	      <td data-id="checkDateStr"></td>
	    </tr>
	  </tbody>
	</table>
	<h3>保养记录</h3>
	<table class="layui-table" id="carMaintainInfo" lay-filter="tableInfo"></table>
	
	<h3>消费记录</h3>
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
<%--				     <%@ include file="../commonTime.jsp"%>--%>
					<button class="layui-btn layui-btn-normal" data-type="reload" lay-filter="searchBtn" type="button">搜索</button>
				</fieldset>
			</div>
		</form>
	<table class="layui-table" id="consumeInfo" lay-filter="tableInfo"></table>
</body>

<input type="hidden" id="carDetail" value='${ car }'>
<input type="hidden" data-id="carId" value="${ carId }">
<input type="hidden" id="nextOneMonth" value="${nextOneMonth}">
<input type="hidden" id="nextTwoMonth" value="${nextTwoMonth}">
<input type="hidden" id="nextThreeMonth" value="${nextThreeMonth}">
<input type="hidden" id="carNumber" value="${ carNumber }">

<script type="text/html" id="barLine">
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="detail" data-url="car/ordersDetailView.do" tabId="customMealInfo">查看</a>
</script>

<script type="text/html" id="productTpl">
	<li>{{ d.productName }}   {{ d.price}}</li>
</script>

<script type="text/html" id="serverTpl">
	<li>{{ d.serveName }}   {{ d.price}}</li>
</script>
</html>




