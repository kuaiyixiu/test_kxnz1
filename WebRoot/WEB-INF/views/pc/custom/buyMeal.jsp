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
<script type="text/javascript" src="js/sysmanage/custom/buyMeal.js"></script>
</head>
<style>
.layui-form-selectup dl {
    bottom: auto;
}
</style>
<body>
	<form action="" class="layui-form" id="buyMealForm" style="margin-top: 1%;margin-left: 50px;">
		<div class="layui-form-item">
			<div class="layui-inline">
			    <label class="layui-form-label">套餐类型</label>
			    <div class="layui-input-block normal-select" id="mealTypeDiv">
			    </div>
			</div>
		</div>
		<div class="layui-form-item" id="mealDetail" style="display:none;">
			<div class="layui-inline">
				<label class="layui-form-label">套餐说明</label>
				<div class="layui-input-block" id="mealUl">
					
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">实收价格</label>
				<div class="layui-input-block">
			      <input type="number" id="totalPrice" name="price" lay-verify="money"
						placeholder="请输入实收价格" autocomplete="off" class="layui-input"
						value="0.00" onblur="initNum(this,2)">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
				<label class="layui-form-label">付款方式</label>
			    <div class="layui-input-block normal-select" id="payTypeDiv">
			      <!-- <textarea placeholder="请输入内容" name="remark" class="layui-textarea"></textarea> -->
			    </div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">过期时间</label>
				<div class="layui-input-block">
					<input type="text" name="endDate"  id="expireDate" 
						placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input layui-disabled" value="${nextYear}">
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div align="left"  style="margin-left: 120px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn layui-btn-normal closeBtn" lay-filter="closeBtn">关闭</a>
			</div>
		</div>
	</form>
</body>
</html>
<input type="hidden" id="meals" value='${meals}'>
<input type="hidden" id="payTypes" value='${payTypes}'>

<script id="mealsTpl" type="text/html">
	<select lay-search="" name="mealId" lay-filter="mealsSelect" lay-verify="required">
		<option value="">请选择套餐类型</option>
	{{#  layui.each(d, function(index, item){ }}
		<option value="{{ item.id }}" data-amount="{{ item.amount }}">{{ item.name }}</option>
  	{{#  }); }}
  	{{#  if(meals.length === 0){ }}
    	<option value="">暂无</option>
	{{#  } }} 
	</select>
</script>

<script id="mealsDtTpl" type="text/html">
	<ul>
	{{#  layui.each(d, function(index, item){ }}
		<li data-id="{{ item.id }}" data-quantity="{{ item.quantity }}">{{ item.mealDtName }}x{{ item.quantity }}</li>
  	{{#  }); }}
	</ul>
</script>

<script id="payTypesTpl" type="text/html">
	<select  lay-search="" class="layui-anim-upbit" name="typeId" lay-filter="payTypesSelect" lay-verify="required">
	{{#  layui.each(d, function(index, item){ }}
		<option value="{{ item.id }}">{{ item.payName }}</option> 
  	{{#  }); }}
	</select>
</script>
