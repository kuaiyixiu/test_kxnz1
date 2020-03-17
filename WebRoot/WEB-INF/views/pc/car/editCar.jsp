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
<script type="text/javascript" src="js/sysmanage/car/editCar.js"></script> 
</head>
<body>
	<form action="" class="layui-form" id="editCarForm"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<div class="layui-form-item">
				<div class="layui-inline">
				<label class="layui-form-label">车主姓名</label>
				<div class="layui-input-block">
					<input type="text" name="ownerName" lay-verify="required"
						placeholder="请输入车主姓名" autocomplete="off" class="layui-input"
						value=''>
				</div>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label">联系方式</label>
			    <div class="layui-input-block">
					<input type="text" name="ownerCellphone" lay-verify="required"
						placeholder="请输入车主姓名" autocomplete="off" class="layui-input"
						value=''>
				</div>
			    </div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车辆型号</label>
				<div class="layui-input-block" id="vehicleType">
					
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">车辆品牌</label>
				<div class="layui-input-block">
				<input type="text" name="carBrand" 
					 placeholder="请输入车辆品牌" autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label">车架号</label>
				<div class="layui-input-block">
					<input type="text" name="carVin" 
						placeholder="请输入车架号" autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
		
			<div class="layui-inline">
				<label class="layui-form-label">发动机号</label>
				<div class="layui-input-block">
				<input type="text" name="carEngine" 
					 placeholder="请输入发送机号" autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label">交强险到期</label>
				<div class="layui-input-block">
					<input type="text" name="compulsoryDateStr" data-id="dateInput" id="compulsoryDateStr"
						placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">商业险到期</label>
				<div class="layui-input-block">
				<input type="text" name="commercialDateStr" id="commercialDateStr" data-id="dateInput" 
				  placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
				</div>
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label">年检到期</label>
				<div class="layui-input-block">
			      <input type="text" id="checkDateStr" data-id="dateInput" name="checkDateStr"  
					placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input"
					>
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
				<label class="layui-form-label">备注</label>
			    <div class="layui-input-block">
			      <textarea placeholder="请输入内容" name="remark" class="layui-textarea" style="width: 490px;"></textarea>
			    </div>
		</div>

		<div class="layui-form-item">
			<div align="left" style="margin-left: 250px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn closeBtn layui-btn-normal">关闭</a>
			</div>
		</div>
		<input type="hidden" name="customId">
		<input type="hidden" name="id">
		<input type="hidden" name="customId">
		<input type="hidden" name="vehicleType">
		<input type="hidden" name="carType">
	</form>
	<input type="hidden" id="carTypes" value='${carTypes}'>
	<input type="hidden" id="carDetail" value='${carDetail}'>
</body>
</html>

<script id="vehicleTypeTpl" type="text/html">
	{{#  layui.each(d.list, function(index, item){ }}
		{{#  if(item.key == d.checkid){ }}
			<input type="radio" name="vehicleType" value="{{ item.key }}" title="{{ item.value }}" checked="checked">
		{{#  } 
		else { }}
			<input type="radio" name="vehicleType" value="{{ item.key }}" title="{{ item.value }}">
		{{#  } }}
  	{{#  }); }}
</script>
 