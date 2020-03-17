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
<script type="text/javascript" src="js/sysmanage/car/carUtil.js"></script>
<script type="text/javascript" src="js/sysmanage/custom/otherShopIndex.js">
</script>

</head>

<body>
	<div class="page-content-wrap">
		<form class="layui-form">
			<div class="layui-form-item searchDiv">
				<fieldset>
					<legend>查询条件</legend>
					<div class="layui-inline">
				      <label class="layui-form-label">门店</label>
				      <div class="layui-input-inline" id="shopsDiv">
				      </div>
				    </div>
				    
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
			<table class="layui-table" id="otherShopInfo" lay-filter="tableInfo"></table>
		</div>
	</div>
</body>

<input type="hidden" id="nextOneMonth" value="${nextOneMonth}">
<input type="hidden" id="nextTwoMonth" value="${nextTwoMonth}">
<input type="hidden" id="nextThreeMonth" value="${nextThreeMonth}">
<input type="hidden" id="shopListMap" value='${shopListMap}'>
<input type="hidden" id="customShopId" value="${customShopId}">

</html>

<script id="shopsTpl" type="text/html">
	<select name="shopId"  lay-search="">
	{{#  layui.each(d.list, function(index, item){ }}
		<option value="{{ item.id }}" >{{ item.shopName }}</option>
  	{{#  }); }}
  	{{#  if(d.list.length === 0){ }}
    	<option value="">暂无</option>
	{{#  } }} 
	</select>
</script>
