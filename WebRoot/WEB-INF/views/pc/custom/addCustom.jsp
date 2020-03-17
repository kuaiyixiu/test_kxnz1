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
<script type="text/javascript" src="js/sysmanage/custom/addCustom.js"></script>
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
	<form action="" class="layui-form" id="addCustomForm"
		style="width: 95%;height: 98%;margin-top: 1%;">
		<h3>基本信息</h3>
		<div class="layui-form-item">
				<div class="layui-inline">
				<label class="layui-form-label">客户姓名</label>
				<div class="layui-input-block">
					<input type="text" name="custName" lay-verify="required"
						placeholder="请输入客户姓名" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
			<div class="layui-inline">
			    <label class="layui-form-label">会员类型</label>
			    <div class="layui-input-block normal-select" id="customSelect">
			    </div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">联系方式</label>
				<div class="layui-input-block">
					<input type="text" name="cellphone" lay-verify="required|phone"
						placeholder="请输入联系方式" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div>
			
			<div class="layui-inline">
				<label class="layui-form-label">会员生日</label>
				<div class="layui-input-block">
				<input type="text" name="birthdayStr" id="date"  placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
<!-- 			<div class="layui-inline">
				<label class="layui-form-label">初始积分</label>
				<div class="layui-input-block">
					<input type="text" name="score" lay-verify="required"
						placeholder="请输入初始积分" autocomplete="off" class="layui-input"
						value="">
				</div>
			</div> -->
			
			<div class="layui-inline">
				<label class="layui-form-label">会员性别</label>
				<div class="layui-input-block">
			      <input type="radio" name="sex" value="0" title="男" checked="">
			      <input type="radio" name="sex" value="1" title="女">
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
			<h3 class="addCarModule">绑定车牌</h3>
			<a title="添加" class="layui-btn layui-btn-sm addCarbutton"  data-method="addCarTpl" data-id='carButton' data-method="addCarTpl" > <i class="layui-icon">&#xe654;</i></a>
		</div>
		
		<div id="carList"></div>

		<div class="layui-form-item">
			<div align="left" style="margin-left: 250px;">
				<a class="layui-btn layui-btn-normal" lay-submit lay-filter="formSubmitBtn">提交</a>
				<a class="layui-btn layui-btn-normal" id="closeButton" lay-filter="closeBtn">关闭</a>
			</div>
		</div>
	</form>
</body>
</html>
<input type="hidden" id="customTypes" value='${customTypes}'>

<script id="carTpl" type="text/html">
		<div class="layui-form-item childrenCar">
		  <div class="layui-inline">
		    <label class="layui-form-label">车牌号</label>
		    <div class="layui-input-block">
		      <input type="text" data-id="carNumber" lay-verify="carNo" placeholder="请输入车牌号" autocomplete="off" class="layui-input"></div>
		  </div>
         <a title="删除" class="layui-btn carClose layui-btn-sm" data-id='carButton' data-id='carButton' data-method="delCarTpl" style="margin-left: 22;" data-method="delCarTpl"> <i class="layui-icon">&#xe640;</i></a>
		</div>
</script>

<script id="customTypesTpl" type="text/html">
	<select name="custType"  lay-search="">
	{{#  layui.each(d, function(index, item){ }}
		{{#  if(item.typeName == "到店开卡"){ }}
			<option value="{{ item.id }}" selected="selected">{{ item.typeName }}</option> 
		{{#  } 
		else { }}
		<option value="{{ item.id }}">{{ item.typeName }}</option>
	{{#  } }}
  	{{#  }); }}
  	{{#  if(customTypes.length === 0){ }}
    	<option value="">暂无</option>
	{{#  } }} 
	</select>
</script>

