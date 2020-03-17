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
<link rel="stylesheet" type="text/css"
	href="lib/admin/layui/css/layui.css" />
 <script src="lib/admin/layui/layui.js" type="text/javascript" 
	charset="utf-8"></script>
<script src="lib/admin/js/common.js" type="text/javascript"
	charset="utf-8"></script> 
<script type="text/javascript" src="js/sysmanage/custom/delMealDt.js"></script> 
</head>
<body>
	<form action="" class="layui-form" id="delMealDtForm"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
				<div class="layui-inline">
				<label class="layui-form-label">项目价值</label>
				<div class="layui-input-block">
					<input type="text" name="price" lay-verify="required"
						placeholder="请输入车主姓名" autocomplete="off" class="layui-input"
						value='${price}'>
				</div>
			</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">是否退款</label>
				<div class="layui-input-block">
				<input type="radio" name="refund" value="1" title="是" checked="">
      			<input type="radio" name="refund" value="0" title="否">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">退款金额</label>
				<div class="layui-input-block">
				<input type="text" name="carEngine" 
					 placeholder="请输入退款金额" autocomplete="off" class="layui-input" value="${price}">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">支付方式</label>
				<div class="layui-input-block" id="payTypeDiv">
				
				</div>
			</div>
		</div>

		<div class="layui-form-item">
			<div align="left" style="margin-left: 250px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
			</div>
		</div>
		<input type="hidden" name="id" value="${ id }">
		<input type="hidden" name="pId" value="${ pId }">
	</form>
	<input type="hidden" id="payTypes" value='${payTypes}'>
	
	<script id="payTypesTpl" type="text/html">
	<select lay-search="" name="typeId" lay-filter="payTypesSelect" lay-verify="required">
	{{#  layui.each(d, function(index, item){ }}
		{{#  if(item.id == '3'){ }}
			<option value="{{ item.id }}" selected="selected">{{ item.payName }}</option>
		{{#  } 
		else { }}
			<option value="{{ item.id }}">{{ item.payName }}</option>
		{{#  } }}
  	{{#  }); }}
	</select>
</script>
</body>
</html>