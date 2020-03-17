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
<%@ include file="../../base.jsp"%>
<script type="text/javascript" src="js/sysmanage/meal/addMeal.js"></script> 
</head>
<style>
h3 {
  margin-top: 25px;
  margin-bottom: 20px;
  font-size: 18px;
  padding-left: 10px;
  border-left: 4px solid #0DA0FE; }
</style>
<body>
	<form action="" class="layui-form" id="mealForm"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<h3>基本信息</h3>
		<div class="layui-form-item">
				<div class="layui-inline">
				<label class="layui-form-label">套餐名称</label>
				<div class="layui-input-block">
					<input type="text" name="mealName" id="mealName" lay-verify="required"
						placeholder="请输入套餐名称" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label">有效天数</label>
			    <div class="layui-input-block normal-select">
			    	<input type="number" name="day" lay-verify="required"
						placeholder="请输入有效天数" autocomplete="off" class="layui-input"
						value="365">
			    </div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">套餐售价</label>
				<div class="layui-input-block">
					<input type="number" name="amount" lay-verify="required"
						placeholder="请输入套餐售价" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
				<label class="layui-form-label">套餐备注</label>
			    <div class="layui-input-block">
			      <textarea placeholder="请输入套餐备注" name="remark" class="layui-textarea" style="width: 490px;"></textarea>
			    </div>
		</div>
		
        <div class="layui-form-item">
        	<div style="folat:left">
				<h3 class="addCarModule">服务</h3>
				<a class="layui-btn layui-btn-normal" id="addServeBtn">添加</a>
			</div>
			<table class="layui-table" id="serveInfo" lay-filter="tableInfo">
			  <colgroup>
			    <col width="150">
			    <col width="200">
			    <col width="200">
			    <col>
			  </colgroup>
			  <thead>
			    <tr>
			      <th lay-data="{field:'name'}">服务名</th>
			      <th lay-data="{field:'price'}">单价</th>
			      <th lay-data="{field:'count'}">次数</th>
			      <th lay-data="{field:'id'}" style="display:none;">ID</th>
			      <th>操作</th>
			    </tr> 
			  </thead>
			  <tbody>
			  
			  </tbody>
			</table>
		</div>
		
		<div class="layui-form-item">
		    <div style="folat:left">
				<h3 class="addCarModule">产品</h3>
				<a class="layui-btn layui-btn-normal" id="addProductBtn">添加</a>
			</div>
			<table class="layui-table" id="productInfo" lay-filter="tableInfo">
			  <colgroup>
			    <col width="150">
			    <col width="200">
			    <col width="200">
			    <col>
			  </colgroup>
			  <thead>
			    <tr>
			      <th>产品名</th>
			      <th>单价</th>
			      <th>次数</th>
			      <th>操作</th>
			    </tr> 
			  </thead>
			  <tbody>
			  </tbody>
			</table>
		</div>

		<div class="layui-form-item">
			<div align="left" style="margin-left: 250px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn layui-btn-normal" id="closeButton" lay-filter="closeBtn">关闭</a>
			</div>
		</div>
	</form>
</body>
</html>
