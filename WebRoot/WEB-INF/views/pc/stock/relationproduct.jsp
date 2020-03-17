<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<%@ include file="../base.jsp"%>
<style>
.layui-form-select dl {
	max-height: 100px;
}
</style>
<title>连途</title>
</head>
<body>
<form class="layui-form" action="" id="form" style="width: 96%;height: 98%;margin-top: 1%;margin-left: 2%;">
<div class="layui-collapse" lay-accordion lay-filter="test">
  <div class="layui-colla-item">
    <h2 class="layui-colla-title">使用调出门店产品名称</h2>
    <div class="layui-colla-content layui-show" id="addProduct">
    		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 30px;">名称</label>
			<div class="layui-input-block" style="margin-left: 80px;">
			<input type="text" name="productName" id="productName" autocomplete="off" class="layui-input layui-disabled" value="${product.productName }" disabled="disabled" />
			</div>
			</div>
    		<div class="layui-form-item">
			<label class="layui-form-label" style="width: 30px;">型号</label>
			<div class="layui-input-block" style="margin-left: 80px;">
			<input type="text" name="productType" id="productType" autocomplete="off" class="layui-input layui-disabled" value="${product.productType }" disabled="disabled" />
			</div>
			</div>
    </div>
  </div>
  <div class="layui-colla-item">
    <h2 class="layui-colla-title">匹配调入方产品</h2>
    <div class="layui-colla-content" id="fitProduct">
      <div class="layui-form-item">
    <div class="layui-input-block" style="margin-left: 30px;">
      <select name="productId" id="productId" lay-search>
        <c:forEach items="${list }" var="product">
        <option value="${product.id }">${product.productName }</option>
        </c:forEach>
      </select>
    </div>
  </div>
    </div>
  </div>
</div>
</form>
</body>
<script type="text/javascript">
	layui.use([ 'jquery', 'table', 'dialog', 'element' ], function() {
		var table = layui.table;
		var dialog = layui.dialog;
		var $ = layui.jquery;
	});
</script>

</html>

